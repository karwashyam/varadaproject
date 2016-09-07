package com.webapp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.BookingDao;
import com.webapp.daos.MemberDao;
import com.webapp.daos.PaymentDao;
import com.webapp.daos.PaymentSchemeDao;
import com.webapp.daos.ProjectDao;
import com.webapp.models.BookingModel;
import com.webapp.models.MemberModel;
import com.webapp.models.PaymentModel;
import com.webapp.models.PaymentSchemeModel;
import com.webapp.models.PenaltyModel;
import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPlotsModel;
import com.webapp.models.TransferModel;

@Service("bookingService")
public class BookingService {

	private static final Logger logger = Logger.getLogger(BookingService.class);

	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private PaymentSchemeDao paymentSchemeDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private MemberDao memberDao;
	

	public List<BookingModel> fetchBookingList(JQTableUtils tableUtils) {
		return bookingDao.fetchBookingList(tableUtils);
	}


	public long fetchTotalBookingList(JQTableUtils tableUtils) {
		return bookingDao.fetchTotalBookingList(tableUtils);
	}

	public long fetchTotalBookingListByDate(int iDisplayLength, int iDisplayStart, int serialNo, String sSortDir, String columnName, String sSearch, Map<String, Object> inputMap) {
	
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);

		return bookingDao.fetchTotalBookingListByDate(inputMap);

	}

	public List<BookingModel> fetchBookingListByCurrentYear(
			Map<String, Object> newRequestMap) {
		return bookingDao.fetchBookingListByCurrentYear(newRequestMap);
	}
	@Transactional
	public HashMap<String, Object> fetchProjPaymentSchemeANDPlots(String projectId) {
		
		List<PaymentSchemeModel> paymentSchemeList=paymentSchemeDao.fetchPaymentSchemeForProject(projectId);
		List<ProjectPlotsModel> plotList = projectDao.fetchProjectAllPlotsList(projectId);
		
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("paymentSchemeList", paymentSchemeList);
		outputMap.put("plotList", plotList);
		return outputMap;
	}

	@Transactional
	public int addBooking(BookingModel bookingModel, String userId) {
		long time = DateUtils.nowAsGmtMillisec();
		
		bookingModel.setCreatedBy(userId);
		bookingModel.setUpdatedAt(time);
		bookingModel.setUpdatedBy(userId);
		bookingModel.setCreatedAt(time);
		bookingModel.setBookingId(UUIDGenerator.generateUUID());
		ProjectModel projectModel = null;
		projectModel = projectDao.getProjectDetailsById(bookingModel.getProjectId());
		bookingModel.setProjectName(projectModel.getTitle());
		String[] plotDetails=bookingModel.getPlotId().split("_");
		bookingModel.setPlotId(plotDetails[0]);
		bookingModel.setPlotSize(plotDetails[1]);
		bookingModel.setPlotName(plotDetails[2]);
		String[] memberDetails=bookingModel.getMemberId().split("_");
		bookingModel.setMemberId(memberDetails[0]);
		bookingModel.setMemberName(memberDetails[1]);
		String[] paymentScheme=bookingModel.getPaymentSchemeId().split("_");
		bookingModel.setPaymentSchemeId(paymentScheme[0]);
		bookingModel.setNoOfEmi(Long.parseLong(paymentScheme[1]));
		bookingModel.setInterestRate(Long.parseLong(paymentScheme[2]));
		MemberModel memberModel=memberDao.fetchMemberById(memberDetails[0]);
		bookingModel.setFranchiseeId(memberModel.getFranchiseeId());
		bookingModel.setFatherName(memberModel.getFranchiseeName());
		bookingModel.setNomineeDobLong(DateUtils.getMilesecFromDateStr(bookingModel.getNomineeDob(), "dd/mm/yyyy", "GMT"));
		bookingModel.setNextEmiOn(DateUtils.getGmtMillisecAfterMonths(ProjectConstant.ONE_MONTH));
		bookingModel.setRemainingPayment(0l);
		if(projectModel.getLastBookingPostfix()==null){
			projectModel.setLastBookingPostfix(1l);
		}else{
			projectModel.setLastBookingPostfix(projectModel.getLastBookingPostfix()+1);
		}
		bookingModel.setBookingCode(projectModel.getBookingPrefix()+"-"+String.format("%04d", projectModel.getLastBookingPostfix()));
		
		projectDao.updateLastBookingCode(projectModel);
		bookingDao.addBooking(bookingModel);
		projectDao.updatePlotStatus(bookingModel.getPlotId());
		
		List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
		if(bookingModel.getDownPayment()>0){
			PaymentModel paymentModel = new PaymentModel();
			paymentModel.setPaymentId(UUIDGenerator.generateUUID());
			paymentModel.setMemberId(bookingModel.getMemberId());
			paymentModel.setMemberName(bookingModel.getMemberName());
			paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
			paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
			paymentModel.setBookingId(bookingModel.getBookingId());
			paymentModel.setCreatedBy(userId);
			paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
			paymentModel.setUpdatedBy(userId);
			paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
			paymentModel.setEmiDate(DateUtils.nowAsGmtMillisec());
			paymentModel.setDescription("Down Payment");
			paymentModel.setPaymentAmount(bookingModel.getDownPayment());
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_ADDED_EMI);
			bookingDao.changePaidPayment(paymentModel.getBookingId(),0l,paymentModel.getPaymentAmount());
			paymentModelList.add(paymentModel);
			
		}
		if(bookingModel.getDownPayment()>0){
			PaymentModel paymentModel = new PaymentModel();
			paymentModel.setPaymentId(UUIDGenerator.generateUUID());
			paymentModel.setMemberId(bookingModel.getMemberId());
			paymentModel.setMemberName(bookingModel.getMemberName());
			paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
			paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
			paymentModel.setBookingId(bookingModel.getBookingId());
			paymentModel.setCreatedBy(userId);
			paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
			paymentModel.setUpdatedBy(userId);
			paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
			paymentModel.setEmiDate(DateUtils.getMilesecFromDateStr(bookingModel.getPaymentDate(), "dd/MM/yyyy", "GMT"));
			paymentModel.setDescription(bookingModel.getDescription());
			paymentModel.setPaymentAmount(bookingModel.getDownPayment());
			if(bookingModel.getPaymentMethod().equalsIgnoreCase("Cash")){
				paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
				paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
				paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
				bookingDao.changePaidPayment(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount());
			}else if(bookingModel.getPaymentMethod().equalsIgnoreCase("Cheque")){
				paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
				paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_UNCLEARED);
				paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
				
				paymentModel.setChequeDate(DateUtils.getMilesecFromDateStr(bookingModel.getPaymentDate(), "dd/mm/yyyy", "GMT"));
				paymentModel.setChequeNumber(bookingModel.getChequeNo());
				paymentModel.setBank(bookingModel.getBank());
				paymentModel.setAccountHolder(bookingModel.getAccountHolder());
			}else if(bookingModel.getPaymentMethod().equalsIgnoreCase("Online")){
				paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
				paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
				paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
				
				paymentModel.setTransactionNumber(bookingModel.getChequeNo());
				paymentModel.setBank(bookingModel.getBank());
				paymentModel.setAccountHolder(bookingModel.getAccountHolder());
				bookingDao.changePaidPayment(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount());
			}
			paymentModelList.add(paymentModel);
		}
		for (int i=0;i<bookingModel.getNoOfEmi();i++) {
			PaymentModel paymentModel = new PaymentModel();
			paymentModel.setPaymentId(UUIDGenerator.generateUUID());
			paymentModel.setMemberId(bookingModel.getMemberId());
			paymentModel.setMemberName(bookingModel.getMemberName());
			paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
			paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
			paymentModel.setBookingId(bookingModel.getBookingId());
			paymentModel.setCreatedBy(userId);
			paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
			paymentModel.setUpdatedBy(userId);
			paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
			paymentModel.setEmiDate(DateUtils.getGmtMillisecAfterMonths(i+1));
			paymentModel.setDescription("EMI "+(i+1));
			paymentModel.setPaymentAmount(bookingModel.getDownPayment());
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_FUTURE_EMI);
			paymentModelList.add(paymentModel);
		}
		int status=paymentDao.addPayments(paymentModelList);
		return status;
	}


	public BookingModel getBookingDetailsById(String bookingId) {
		return bookingDao.getBookingDetailsById(bookingId);
	}


	public List<PaymentModel> getPaymentDetailsByBookingId(String bookingId) {
		return paymentDao.getPaymentDetailsByBookingId(bookingId);
	}


	public void changeAllotmentLetterGiven(String bookingId) {
		bookingDao.changeAllotmentLetterGiven(bookingId);
		
	}


	@Transactional
	public int cancelBooking(String bookingId, String userId) {
		BookingModel bookingModel=bookingDao.getBookingDetailsById(bookingId);
		bookingModel.setUpdatedBy(userId);
		bookingModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		bookingDao.cancelBooking(bookingModel);
		
		long paymentPaid=(long) (bookingModel.getPaymentMadeTillNow()-0.2*bookingModel.getPaymentMadeTillNow());
		List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setPaymentId(UUIDGenerator.generateUUID());
		paymentModel.setMemberId(bookingModel.getMemberId());
		paymentModel.setMemberName(bookingModel.getMemberName());
		paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
		paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
		paymentModel.setBookingId(bookingModel.getBookingId());
		paymentModel.setCreatedBy(userId);
		paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setUpdatedBy(userId);
		paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setEmiDate(DateUtils.nowAsGmtMillisec());
		paymentModel.setDescription("Payment Refunded (20% Deducted)");
		paymentModel.setPaymentAmount(paymentPaid);
		paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
		paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_ADDED_EMI);
		bookingDao.changePaidPaymentForCancelledBooking(paymentModel.getBookingId());
		paymentModelList.add(paymentModel);
		int status=paymentDao.addPayments(paymentModelList);
		projectDao.updatePlotStatusToAvailable(bookingModel.getPlotId());
		paymentDao.disableFuturePayment(paymentModel);
		return status;
		
	}


	public boolean transferBookingCheck(String bookingId,String memberId) {
		return bookingDao.transferBookingCheck(bookingId,memberId);
	}


	public List<Map<String, Object>> transferBookingIds(String bookingId, String memberId) {
		return bookingDao.transferBookingIds(bookingId,memberId);
	}

	@Transactional
	public int transferBookingPost(TransferModel transferModel, String userId) {
		
		BookingModel bookingModel=bookingDao.getBookingDetailsById(transferModel.getMemberBookingId());
		bookingModel.setUpdatedBy(userId);
		bookingModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		bookingDao.cancelBooking(bookingModel);
		
		BookingModel newBookingModel=bookingDao.getBookingDetailsById(transferModel.getBookingId());
		
		long paymentPaid=(long) (bookingModel.getPaymentMadeTillNow());
		List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
		
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setPaymentId(UUIDGenerator.generateUUID());
		paymentModel.setMemberId(bookingModel.getMemberId());
		paymentModel.setMemberName(bookingModel.getMemberName());
		paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
		paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
		paymentModel.setBookingId(bookingModel.getBookingId());
		paymentModel.setCreatedBy(userId);
		paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setUpdatedBy(userId);
		paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setEmiDate(DateUtils.nowAsGmtMillisec());
		paymentModel.setDescription("Amount transfered to Booking No. "+newBookingModel.getBookingCode());
		paymentModel.setPaymentAmount(paymentPaid);
		paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
		paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_ADDED_EMI);
		bookingDao.changePaidPaymentForCancelledBooking(paymentModel.getBookingId());
		paymentModelList.add(paymentModel);
		projectDao.updatePlotStatusToAvailable(bookingModel.getPlotId());
		paymentDao.disableFuturePayment(paymentModel);
		
		PaymentModel newPayemntModel = new PaymentModel();
		newPayemntModel.setPaymentId(UUIDGenerator.generateUUID());
		newPayemntModel.setMemberId(newBookingModel.getMemberId());
		newPayemntModel.setMemberName(newBookingModel.getMemberName());
		newPayemntModel.setFranchiseeId(newBookingModel.getFranchiseeId());
		newPayemntModel.setFranchiseeName(newBookingModel.getFranchiseeName());
		newPayemntModel.setBookingId(newBookingModel.getBookingId());
		newPayemntModel.setCreatedBy(userId);
		newPayemntModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		newPayemntModel.setUpdatedBy(userId);
		newPayemntModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
		newPayemntModel.setEmiDate(DateUtils.nowAsGmtMillisec());
		newPayemntModel.setDescription("Amount transfered from Booking No. "+bookingModel.getBookingCode());
		newPayemntModel.setPaymentAmount(paymentPaid);
		newPayemntModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
		newPayemntModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
		bookingDao.changePaidPayment(newPayemntModel.getBookingId(),paymentPaid,-paymentPaid);
		paymentModelList.add(newPayemntModel);
		int status=paymentDao.addPayments(paymentModelList);
		
		return status;
	}

	@Transactional
	public void addPayment(BookingModel bookingModel, String userId) {
		List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setBookingId(bookingModel.getBookingId());
		paymentModel.setDescription(bookingModel.getDescription());
		paymentModel.setPaymentAmount(bookingModel.getAmount());
		paymentModel.setCreatedBy(userId);
		paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setUpdatedBy(userId);
		paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setEmiDate(DateUtils.getMilesecFromDateStr(bookingModel.getPaymentDate(), "dd/MM/yyyy", "GMT"));
		Long receiptNo=paymentDao.getLatestReceiptNo();
		if(receiptNo==null){
			receiptNo=10000l;
		}else{
			receiptNo++;
		}
		paymentModel.setReceiptNo(receiptNo);
		if(bookingModel.getPaymentMethod().equalsIgnoreCase("Cash")){
			paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			bookingDao.changePaidPayment(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount());
		}else if(bookingModel.getPaymentMethod().equalsIgnoreCase("Cheque")){
			paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_UNCLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			
			paymentModel.setChequeDate(DateUtils.getMilesecFromDateStr(bookingModel.getPaymentDate(), "dd/mm/yyyy", "GMT"));
			paymentModel.setChequeNumber(bookingModel.getChequeNo());
			paymentModel.setBank(bookingModel.getBank());
			paymentModel.setAccountHolder(bookingModel.getAccountHolder());
		}else if(bookingModel.getPaymentMethod().equalsIgnoreCase("Online")){
			paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			
			paymentModel.setTransactionNumber(bookingModel.getChequeNo());
			paymentModel.setBank(bookingModel.getBank());
			paymentModel.setAccountHolder(bookingModel.getAccountHolder());
			bookingDao.changePaidPayment(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount());
		}
		bookingModel = bookingDao.getBookingDetailsById(bookingModel.getBookingId());
		
		paymentModel.setPaymentId(UUIDGenerator.generateUUID());
		paymentModel.setMemberId(bookingModel.getMemberId());
		paymentModel.setMemberName(bookingModel.getMemberName());
		paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
		paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
		paymentModelList.add(paymentModel);
		paymentDao.addPayments(paymentModelList);
	}

	@Transactional
	public void addPenalty(PenaltyModel penaltyModel, String userId) {
		List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setBookingId(penaltyModel.getBookingId());
		paymentModel.setDescription(penaltyModel.getDescription());
		paymentModel.setPaymentAmount(penaltyModel.getAmount1());
		paymentModel.setCreatedBy(userId);
		paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setUpdatedBy(userId);
		paymentModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setEmiDate(DateUtils.nowAsGmtMillisec());
		if(penaltyModel.getType().equalsIgnoreCase("penalty")){
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_ADDED_EMI);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
//			bookingDao.changePaidPayment(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount());
			bookingDao.changeDiscount(paymentModel.getBookingId(),0l,paymentModel.getPaymentAmount(),paymentModel.getPaymentAmount());
		}else if(penaltyModel.getType().equalsIgnoreCase("discount")){
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			bookingDao.changeDiscount(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),0l,-paymentModel.getPaymentAmount());
		}
		BookingModel bookingModel = bookingDao.getBookingDetailsById(penaltyModel.getBookingId());
		
		paymentModel.setPaymentId(UUIDGenerator.generateUUID());
		paymentModel.setMemberId(bookingModel.getMemberId());
		paymentModel.setMemberName(bookingModel.getMemberName());
		paymentModel.setFranchiseeId(bookingModel.getFranchiseeId());
		paymentModel.setFranchiseeName(bookingModel.getFranchiseeName());
		paymentModelList.add(paymentModel);
		paymentDao.addPayments(paymentModelList);
	}
		
	public List<Map<String, Object>> fetchBookingListByDate(
			int iDisplayLength, int iDisplayStart, int serialNo, String sSortDir, String columnName, String sSearch, Map<String, Object> inputMap) {
//		return bookingDao.fetchBookingListByDate(inputMap);
//		Map<String, Object> inputMap2 = new HashMap<>();
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);


		List<Map<String, Object> > resultList = bookingDao.fetchBookingListByDate(inputMap);
		for (Map<String, Object> map : resultList) {
			map.put("srNo", serialNo++);
		}


		return resultList;

	}


	public List<String> fethBookedPlotsIdListOfProjects() {
		return bookingDao.fethBookedPlotsIdListOfProjects();
	}


	public List<BookingModel> getBookings() {
		return bookingDao.getBookings();
	}


	public void editPayment(PaymentModel paymentModel, String userId) {

		List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
		PaymentModel oldPaymentModel = paymentDao.getPaymentDetailsById(paymentModel.getPaymentId());
		paymentModel.setBookingId(oldPaymentModel.getBookingId());
		paymentModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		paymentModel.setUpdatedBy(userId);
		paymentModel.setEmiDate(DateUtils.getMilesecFromDateStr(paymentModel.getEmiDateString(), "dd/MM/yyyy", "GMT"));
		if(paymentModel.getPaymentMode().equalsIgnoreCase("Cash")){
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			bookingDao.changePaidPayment(oldPaymentModel.getBookingId(),paymentModel.getPaymentAmount()-oldPaymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount()+oldPaymentModel.getPaymentAmount());
		}else if(paymentModel.getPaymentMode().equalsIgnoreCase("Cheque")){
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_UNCLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			
			paymentModel.setChequeDate(DateUtils.getMilesecFromDateStr(paymentModel.getChequeDateString(), "dd/mm/yyyy", "GMT"));
			if(oldPaymentModel.getStatus().equalsIgnoreCase(ProjectConstant.PAYMENT_STATUS_CLEARED)){
				bookingDao.changePaidPayment(oldPaymentModel.getBookingId(),paymentModel.getPaymentAmount()-oldPaymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount()+oldPaymentModel.getPaymentAmount());
			}
		}else if(paymentModel.getPaymentMode().equalsIgnoreCase("Online")){
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
			
			paymentModel.setTransactionNumber(paymentModel.getChequeNumber());
			paymentModel.setChequeNumber(null);
			bookingDao.changePaidPayment(oldPaymentModel.getBookingId(),paymentModel.getPaymentAmount()-oldPaymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount()+oldPaymentModel.getPaymentAmount());
		}
		paymentDao.editPayment(paymentModel);
	}


	public Long getUnclearAmount(String bookingId) {
		// TODO Auto-generated method stub
		return bookingDao.getUnclearAmount(bookingId);
	}


}