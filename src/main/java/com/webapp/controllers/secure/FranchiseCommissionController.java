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
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.FranchiseDto;
import com.webapp.models.FranchiseCommissionModel;
import com.webapp.models.FranchiseModel;
import com.webapp.services.FranchiseCommissionService;
import com.webapp.services.FranchiseService;
import com.webapp.validator.FranchiseCommissionValidator;

@Controller
@RequestMapping("/franchisee-commission")
public class FranchiseCommissionController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private FranchiseService franchiseService;
	
	@Autowired 
	private FranchiseCommissionService franchiseCommissionService;
	
	@Autowired
	private FranchiseCommissionValidator franchiseCommissionValidator;
	
	@InitBinder("franchiseCommissionModel")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(franchiseCommissionValidator);
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
		return "franchisee-commission";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<FranchiseModel> getFranchiseList(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<FranchiseModel> dt = new DataTablesTO<FranchiseModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		//DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		//String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<FranchiseModel> accts = franchiseService.fetchFranchiseCommissionList(tableUtils);
		int i=1;
		for(FranchiseModel data: accts)
		{
			data.setSrNo((i++)+tableUtils.getiDisplayStart());
			data.setAction("<a href='javascript:void(0);' onclick=viewFranchiseDetail('"+ data.getFranchiseeId()+ "');>View Detail &nbsp; <i class='fa fa-eye font-size-17px'></i></a>");
		}
		long count =  franchiseService.fetchTotalEmployeeList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	
	
	@RequestMapping(value = "/view-franchisee/{franchiseeId}", method = RequestMethod.GET)
	public String viewFranchisee(Model model, @PathVariable("franchiseeId") String franchiseeId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		FranchiseModel franchiseeModel = franchiseService.fetchFranchiseDetail(franchiseeId);
		model.addAttribute("franchiseModel", franchiseeModel);
		List<FranchiseCommissionModel> franchiseCommissionList = franchiseCommissionService.fetchFranchiseCommissionList(franchiseeId);
		int i=1;
		for (FranchiseCommissionModel franchiseCommissionModel : franchiseCommissionList) {
			franchiseCommissionModel.setSrNo((i++));
			if(franchiseCommissionModel.getChequeDate()>0){
				franchiseCommissionModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(franchiseCommissionModel.getChequeDate(), "IST", "dd/MM/yyyy"));
			}
		}
		model.addAttribute("franchiseCommissionList",franchiseCommissionList);
		return "/view-franchisee";
	}
	@RequestMapping(value = "/view-franchisee-commission", method = RequestMethod.GET)
	public String viewFranchiseeList(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		List<FranchiseCommissionModel> franchiseCommissionList = franchiseCommissionService.fetchAllFranchiseCommissionList();
		int i=1;
		/*//For Testing purpose
		FranchiseCommissionModel test=new FranchiseCommissionModel();
		for(int j=0;j<10;j++)
		{
			test=new FranchiseCommissionModel();
			test.setFranchiseeName("test"+j);
			test.setPan("PANTest"+j);
			test.setCommissionAmount(20000*j);
			test.setTds(20+j+"");
			test.setTdsAmount(((20000*j)*20+j)/100+"");
			franchiseCommissionList.add(test);
		}*/
		for (FranchiseCommissionModel franchiseCommissionModel : franchiseCommissionList) {
			franchiseCommissionModel.setSrNo((i++));
			franchiseCommissionModel.setTdsAmount(Math.round((franchiseCommissionModel.getTds()*franchiseCommissionModel.getCommissionUnpaid())/100));
		}
		model.addAttribute("franchiseCommissionList",franchiseCommissionList);
		return "/view-franchisee-commission";
	}
	@RequestMapping(value = "/add-franchisee-commission", method = RequestMethod.POST)
	public String editStatePost(Model model, @Validated FranchiseCommissionModel franchiseCommissionModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		if (!dbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		if (result.hasErrors()) {
			List<FranchiseDto> franchiseeModelList= franchiseService.fetchAllFranchiseList();
			model.addAttribute("franchiseCommissionModel",franchiseCommissionModel);
			model.addAttribute("franchiseeModelList", franchiseeModelList);
			return "add-franchisee-commission";
		}
		String franchiseValues[]= franchiseCommissionModel.getFranchiseeId().split("_");
		franchiseCommissionModel.setFranchiseeId(franchiseValues[0]);
		franchiseCommissionModel.setFranchiseeName(franchiseValues[1]);
		if(franchiseCommissionModel.getChequeDateString()!=null && !"".equals(franchiseCommissionModel.getChequeDateString()))
			franchiseCommissionModel.setChequeDate(DateUtils.getMilesecFromDateStr(franchiseCommissionModel.getChequeDateString(), "dd/MM/yyyy", "IST"));
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		int status =franchiseCommissionService.addFranchiseCommission(franchiseCommissionModel,userId);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}

		return pageRedirect("/franchisee-commission");
	}
	
	@RequestMapping(value = "/add-franchisee-commission",method = RequestMethod.GET)
	public String addEmployee(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		List<FranchiseDto> franchiseeModelList= franchiseService.fetchAllFranchiseList();
		FranchiseCommissionModel franchiseeCommissionModel= new FranchiseCommissionModel();
		model.addAttribute("franchiseCommissionModel",franchiseeCommissionModel);
		model.addAttribute("franchiseeModelList", franchiseeModelList);
		return "add-franchisee-commission";
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/validations/form-validation.js","js/viewjs/franchisee-commission.js" };
	}

	
}
