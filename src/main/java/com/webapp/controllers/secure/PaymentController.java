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
import com.webapp.models.BookingModel;
import com.webapp.models.PaymentModel;
import com.webapp.services.BookingService;
import com.webapp.services.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private BookingService bookingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		return "payment";
	}
	
	@RequestMapping(value="/cheque",method = RequestMethod.GET)
	public String unclearCheques(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		return "cheque";
	}
	
	@RequestMapping(value="/add",method = RequestMethod.GET)
	public String addPayment(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		List<BookingModel> bookingModelList= bookingService.getBookings();
		model.addAttribute("bookingModelList",bookingModelList);
		model.addAttribute("bookingModel", new BookingModel());
		return "payment-booking";
	}
	
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<PaymentModel> showOrders(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<PaymentModel> dt = new DataTablesTO<PaymentModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<PaymentModel> accts = paymentService.fetchPaymentList(tableUtils);
		for (PaymentModel paymentModel : accts) {
			paymentModel.setEmiDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getEmiDate(), "IST", "dd/MM/yyyy"));
//			if(paymentModel.getChequeDate()>0){
//				paymentModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getChequeDate(), "IST", "dd/MM/yyyy"));
//			}
		}
		long count =  paymentService.fetchTotalPaymentList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/cheque-list", produces = "application/json")
	public @ResponseBody DataTablesTO<PaymentModel> showCheques(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<PaymentModel> dt = new DataTablesTO<PaymentModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<PaymentModel> accts = paymentService.fetchChequeList(tableUtils);
		for (PaymentModel paymentModel : accts) {
			paymentModel.setEmiDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getEmiDate(), "IST", "dd/MM/yyyy"));
			if(paymentModel.getChequeDate()>0){
				paymentModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(paymentModel.getChequeDate(), "IST", "dd/MM/yyyy"));
			}
		}
		long count =  paymentService.fetchTotalChequeList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/print-receipt/{paymentId}",method = RequestMethod.GET)
	public String printAllotmentLetter(@PathVariable("paymentId") String paymentId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		PaymentModel paymentModel= paymentService.getPaymentDetailsById(paymentId);
//		bookingModel.setNomineeDob(DateUtils.fetchDateStrFromMilisec(bookingModel.getNomineeDobLong(), "IST", "dd/MM/yyyy"));
//		bookingModel.setBookingDate(DateUtils.fetchDateStrFromMilisec(bookingModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
//		bookingModel.setTodayDate(DateUtils.fetchDateStrFromMilisec(DateUtils.nowAsGmtMillisec(), "IST", "dd/MM/yyyy"));
		model.addAttribute("paymentModel",paymentModel);
		return "print-receipt";
	}
	
	@RequestMapping(value = "/clear/{paymentId}",method = RequestMethod.GET)
	public @ResponseBody ModelAndView clearCheque(@PathVariable("paymentId") String paymentId,Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		int status = paymentService.clearCheque(paymentId,userId);
		Map<String, Object> outputFinalMap= new HashMap<String,Object>();
		if (status ==0) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/bootstrap/bootstrap-dialog.js", "js/viewjs/payment.js" };
		//return new String[] { "js/viewjs/city.js" };
	}

	
}
