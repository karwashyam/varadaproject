package com.webapp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPlotsModel;

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
		MemberModel memberModel=memberDao.fetchMemberById(memberDetails[0]);
		bookingModel.setFranchiseeId(memberModel.getFranchiseeId());
		bookingModel.setFatherName(memberModel.getFranchiseeName());
		bookingModel.setNomineeDobLong(DateUtils.getMilesecFromDateStr(bookingModel.getNomineeDob(), "dd/mm/yyyy", "GMT"));
		bookingModel.setNextEmiOn(DateUtils.getGmtMillisecAfterMonths(ProjectConstant.ONE_MONTH));
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
			paymentModel.setEmiDate(DateUtils.nowAsGmtMillisec());
			paymentModel.setDescription(bookingModel.getDescription());
			paymentModel.setPaymentAmount(bookingModel.getDownPayment());
			if(bookingModel.getPaymentMethod().equalsIgnoreCase("Cash")){
				paymentModel.setPaymentMode(bookingModel.getPaymentMethod());
				paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
				paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
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
			paymentModel.setEmiDate(DateUtils.nowAsGmtMillisec());
			paymentModel.setDescription("EMI");
			paymentModel.setPaymentAmount(bookingModel.getDownPayment());
			paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
			paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_FUTURE_EMI);
			paymentModelList.add(paymentModel);
		}
		int status=paymentDao.addPayments(paymentModelList);
		return status;
	}
}