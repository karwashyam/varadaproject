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
import com.webapp.models.FranchiseModel;
import com.webapp.models.MemberModel;
import com.webapp.models.State;
import com.webapp.services.FranchiseService;
import com.webapp.services.MemberService;
import com.webapp.validator.MemberValidator;

@Controller
@RequestMapping("/member")
public class MemberController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private MemberService memberService;
	
	@Autowired 
	private FranchiseService franchiseService;
		
	@Autowired
	private MemberValidator memberValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(memberValidator);
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
		return "member";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<MemberModel> getMemberList(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<MemberModel> dt = new DataTablesTO<MemberModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		//DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		//String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<MemberModel> accts = memberService.fetchAllMemberList(tableUtils);
		int i=1;
		for(MemberModel data: accts)
		{
			data.setSrNo((i++)+tableUtils.getiDisplayStart());
			data.setAction("<a href='javascript:void(0);' onclick=editMember('"+ data.getMemberId()+ "');>Edit &nbsp; <i class='fa fa-edit font-size-17px'></i></a>");
			if(data.getRecordStatus().equals("A")){
				data.setAction(data.getAction()+"&nbsp; <a href='javascript:void(0);' onclick=changeMemberStatus('"+ data.getMemberId()+ "');>Deactivate &nbsp;<i class='fa fa-times font-size-17px'></i></a>");
			}
			else 
			{
				data.setAction(data.getAction()+"&nbsp; <a href='javascript:void(0);' onclick=changeMemberStatus('"+ data.getMemberId()+ "');>Activate &nbsp; <i class='fa fa-check-circle font-size-17px'></i></a>");
			}
		}
		long count =  memberService.fetchTotalMemberCountList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String addMember(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		MemberModel memberModel= new MemberModel();
		model.addAttribute("memberModel",memberModel);
		List<FranchiseDto> franchiseList = franchiseService.fetchAllFranchiseList();
		model.addAttribute("franchiseList", franchiseList);
		return "add-member";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String postAddMember(Model model,@Validated MemberModel memberModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		if(memberModel.getDob()!=null && !memberModel.getDob().equals(""))
			memberModel.setDobForDb(DateUtils.getMilesecFromDateStr(memberModel.getDob(), "dd/MM/yyyy", "IST"));
		
		if (result.hasErrors()) {
			model.addAttribute("memberModel",memberModel);
			List<FranchiseDto> franchiseList = franchiseService.fetchAllFranchiseList();
			model.addAttribute("franchiseList", franchiseList);
			return "add-member";
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		
		if(memberModel.getFranchiseeId()!=null && !memberModel.getFranchiseeId().equals(""))
			memberModel.setFranchiseeName(franchiseService.fetchFranchiseDetail(memberModel.getFranchiseeId()).getFranchiseeName());
		
		int status =memberService.postAddMember(memberModel, userId);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}
		return pageRedirect("/member");
	}
	
	@RequestMapping(value = "/edit-member/{memberId}", method = RequestMethod.GET)
	public String editEmployee(Model model, @PathVariable("memberId") String memberId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		MemberModel memberModel = memberService.fetchMemberDetail(memberId);
		model.addAttribute("memberModel", memberModel);
		List<FranchiseDto> franchiseList = franchiseService.fetchAllFranchiseList();
		model.addAttribute("franchiseList", franchiseList);
		model.addAttribute("franchiseeId", memberModel.getFranchiseeId());
		if(memberModel.getDobForDb()!= 0)
			memberModel.setDob(DateUtils.dbTimeStampToSesionDate(memberModel.getDobForDb(), "IST", "dd/MM/yyyy") );
		return "/edit-member";
	}
	
	@RequestMapping(value = "/edit-member", method = RequestMethod.POST)
	public String editMemberPost(Model model, @Validated MemberModel memberModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		
		if(memberModel.getDob()!=null && !memberModel.getDob().equals(""))
			memberModel.setDobForDb(DateUtils.getMilesecFromDateStr(memberModel.getDob(), "dd/MM/yyyy", "IST"));
		
		if (!dbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		if (result.hasErrors()) {
			model.addAttribute("memberModel",memberModel);
			List<FranchiseDto> franchiseList = franchiseService.fetchAllFranchiseList();
			model.addAttribute("franchiseList", franchiseList);
			model.addAttribute("franchiseeId", memberModel.getFranchiseeId());
			if(memberModel.getDobForDb()!= 0)
				memberModel.setDob(DateUtils.dbTimeStampToSesionDate(memberModel.getDobForDb(), "IST", "dd/MM/yyyy") );
			return "edit-member";
		}
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		memberModel.setUpdatedBy(userId);
		
		if(memberModel.getFranchiseeId()!=null && !memberModel.getFranchiseeId().equals(""))
			memberModel.setFranchiseeName(franchiseService.fetchFranchiseDetail(memberModel.getFranchiseeId()).getFranchiseeName());
		if(memberModel.getDob()!=null && !memberModel.getDob().equals(""))
			memberModel.setDobForDb(DateUtils.getMilesecFromDateStr(memberModel.getDob(), "dd/MM/yyyy", "IST"));
		
		int status =memberService.editMember(memberModel);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}

		return pageRedirect("/member");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/validations/form-validation.js","js/viewjs/member.js" };
	}

	
}
