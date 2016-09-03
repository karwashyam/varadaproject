package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.ProjectPaymentSchemeDto;
import com.webapp.models.BookingModel;
import com.webapp.models.PaymentSchemeModel;
import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPaymentSchemeModel;
import com.webapp.services.PaymentSchemeSerivce;
import com.webapp.services.ProjPaymentSchemeSerivce;
import com.webapp.services.ProjectSerivce;
import com.webapp.validator.ProjPaymentSchemeValidator;

@Controller
@RequestMapping(value="/proj-payment-scheme")
public class ProjPaymentSchemeController extends BusinessController{

	@Autowired
	private ProjPaymentSchemeValidator projPaymentSchemeValidator;
//	
	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	ProjPaymentSchemeSerivce projPaymentSchemeSerivce;
	
	@Autowired
	PaymentSchemeSerivce paymentSchemeSerivce;
	

	@Autowired
	ProjectSerivce projectSerivce;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(projPaymentSchemeValidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String fetchProjPayschemeForl(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		/*DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_VIEW)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}
*/
		req.setAttribute("title", "Project Payment Scheme Management");

		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);

		return "proj-payment-scheme";
	}

	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
//		ProjectModel projectModel=new ProjectModel();
		

		List<ProjectModel> projectsList = new ArrayList<ProjectModel>();
		projectsList = projectSerivce.fetchProjects();
		model.addAttribute("projectsList", projectsList);


		List<PaymentSchemeModel> paymentSchemeList = new ArrayList<PaymentSchemeModel>();
		paymentSchemeList = paymentSchemeSerivce.fetchPaymentScheme();
		model.addAttribute("paymentSchemeList", paymentSchemeList);


		ProjectPaymentSchemeDto projectPaymentSchemeDto=new ProjectPaymentSchemeDto();
		model.addAttribute("projectPaymentSchemeDto", projectPaymentSchemeDto);
		return "add-proj-payment-scheme";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(Model model,@Validated ProjectPaymentSchemeDto projectPaymentSchemeDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		long currentTime = new Date().getTime();


			if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
				String url ="/login.do";
				return "redirect:" + url;
			}
			if (result.hasErrors()) {
				model.addAttribute("projectPaymentSchemeDto", projectPaymentSchemeDto);
				return "add-proj-payment-scheme";
			}
			DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
			String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

			model.addAttribute("projectPaymentSchemeDto",projectPaymentSchemeDto);
			String projectPaymentSchemeId = UUIDGenerator.generateUUID();

			ProjectPaymentSchemeModel paymentSchemeModel=new ProjectPaymentSchemeModel();
			
			paymentSchemeModel.setProjectPaymentSchemeId(projectPaymentSchemeId);
			paymentSchemeModel.setPaymentSchemeTitle(paymentSchemeModel.getPaymentSchemeTitle());
			paymentSchemeModel.setPaymentSchemeId(projectPaymentSchemeDto.getPaymentSchemeId());
			paymentSchemeModel.setProjectTitle(projectPaymentSchemeDto.getProjectTitle());
			paymentSchemeModel.setProjectId(projectPaymentSchemeDto.getProjectId());

			
			projPaymentSchemeSerivce.addProjectPaymentScheme(paymentSchemeModel, userId);

		return pageRedirect("/proj-payment-scheme.do");
	}
	
	@RequestMapping(value = "/edit/{projPaymentSchemeId}", method = RequestMethod.GET)
	public String editForm(Model model, @PathVariable("projPaymentSchemeId") String projPaymentSchemeId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}


		ProjectPaymentSchemeModel projpaymentSchemeModel = projPaymentSchemeSerivce.getProjectsPaymentSchemeById(projPaymentSchemeId);

		ProjectPaymentSchemeDto projectModelDto=new ProjectPaymentSchemeDto();
		projectModelDto.setProjectPaymentSchemeId(projPaymentSchemeId);
		projectModelDto.setProjectId(projpaymentSchemeModel.getProjectId());
		projectModelDto.setProjectTitle(projpaymentSchemeModel.getProjectTitle());
		projectModelDto.setPaymentSchemeId(projpaymentSchemeModel.getPaymentSchemeId());
		projectModelDto.setPaymentSchemeTitle(projpaymentSchemeModel.getPaymentSchemeTitle());

		model.addAttribute("projectPaymentSchemeDto", projectModelDto);
		
		List<ProjectModel> projectsList = new ArrayList<ProjectModel>();
		projectsList = projectSerivce.fetchProjects();
		model.addAttribute("projectsList", projectsList);


		List<PaymentSchemeModel> paymentSchemeList = new ArrayList<PaymentSchemeModel>();
		paymentSchemeList = paymentSchemeSerivce.fetchPaymentScheme();
		model.addAttribute("paymentSchemeList", paymentSchemeList);

		
		model.addAttribute("paymentSchemeTitle", projpaymentSchemeModel.getPaymentSchemeTitle());
		model.addAttribute("projPaymentSchemeId", projpaymentSchemeModel.getProjectPaymentSchemeId());
		model.addAttribute("paymentSchId", projpaymentSchemeModel.getPaymentSchemeId());
		model.addAttribute("projId", projpaymentSchemeModel.getProjectId());
		model.addAttribute("projectTitle", projpaymentSchemeModel.getProjectTitle());

		
		return "edit-proj-payment-scheme";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editFormPost(Model model, ProjectPaymentSchemeDto projectPaymentSchemeDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("\n\t\t ----edit===>"+projectPaymentSchemeDto.getProjectPaymentSchemeId());
		preprocessRequest(model, req, res);

		/*if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_ADD)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}
*/
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		ProjectPaymentSchemeModel paymentSchemeModel=new ProjectPaymentSchemeModel();
		
		paymentSchemeModel.setPaymentSchemeId(projectPaymentSchemeDto.getPaymentSchemeId());
		paymentSchemeModel.setProjectPaymentSchemeId(projectPaymentSchemeDto.getProjectPaymentSchemeId());
		paymentSchemeModel.setProjectId(projectPaymentSchemeDto.getProjectId());

		projPaymentSchemeSerivce.updateProjectPaymentScheme(paymentSchemeModel, userId);

		return pageRedirect("/proj-payment-scheme.do");
	}
	
	
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js", "js/viewjs/add-proj-payment-scheme.js","js/vendor/bootstrap-filestyle.min.js","js/viewjs/proj-payscheme_management.js",
				"js/bootstrap/bootstrap-dialog.js"};
	}


	
	
}
