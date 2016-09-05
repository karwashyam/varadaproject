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
import com.webapp.dto.FranchiseDto;
import com.webapp.models.CityModel;
import com.webapp.models.EmployeeModel;
import com.webapp.models.FranchiseModel;
import com.webapp.models.State;
import com.webapp.services.CityService;
import com.webapp.services.EmployeeService;
import com.webapp.services.FranchiseService;
import com.webapp.services.StateSerivce;
import com.webapp.validator.EmployeeValidator;
import com.webapp.validator.FranchiseValidator;

@Controller
@RequestMapping("/franchisee")
public class FranchiseController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private FranchiseService franchiseService;
		
	@Autowired
	private FranchiseValidator franchiseValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(franchiseValidator);
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
		return "franchisee";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<FranchiseDto> getFranchiseList(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<FranchiseDto> dt = new DataTablesTO<FranchiseDto>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		//DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		//String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<FranchiseDto> accts = franchiseService.fetchFranchiseList(tableUtils);

		for(FranchiseDto data: accts)
		{
			data.setAction("<a href='javascript:void(0);' onclick=editFranchise('"+ data.getFranchiseeId()+ "');>Edit &nbsp; <i class='fa fa-edit font-size-17px'></i></a>");
			if(data.getRecordStatus().equals("A")){
				data.setAction(data.getAction()+"&nbsp; <a href='javascript:void(0);' onclick=changeFranchiseStatus('"+ data.getFranchiseeId()+ "');>Deactivate &nbsp;<i class='fa fa-times font-size-17px'></i></a>");
			}
			else 
			{
				data.setAction(data.getAction()+"&nbsp; <a href='javascript:void(0);' onclick=changeFranchiseStatus('"+ data.getFranchiseeId()+ "');>Activate &nbsp; <i class='fa fa-check-circle font-size-17px'></i></a>");
			}
		}
		long count =  franchiseService.fetchTotalEmployeeList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String addFranchise(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		FranchiseModel employeeModel= new FranchiseModel();
		model.addAttribute("franchiseModel",employeeModel);
		return "add-franchisee";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String postAddEmployee(Model model,@Validated FranchiseModel franchiseModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		
		if (result.hasErrors()) {
			model.addAttribute("franchiseModel",franchiseModel);
			return "add-franchisee";
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
	
		int status =franchiseService.postAddFranchise(franchiseModel, userId);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}
		return pageRedirect("/franchisee");
	}
	
	@RequestMapping(value = "/edit-franchisee/{franchiseeId}", method = RequestMethod.GET)
	public String editEmployee(Model model, @PathVariable("franchiseeId") String franchiseeId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		FranchiseModel franchiseeModel = franchiseService.fetchFranchiseDetail(franchiseeId);
		model.addAttribute("franchiseModel", franchiseeModel);
		return "/edit-franchisee";
	}
	
	@RequestMapping(value = "/edit-franchisee", method = RequestMethod.POST)
	public String editStatePost(Model model, @Validated FranchiseModel franchiseModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		if (!dbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		if (result.hasErrors()) {
			model.addAttribute("franchiseModel",franchiseModel);
			return "edit-franchisee";
		}
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		franchiseModel.setUpdatedBy(userId);
		int status =franchiseService.editFranchise(franchiseModel);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}

		return pageRedirect("/franchisee");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/validations/form-validation.js","js/viewjs/franchisee.js" };
	}

	
}
