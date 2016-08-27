package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.DateUtils;
import com.fnf.utils.EncryptionUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.EmployeeDto;
import com.webapp.models.CityModel;
import com.webapp.models.EmployeeModel;
import com.webapp.models.State;
import com.webapp.services.CityService;
import com.webapp.services.EmployeeService;
import com.webapp.services.StateSerivce;
import com.webapp.validator.EmployeeValidator;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private StateSerivce stateService;
		
	@Autowired
	private EmployeeValidator employeeValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(employeeValidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "employee";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<EmployeeDto> getEmployeeList(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<EmployeeDto> dt = new DataTablesTO<EmployeeDto>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		//DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<EmployeeDto> accts = employeeService.fetchEmployeeList(tableUtils);

		for(EmployeeDto data: accts)
		{
			data.setAction("<a href='javascript:void(0);' onclick=editEmployee('"+ data.getUserId()+ "');>Edit &nbsp; <i class='fa fa-edit font-size-17px'></i></a>");
			if(data.getRecordStatus().equals("A")){
				data.setAction(data.getAction()+"&nbsp; <a href='javascript:void(0);' onclick=changeEmployeeStatus('"+ data.getUserId()+ "');>Deactivate &nbsp;<i class='fa fa-times font-size-17px'></i></a>");
			}
			else 
			{
				data.setAction(data.getAction()+"&nbsp; <a href='javascript:void(0);' onclick=changeEmployeeStatus('"+ data.getUserId()+ "');>Activate &nbsp; <i class='fa fa-check-circle font-size-17px'></i></a>");
			}
		}
		long count =  employeeService.fetchTotalEmployeeList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String addEmployee(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		EmployeeModel employeeModel= new EmployeeModel();
		model.addAttribute("employeeModel",employeeModel);
		return "add-employee";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String postAddEmployee(Model model,@Validated EmployeeModel employeeModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		
		if (result.hasErrors()) {
			model.addAttribute("employeeModel",employeeModel);
			return "add-employee";
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		String[] fullName=employeeModel.getFullName().split(" ");
		if(fullName.length>0)
		{
			String userName;
			if(fullName.length>1)
				userName=fullName[1].toLowerCase()+fullName[0].toLowerCase().charAt(0);
			else
				userName=fullName[0].toLowerCase();
			employeeModel.setUserName(userName);
			int count=employeeService.fetchEmployeeByUserName(employeeModel);
			if(count>0)
				employeeModel.setUserName(userName+count);
		}
		if(employeeModel.getBirthDateForModel()!=null && !"".equalsIgnoreCase(employeeModel.getBirthDateForModel()) )
			employeeModel.setBirthDate(DateUtils.getMilesecFromDateStr(employeeModel.getBirthDateForModel(), "dd/MM/yyyy", "IST"));
		employeeModel.setPassword(EncryptionUtils.encryptPassword(employeeModel.getPassword()));
		employeeModel.setProfileName("");
		int status =employeeService.postAddEmployee(employeeModel, userId);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}
		return pageRedirect("/employee");
	}
	
	@RequestMapping(value = "/edit-employee/{employeeId}", method = RequestMethod.GET)
	public String editEmployee(Model model, @PathVariable("employeeId") String employeeId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		EmployeeModel employeeModel = employeeService.fetchEmployeeDetail(employeeId);
		if(employeeModel.getBirthDate()!=null)
			employeeModel.setBirthDateForModel(DateUtils.dbTimeStampToSesionDate(employeeModel.getBirthDate(), "IST", "dd/MM/yyyy") );
		model.addAttribute("employeeModel", employeeModel);
		return "/edit-employee";
	}
	
	@RequestMapping(value = "/edit-employee", method = RequestMethod.POST)
	public String editStatePost(Model model, @Validated EmployeeModel employeeModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		if (!dbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		if (result.hasErrors()) {
			model.addAttribute("employeeModel",employeeModel);
			return "edit-employee";
		}
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		employeeModel.setUpdatedBy(userId);
		if(employeeModel.getBirthDateForModel()!=null && !"".equalsIgnoreCase(employeeModel.getBirthDateForModel()))
			employeeModel.setBirthDate(DateUtils.getMilesecFromDateStr(employeeModel.getBirthDateForModel(), "dd/MM/yyyy", "IST"));
		employeeService.editEmployee(employeeModel);

		return pageRedirect("/employee");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/validations/form-validation.js","js/viewjs/employee.js" };
		//return new String[] { "js/viewjs/city.js" };
	}

	
}
