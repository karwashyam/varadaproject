package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.FranchiseDto;
import com.webapp.models.BookingModel;
import com.webapp.models.FranchiseModel;
import com.webapp.models.MemberModel;
import com.webapp.models.PaymentModel;
import com.webapp.models.PenaltyModel;
import com.webapp.models.ProjectModel;
import com.webapp.models.TransferModel;
import com.webapp.services.BookingService;
import com.webapp.services.FranchiseService;
import com.webapp.services.MemberService;
import com.webapp.services.PaymentService;
import com.webapp.services.ProjectSerivce;
import com.webapp.validator.BookingValidator;

@Controller
@RequestMapping("/booking")
public class BookingController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ProjectSerivce projectSerivce;
	
	@Autowired
	private FranchiseService franchiseService;
	
	@Autowired
	private MemberService memberService;
		
	@Autowired
	private BookingValidator bookingValidator;
	
	@InitBinder("bookingModel")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(bookingValidator);
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
		return "booking";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String addCity(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		BookingModel bookingModel= new BookingModel();
		model.addAttribute("bookingModel",bookingModel);
		List<ProjectModel> projectModel= projectSerivce.fetchProjects();
		model.addAttribute("projectModel",projectModel);
		List<MemberModel> memberModelList = memberService.fetchMembersList();
		model.addAttribute("memberModelList",memberModelList);
		
		List<FranchiseDto> franchiseeModelList = franchiseService.fetchAllFranchiseList();
		model.addAttribute("franchiseeModelList",franchiseeModelList);
		
		return "add-booking";
	}
	
	@RequestMapping(value = "/view/{bookingId}",method = RequestMethod.GET)
	public String viewBooking(@PathVariable("bookingId")String bookingId,Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		BookingModel bookingModel= bookingService.getBookingDetailsById(bookingId);
		bookingModel.setNomineeDob(DateUtils.fetchDateStrFromMilisec(bookingModel.getNomineeDobLong(), "IST", "dd/MM/yyyy"));
		bookingModel.setBookingDate(DateUtils.fetchDateStrFromMilisec(bookingModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
		bookingModel.setTodayDate(DateUtils.fetchDateStrFromMilisec(DateUtils.nowAsGmtMillisec(), "IST", "dd/MM/yyyy"));
		model.addAttribute("bookingModel",bookingModel);
		Long unclearAmount=bookingService.getUnclearAmount(bookingId);
		if(unclearAmount==null){
			unclearAmount=0l;
		}
		model.addAttribute("unclearAmount", unclearAmount);
		List<PaymentModel> paymentModelList= bookingService.getPaymentDetailsByBookingId(bookingId);
		for (PaymentModel paymentModel : paymentModelList) {
			paymentModel.setEmiDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getEmiDate(), "IST", "dd/MM/yyyy"));
			if(paymentModel.getChequeDate()>0){
				paymentModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getChequeDate(), "IST", "dd/MM/yyyy"));
			}
		}
		
		model.addAttribute("paymentModelList",paymentModelList);
		
		return "view-booking";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String addBooking(Model model,@Validated BookingModel bookingModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		
//		if (result.hasErrors()) {
//			model.addAttribute("bookingModel",bookingModel);
//			return "add-booking";
//			model.addAttribute("cityModel",cityModel);
//			List<State> stateList = stateService.fetchAllStateList();
//			model.addAttribute("stateModel", stateList);
//			return "add-city";
//		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		int status =bookingService.addBooking(bookingModel,userId);
		if(status>0){
				model.addAttribute("msg", "Booking added successfully");
		}else{
			model.addAttribute("msg", "Failed to add booking");
		}
		return "booking";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<BookingModel> showOrders(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<BookingModel> dt = new DataTablesTO<BookingModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		List<BookingModel> accts = bookingService.fetchBookingList(tableUtils);

		long count =  bookingService.fetchTotalBookingList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/fetch/{projectId}", produces = "application/json")
	public @ResponseBody HashMap<String, Object> fetchProjPaymentSchemeANDPlots(@PathVariable("projectId") String projectId, HttpServletRequest req, HttpServletResponse res) {

		return bookingService.fetchProjPaymentSchemeANDPlots(projectId);
	}
	
	@RequestMapping(value = "/allotment-letter/{bookingId}",method = RequestMethod.GET)
	public String printAllotmentLetter(@PathVariable("bookingId") String bookingId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		bookingService.changeAllotmentLetterGiven(bookingId);
		BookingModel bookingModel= bookingService.getBookingDetailsById(bookingId);
		bookingModel.setNomineeDob(DateUtils.fetchDateStrFromMilisec(bookingModel.getNomineeDobLong(), "IST", "dd/MM/yyyy"));
		bookingModel.setBookingDate(DateUtils.fetchDateStrFromMilisec(bookingModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
		bookingModel.setTodayDate(DateUtils.fetchDateStrFromMilisec(DateUtils.nowAsGmtMillisec(), "IST", "dd/MM/yyyy"));
		model.addAttribute("bookingModel",bookingModel);
		return "allotment-letter";
	}
	
	@RequestMapping(value = "/add-payment/{bookingId}",method = RequestMethod.GET)
	public String addPayment(@PathVariable("bookingId") String bookingId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		BookingModel bookingModel= bookingService.getBookingDetailsById(bookingId);
		bookingModel.setNomineeDob(DateUtils.fetchDateStrFromMilisec(bookingModel.getNomineeDobLong(), "IST", "dd/MM/yyyy"));
		bookingModel.setBookingDate(DateUtils.fetchDateStrFromMilisec(bookingModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
		bookingModel.setTodayDate(DateUtils.fetchDateStrFromMilisec(DateUtils.nowAsGmtMillisec(), "IST", "dd/MM/yyyy"));
		model.addAttribute("bookingModel",bookingModel);
		List<PaymentModel> paymentModelList= bookingService.getPaymentDetailsByBookingId(bookingId);
		for (PaymentModel paymentModel : paymentModelList) {
			paymentModel.setEmiDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getEmiDate(), "IST", "dd/MM/yyyy"));
			if(paymentModel.getChequeDate()>0){
				paymentModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getChequeDate(), "IST", "dd/MM/yyyy"));
			}
		}
		
		model.addAttribute("paymentModelList",paymentModelList);
		PenaltyModel penaltyModel=new PenaltyModel();
		penaltyModel.setBookingId(bookingModel.getBookingId());
		model.addAttribute("penaltyModel",penaltyModel);
		return "add-payment";
	}
	
	@RequestMapping(value = "/edit-payment/{paymentId}",method = RequestMethod.GET)
	public String editPayment(@PathVariable("paymentId") String paymentId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		PaymentModel paymentModel1= paymentService.getPaymentDetailsById(paymentId);
		if(paymentModel1.getPaymentMode().equalsIgnoreCase("Online")){
			paymentModel1.setChequeNumber(paymentModel1.getTransactionNumber());
		}
		paymentModel1.setEmiDateString(DateUtils.fetchDateStrFromMilisec(paymentModel1.getEmiDate(), "IST", "dd/MM/yyyy"));
		if(paymentModel1.getChequeDate()>0)
		paymentModel1.setChequeDateString(DateUtils.fetchDateStrFromMilisec(paymentModel1.getChequeDate(),"IST", "dd/MM/yyyy"));
		
		model.addAttribute("paymentModel",paymentModel1);
		BookingModel bookingModel= bookingService.getBookingDetailsById(paymentModel1.getBookingId());
		bookingModel.setNomineeDob(DateUtils.fetchDateStrFromMilisec(bookingModel.getNomineeDobLong(), "IST", "dd/MM/yyyy"));
		bookingModel.setBookingDate(DateUtils.fetchDateStrFromMilisec(bookingModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
		bookingModel.setTodayDate(DateUtils.fetchDateStrFromMilisec(DateUtils.nowAsGmtMillisec(), "IST", "dd/MM/yyyy"));
		model.addAttribute("bookingModel",bookingModel);
		List<PaymentModel> paymentModelList= bookingService.getPaymentDetailsByBookingId(paymentModel1.getBookingId());
		for (PaymentModel paymentModel : paymentModelList) {
			paymentModel.setEmiDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getEmiDate(), "IST", "dd/MM/yyyy"));
			if(paymentModel.getChequeDate()>0){
				paymentModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getChequeDate(), "IST", "dd/MM/yyyy"));
			}
		}
		
		model.addAttribute("paymentModelList",paymentModelList);
		return "edit-payment";
	}
	
	@RequestMapping(value = "/cancel-booking/{bookingId}",method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView cancelBooking(@PathVariable("bookingId") String bookingId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		int status = bookingService.cancelBooking(bookingId,userId);
		Map<String, Object> outputFinalMap= new HashMap<String,Object>();
		if (status ==0) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );
	}
	
	@RequestMapping(value = "/transfer-booking/{bookingId}/{memberId}",method = RequestMethod.GET)
	public @ResponseBody ModelAndView transferBookingCheck(@PathVariable("bookingId") String bookingId,@PathVariable("memberId") String memberId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		boolean status = bookingService.transferBookingCheck(bookingId,memberId);
		Map<String, Object> outputFinalMap= new HashMap<String,Object>();
		if (!status) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );
	}
	
	@RequestMapping(value = "/transfer/{bookingId}/{memberId}",method = RequestMethod.GET)
	public String transferBooking(@PathVariable("bookingId") String bookingId,@PathVariable("memberId") String memberId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		List<Map<String,Object>> bookingIds = bookingService.transferBookingIds(bookingId,memberId);
		TransferModel transferModel = new TransferModel();
		transferModel.setMemberBookingId(bookingId);
		transferModel.setMemberId(memberId);
		model.addAttribute("bookingIdList",bookingIds);
		model.addAttribute("transferModel",transferModel);
		BookingModel bookingModel= bookingService.getBookingDetailsById(bookingId);
		bookingModel.setNomineeDob(DateUtils.fetchDateStrFromMilisec(bookingModel.getNomineeDobLong(), "IST", "dd/MM/yyyy"));
		bookingModel.setBookingDate(DateUtils.fetchDateStrFromMilisec(bookingModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
		bookingModel.setTodayDate(DateUtils.fetchDateStrFromMilisec(DateUtils.nowAsGmtMillisec(), "IST", "dd/MM/yyyy"));
		model.addAttribute("bookingModel",bookingModel);
		return "transfer-booking";
	}
	
	@RequestMapping(value = "/transfer",method = RequestMethod.POST)
	public String transferBookingPost(Model model, TransferModel transferModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException { 
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		bookingService.transferBookingPost(transferModel,userId);
		return "redirect:/booking/view/"+transferModel.getMemberBookingId();
	}
	
	@RequestMapping(value = "/add-payment",method = RequestMethod.POST)
	public String addPayment(Model model, BookingModel bookingModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException { 
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		bookingService.addPayment(bookingModel,userId);
		return "redirect:/booking/view/"+bookingModel.getBookingId();
	}
	
	@RequestMapping(value = "/edit-payment",method = RequestMethod.POST)
	public String editPayment(Model model, PaymentModel paymentModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException { 
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		bookingService.editPayment(paymentModel,userId);
		return "redirect:/booking/view/"+paymentModel.getBookingId();
	}
	
	@RequestMapping(value = "/add-penalty",method = RequestMethod.POST)
	public String addPenalty(Model model, PenaltyModel penaltyModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException { 
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		bookingService.addPenalty(penaltyModel,userId);
		return "redirect:/booking/view/"+penaltyModel.getBookingId();
	}
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/bootstrap/bootstrap-dialog.js","js/viewjs/booking.js" };
	}

	
}
