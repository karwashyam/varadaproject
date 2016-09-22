package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.Date;

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

import com.fnf.utils.UUIDGenerator;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.PaymentSchemeDto;
import com.webapp.models.PaymentSchemeModel;
import com.webapp.services.PaymentSchemeSerivce;
import com.webapp.validator.PaymentSchemeValidator;

@Controller
@RequestMapping(value="/payment-scheme")
public class PaymentSchemeController extends BusinessController{

	@Autowired
	private PaymentSchemeValidator paymentSchemeValidator;
//	
	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	PaymentSchemeSerivce paymentSchemeSerivce;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(paymentSchemeValidator);
	}
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
//		ProjectModel projectModel=new ProjectModel();
		PaymentSchemeDto paymentSchemeDto=new PaymentSchemeDto();
		model.addAttribute("paymentSchemeDto", paymentSchemeDto);
		return "add-payment-scheme";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(Model model,@Validated PaymentSchemeDto paymentSchemeDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		long currentTime = new Date().getTime();


			if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
				String url ="/login.do";
				return "redirect:" + url;
			}
			if (result.hasErrors()) {
				model.addAttribute("paymentSchemeDto", paymentSchemeDto);
				return "add-payment-scheme";
			}
			DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
			String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

			model.addAttribute("paymentSchemeDto",paymentSchemeDto);
			String paymentSchemeId = UUIDGenerator.generateUUID();

			PaymentSchemeModel paymentSchemeModel=new PaymentSchemeModel();
			
			paymentSchemeModel.setPaymentSchemeId(paymentSchemeId);
			paymentSchemeModel.setTitle(paymentSchemeDto.getTitle());
			paymentSchemeModel.setDownPayment(paymentSchemeDto.getDownPayment());
			paymentSchemeModel.setInterestRate(paymentSchemeDto.getInterestRate());
			paymentSchemeModel.setNoOfMonths(paymentSchemeDto.getNoOfMonths());
			paymentSchemeModel.setPrepaymentPossible(paymentSchemeDto.isPrepaymentPossible());
			paymentSchemeSerivce.addPaymentScheme(paymentSchemeModel, userId);

		return pageRedirect("/payment-scheme.do");
	}
	
	@RequestMapping(value = "/edit/{paymentSchemeId}", method = RequestMethod.GET)
	public String editForm(Model model, @PathVariable("paymentSchemeId") String paymentSchemeId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		
		PaymentSchemeModel paymentSchemeModel = paymentSchemeSerivce.getPaymentSchemeDetailsById(paymentSchemeId);

		PaymentSchemeDto projectModelDto=new PaymentSchemeDto();
		projectModelDto.setPaymentSchemeId(paymentSchemeId);
		projectModelDto.setTitle(paymentSchemeModel.getTitle());
		projectModelDto.setDownPayment(paymentSchemeModel.getDownPayment());
		projectModelDto.setInterestRate(paymentSchemeModel.getInterestRate());
		projectModelDto.setNoOfMonths(paymentSchemeModel.getNoOfMonths());

		model.addAttribute("paymentSchemeDto", projectModelDto);
		
		model.addAttribute("title", paymentSchemeModel.getTitle());
		model.addAttribute("paymentSchemeId", paymentSchemeModel.getPaymentSchemeId());
		model.addAttribute("downPayment", paymentSchemeModel.getDownPayment());
		model.addAttribute("interestRate", paymentSchemeModel.getInterestRate());
		model.addAttribute("noOfMonths", paymentSchemeModel.getNoOfMonths());

		
		return "edit-payment-scheme";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editFormPost(Model model,@Validated PaymentSchemeDto paymentSchemeDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		System.out.println("\n\n\t result.hasErrors()--->"+result.hasErrors());
		if (result.hasErrors()) {
			model.addAttribute("paymentSchemeDto", paymentSchemeDto);
			return "edit-payment-scheme";
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		PaymentSchemeModel paymentSchemeModel=new PaymentSchemeModel();
		
		paymentSchemeModel.setPaymentSchemeId(paymentSchemeDto.getPaymentSchemeId());
		paymentSchemeModel.setTitle(paymentSchemeDto.getTitle());
		paymentSchemeModel.setDownPayment(paymentSchemeDto.getDownPayment());
		paymentSchemeModel.setInterestRate(paymentSchemeDto.getInterestRate());
		paymentSchemeModel.setNoOfMonths(paymentSchemeDto.getNoOfMonths());
		paymentSchemeModel.setPrepaymentPossible(paymentSchemeDto.isPrepaymentPossible());
		
		paymentSchemeSerivce.updatePaymentScheme(paymentSchemeModel, userId);

		return pageRedirect("/payment-scheme.do");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js", "js/viewjs/add-payment-scheme.js","js/vendor/bootstrap-filestyle.min.js"};
	}


	
	
}
