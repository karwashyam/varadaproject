package com.webapp.controllers.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.daos.BookingDao;
import com.webapp.daos.CityDao;
import com.webapp.daos.FranchiseDao;
import com.webapp.daos.MemberDao;
import com.webapp.daos.PaymentDao;
import com.webapp.daos.ProjectDao;
import com.webapp.daos.StateDao;
import com.webapp.models.BookingModel;
import com.webapp.models.FranchiseCommissionModel;
import com.webapp.models.FranchiseModel;
import com.webapp.models.PaymentModel;

@Controller
@RequestMapping("/data")
public class DataInsertionController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private StateDao stateDao;
		
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private FranchiseDao franchiseDao;
	
	/*@RequestMapping(value="/payment",method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		File excelFile = new File("C:/Users/Asus/Videos/revaradabookingolddata/payment_mst.xlsx");
		FileInputStream mreview = new FileInputStream(excelFile);
		XSSFWorkbook metricWorkbook = new XSSFWorkbook(mreview);
		XSSFSheet metricSheet = metricWorkbook.getSheetAt(0);
		Iterator<Row> rowIterator = metricSheet.rowIterator();
		int excel=0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (excel > 0) {
				Cell paymentId = row.getCell(0);
				Cell bookingId = row.getCell(1);
				
				Cell amount = row.getCell(2);
				Cell paymentDate = row.getCell(3);
				Cell paymentMode = row.getCell(4);
				Cell chequeNumber = row.getCell(5);
				Cell chequeDate = row.getCell(6);
				Cell bank = row.getCell(7);
				Cell accountHolder = row.getCell(8);
				Cell isDp = row.getCell(9);
				Cell isPrint = row.getCell(10);
				Cell isReturn = row.getCell(11);
				Cell penalty = row.getCell(12);
				
				long time = DateUtils.nowAsGmtMillisec();
				if(bookingId.getNumericCellValue()>1000){
					

				if(isReturn.getNumericCellValue()>0){
					List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
					
					PaymentModel paymentModel = new PaymentModel();
					String userId="1";
					paymentModel.setCreatedBy(userId);
					paymentModel.setUpdatedAt(time);
					paymentModel.setUpdatedBy(userId);
					paymentModel.setCreatedAt(paymentDate.getDateCellValue().getTime());
					
					
					paymentModel.setBookingId(String.valueOf((long)bookingId.getNumericCellValue()));
					if(penalty!=null)
					paymentModel.setDescription(penalty.getStringCellValue());
					paymentModel.setPaymentAmount((long) amount.getNumericCellValue());
					paymentModel.setEmiDate(paymentModel.getCreatedAt());
					paymentModel.setReceiptNo((long) paymentId.getNumericCellValue());
					paymentModel.setPaymentId(String.valueOf(paymentId.getNumericCellValue()));
					paymentModel.setPaymentMode(paymentMode.getStringCellValue());
					paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_REJECTED);
					paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
					if(chequeDate!=null)
					paymentModel.setChequeDate(chequeDate.getDateCellValue().getTime());
					if(chequeNumber!=null&&"Cheque".equalsIgnoreCase(paymentModel.getPaymentMode())){
						chequeNumber.setCellType(Cell.CELL_TYPE_STRING);
						paymentModel.setChequeNumber(chequeNumber.getStringCellValue());
					}else if(chequeNumber!=null&&"Online".equalsIgnoreCase(paymentModel.getPaymentMode())){
						chequeNumber.setCellType(Cell.CELL_TYPE_STRING);
						paymentModel.setTransactionNumber(chequeNumber.getStringCellValue());
					}
					if(bank!=null)
					paymentModel.setBank(bank.getStringCellValue());
					if(accountHolder!=null)
					paymentModel.setAccountHolder(accountHolder.getStringCellValue());
					
					BookingModel bookingModel1 = bookingDao.getBookingDetailsById(paymentModel.getBookingId());
					if(bookingModel1!=null){
						paymentModel.setMemberId(bookingModel1.getMemberId());
						paymentModel.setMemberName(bookingModel1.getMemberName());
						paymentModel.setFranchiseeId(bookingModel1.getFranchiseeId());
						paymentModel.setFranchiseeName(bookingModel1.getFranchiseeName());
						paymentModelList.add(paymentModel);
						paymentDao.addPayments(paymentModelList);
					}
				}else if(amount.getNumericCellValue()>0){
					List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
					
					PaymentModel paymentModel = new PaymentModel();
					String userId="1";
					paymentModel.setCreatedBy(userId);
					paymentModel.setUpdatedAt(time);
					paymentModel.setUpdatedBy(userId);
					paymentModel.setCreatedAt(paymentDate.getDateCellValue().getTime());
					
					
					paymentModel.setBookingId(String.valueOf((long)bookingId.getNumericCellValue()));
					if(penalty!=null){
						penalty.setCellType(Cell.CELL_TYPE_STRING);
					paymentModel.setDescription(penalty.getStringCellValue());
					}
					if(isDp.getNumericCellValue()>0){
						paymentModel.setDescription("Down Payment");
					}
					paymentModel.setPaymentAmount((long) amount.getNumericCellValue());
					paymentModel.setEmiDate(paymentModel.getCreatedAt());
					paymentModel.setReceiptNo((long) paymentId.getNumericCellValue());
					paymentModel.setPaymentId(String.valueOf(paymentId.getNumericCellValue()));
					paymentModel.setPaymentMode(paymentMode.getStringCellValue());
					paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_CLEARED);
					paymentModel.setType(ProjectConstant.PAYMENT_TYPE_CREDIT);
					if(chequeDate!=null)
					paymentModel.setChequeDate(chequeDate.getDateCellValue().getTime());
					if(chequeNumber!=null&&"Cheque".equalsIgnoreCase(paymentModel.getPaymentMode())){
						chequeNumber.setCellType(Cell.CELL_TYPE_STRING);
						paymentModel.setChequeNumber(chequeNumber.getStringCellValue());
					}else if(chequeNumber!=null&&"Online".equalsIgnoreCase(paymentModel.getPaymentMode())){
						chequeNumber.setCellType(Cell.CELL_TYPE_STRING);
						paymentModel.setTransactionNumber(chequeNumber.getStringCellValue());
					}
					if(bank!=null)
					paymentModel.setBank(bank.getStringCellValue());
					if(accountHolder!=null)
					paymentModel.setAccountHolder(accountHolder.getStringCellValue());
					bookingDao.changePaidPayment(paymentModel.getBookingId(),paymentModel.getPaymentAmount(),-paymentModel.getPaymentAmount());

					BookingModel bookingModel1 = bookingDao.getBookingDetailsById(paymentModel.getBookingId());
					if(bookingModel1!=null){
					FranchiseCommissionModel franchiseCommissionModel= null;
					FranchiseModel franchiseModel= franchiseDao.fetchFranchiseDetail(bookingModel1.getFranchiseeId());
					franchiseCommissionModel = new FranchiseCommissionModel();
					franchiseCommissionModel.setFranchiseeCommissionId(UUIDGenerator.generateUUID());
					franchiseCommissionModel.setFranchiseeId(bookingModel1.getFranchiseeId());
					franchiseCommissionModel.setFranchiseeName(bookingModel1.getFranchiseeName());
					franchiseCommissionModel.setBookingId(paymentModel.getBookingId());
					franchiseCommissionModel.setPaymentId(paymentModel.getPaymentId());
					franchiseCommissionModel.setProjectId(paymentModel.getProjectId());
					franchiseCommissionModel.setCommissionAmount((long)(paymentModel.getPaymentAmount()/100*franchiseModel.getCommissionPercentage()));
					franchiseCommissionModel.setStatus(ProjectConstant.PAYMENT_TYPE_CREDIT);
					
					franchiseCommissionModel.setCreatedBy(userId);
					franchiseCommissionModel.setUpdatedAt(paymentModel.getCreatedAt());
					franchiseCommissionModel.setUpdatedBy(userId);
					franchiseCommissionModel.setCreatedAt(paymentModel.getCreatedAt());
					
					
					paymentModel.setMemberId(bookingModel1.getMemberId());
					paymentModel.setMemberName(bookingModel1.getMemberName());
					paymentModel.setFranchiseeId(bookingModel1.getFranchiseeId());
					paymentModel.setFranchiseeName(bookingModel1.getFranchiseeName());
					paymentModelList.add(paymentModel);
					paymentDao.addPayments(paymentModelList);
					franchiseDao.addFranchiseeCommission(franchiseCommissionModel);
					franchiseDao.updateUnpaidCommission(franchiseCommissionModel.getCommissionAmount(), franchiseCommissionModel.getFranchiseeId());
					}
				}else{
					List<PaymentModel> paymentModelList = new ArrayList<PaymentModel>();
					PaymentModel paymentModel = new PaymentModel();
					
					String userId="1";
					paymentModel.setCreatedBy(userId);
					paymentModel.setUpdatedAt(time);
					paymentModel.setUpdatedBy(userId);
					paymentModel.setCreatedAt(paymentDate.getDateCellValue().getTime());
					paymentModel.setBookingId(String.valueOf((long)bookingId.getNumericCellValue()));
					if(penalty!=null)
					paymentModel.setDescription(penalty.getStringCellValue());
					paymentModel.setPaymentAmount((long) amount.getNumericCellValue()-(2*(long) amount.getNumericCellValue()));
					paymentModel.setEmiDate(paymentModel.getCreatedAt());
					paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_ADDED_EMI);
					paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
					bookingDao.changeDiscount(paymentModel.getBookingId(),0l,paymentModel.getPaymentAmount(),paymentModel.getPaymentAmount());
				
					paymentModel.setPaymentId(String.valueOf(paymentId.getNumericCellValue()));
					
					BookingModel bookingModel1 = bookingDao.getBookingDetailsById(paymentModel.getBookingId());
					if(bookingModel1!=null){
					paymentModel.setMemberId(bookingModel1.getMemberId());
					paymentModel.setMemberName(bookingModel1.getMemberName());
					paymentModel.setFranchiseeId(bookingModel1.getFranchiseeId());
					paymentModel.setFranchiseeName(bookingModel1.getFranchiseeName());
					
					
					
					paymentModelList.add(paymentModel);
					paymentDao.addPayments(paymentModelList);
					}
				}
				}
			}
			excel++;
		}
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "city";
	}*/
	
	/*@RequestMapping(value="/booking",method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		File excelFile = new File("C:/Users/Asus/Videos/revaradabookingolddata/booking_mst.xlsx");
		FileInputStream mreview = new FileInputStream(excelFile);
		XSSFWorkbook metricWorkbook = new XSSFWorkbook(mreview);
		XSSFSheet metricSheet = metricWorkbook.getSheetAt(0);
		Iterator<Row> rowIterator = metricSheet.rowIterator();
		int excel=0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (excel > 0) {
				Cell bookingId = row.getCell(0);
				Cell bookingCode = row.getCell(1);
				Cell projectId = row.getCell(2);
				Cell projectName = row.getCell(3);
				Cell franchiseeId = row.getCell(4);
				Cell franchiseeName = row.getCell(5);
				Cell plotName = row.getCell(6);
				Cell name = row.getCell(7);
				Cell fatherName = row.getCell(8);
				Cell address1 = row.getCell(9);
				Cell state1 = row.getCell(10);
				Cell city1 = row.getCell(11);
				Cell pincode1 = row.getCell(12);
				Cell address2 = row.getCell(13);
				Cell state2 = row.getCell(14);
				Cell city2 = row.getCell(15);
				Cell pincode2 = row.getCell(16);
				Cell mobile1 = row.getCell(17);
				Cell mobile2 = row.getCell(18);
				Cell landline1 = row.getCell(19);
				Cell landline2 = row.getCell(20);
				Cell gender = row.getCell(21);
				Cell dob = row.getCell(22);
				Cell pancard = row.getCell(23);
				Cell email = row.getCell(24);
				Cell nomineeName = row.getCell(25);
				Cell nomineeFather = row.getCell(26);
				Cell nomineeAddress = row.getCell(27);
				Cell nomineeRelation = row.getCell(28);
				Cell nomineeDob = row.getCell(29);
				Cell remarks = row.getCell(30);
				Cell schemeName = row.getCell(31);
				Cell caption = row.getCell(32);
				Cell plotSize = row.getCell(33);
				Cell price = row.getCell(34);
				Cell dp = row.getCell(35);
				Cell emi = row.getCell(36);
				Cell noOfEmi = row.getCell(37);
				Cell bookingDate = row.getCell(38);
				Cell dateAdded = row.getCell(39);
				Cell isAlloted = row.getCell(40);
				Cell loginId = row.getCell(41);
				Cell password = row.getCell(42);
				Cell status = row.getCell(43);
				Cell cusCode = row.getCell(44);
				Cell refCusCode = row.getCell(45);
				Cell ecg = row.getCell(46);
				Cell print = row.getCell(47);
				Cell ecgDate = row.getCell(48);
				Cell companyId = row.getCell(49);
				Cell plotSize123 = row.getCell(50);
				Cell rpy123 = row.getCell(51);
				Cell dp123 = row.getCell(52);
				Cell emi123 = row.getCell(53);
				Cell months123 = row.getCell(54);
				Cell bookingPrice123 = row.getCell(55);
				Cell name2 = row.getCell(56);
				Cell fatherName2 = row.getCell(57);
				Cell alloDate = row.getCell(58);
				Cell schemeName2 = row.getCell(59);
				Cell ecgBank = row.getCell(60);
				Cell ecgBankHolder = row.getCell(61);
				Cell ifscCode = row.getCell(62);
				Cell pid1234 = row.getCell(63);
				Cell ecgAccNo = row.getCell(64);
				Cell description = row.getCell(65);
				Cell dis = row.getCell(66);
				Cell bookingCancel = row.getCell(67);
				Cell returnAmount = row.getCell(68);
				Cell isTransfer = row.getCell(69);
				
				
				long time = DateUtils.nowAsGmtMillisec();
				
				BookingModel bookingModel=new BookingModel();
				String userId="1";
				bookingModel.setCreatedBy(userId);
				bookingModel.setUpdatedAt(time);
				bookingModel.setUpdatedBy(userId);
				bookingModel.setCreatedAt(bookingDate.getDateCellValue().getTime());
				bookingId.setCellType(Cell.CELL_TYPE_STRING);
				bookingModel.setBookingId(bookingId.getStringCellValue());
				projectId.setCellType(Cell.CELL_TYPE_STRING);
				bookingModel.setProjectId(projectId.getStringCellValue());
				bookingModel.setProjectName(projectName.getStringCellValue());
				plotName.setCellType(Cell.CELL_TYPE_STRING);
				String plotId=projectDao.fetchPlotIdFromPlotName(projectId.getStringCellValue(), plotName.getStringCellValue());
				
				bookingModel.setPlotId(plotId);
				plotSize.setCellType(Cell.CELL_TYPE_STRING);
				bookingModel.setPlotSize(plotSize.getStringCellValue());
				bookingModel.setPlotName(plotName.getStringCellValue());
				bookingModel.setMemberId(memberDao.fetchMemberIdByName(name.getStringCellValue()));
				bookingModel.setMemberName(name.getStringCellValue());
				bookingModel.setPaymentSchemeId("1");
				bookingModel.setNoOfEmi((long) noOfEmi.getNumericCellValue());
				if(noOfEmi.getNumericCellValue()==0){
					bookingModel.setInterestRate(0);
				}else{
					bookingModel.setInterestRate(15);
				}
				
				if(franchiseeId!=null){
					franchiseeId.setCellType(Cell.CELL_TYPE_STRING);
					bookingModel.setFranchiseeId(franchiseeId.getStringCellValue());
				}
				if(franchiseeName!=null)
				bookingModel.setFranchiseeName(franchiseeName.getStringCellValue());
				if(nomineeDob!=null)
				bookingModel.setNomineeDobLong(nomineeDob.getDateCellValue().getTime());
				bookingModel.setNextEmiOn(DateUtils.getGmtMillisecAfterMonths(ProjectConstant.ONE_MONTH,bookingDate.getDateCellValue()));
				bookingModel.setRemainingPayment(0l);
				bookingModel.setBookingCode(bookingCode.getStringCellValue());
				bookingModel.setRatePerYard((long)(price.getNumericCellValue()/Double.parseDouble(bookingModel.getPlotSize())));
				bookingModel.setNoOfEmi((long)noOfEmi.getNumericCellValue());
				bookingModel.setDownPayment((long) dp.getNumericCellValue());
				bookingModel.setPrice((long) price.getNumericCellValue());
				bookingModel.setEmi((long) emi.getNumericCellValue());
				if(fatherName!=null)
				bookingModel.setFatherName(fatherName.getStringCellValue());
				if(nomineeName!=null)
				bookingModel.setNomineeName(nomineeName.getStringCellValue());
				if(nomineeFather!=null)
				bookingModel.setNomineeFather(nomineeFather.getStringCellValue());
				if(nomineeRelation!=null)
				bookingModel.setNomineeRelation(nomineeRelation.getStringCellValue());
				if(nomineeAddress!=null)
					bookingModel.setNomineeAddress(nomineeAddress.getStringCellValue());
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
					paymentModel.setUpdatedAt(bookingModel.getCreatedAt());
					paymentModel.setUpdatedBy(userId);
					paymentModel.setCreatedAt(bookingModel.getCreatedAt());
					paymentModel.setEmiDate(bookingModel.getCreatedAt());
					paymentModel.setDescription("Down Payment");
					paymentModel.setPaymentAmount(bookingModel.getDownPayment());
					paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
					paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_ADDED_EMI);
					bookingDao.changePaidPayment(paymentModel.getBookingId(),0l,paymentModel.getPaymentAmount());
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
					paymentModel.setUpdatedAt(bookingModel.getCreatedAt());
					paymentModel.setUpdatedBy(userId);
					paymentModel.setCreatedAt(bookingModel.getCreatedAt());
					paymentModel.setEmiDate(DateUtils.getGmtMillisecAfterMonths(i+1,bookingDate.getDateCellValue()));
					paymentModel.setDescription("EMI "+(i+1));
					paymentModel.setPaymentAmount(bookingModel.getEmi());
					paymentModel.setType(ProjectConstant.PAYMENT_TYPE_DEBIT);
					paymentModel.setStatus(ProjectConstant.PAYMENT_STATUS_FUTURE_EMI);
					paymentModelList.add(paymentModel);
				}
				paymentDao.addPayments(paymentModelList);
				
			}
			excel++;
		}
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "city";
	}*/
	
	/*@RequestMapping(value="/user",method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		File excelFile = new File("C:/Users/Asus/Videos/revaradabookingolddata/user_mst.xlsx");
		FileInputStream mreview = new FileInputStream(excelFile);
		XSSFWorkbook metricWorkbook = new XSSFWorkbook(mreview);
		XSSFSheet metricSheet = metricWorkbook.getSheetAt(0);
		Iterator<Row> rowIterator = metricSheet.rowIterator();
		int excel=0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (excel > 0) {
				Cell userId = row.getCell(0);
				
				Cell franchiseeId = row.getCell(1);
				Cell franchiseeName = row.getCell(2);
				Cell fullName = row.getCell(3);
				Cell fatherName = row.getCell(4);
				Cell address1 = row.getCell(5);
				Cell state1 = row.getCell(6);
				Cell city1 = row.getCell(7);
				Cell pincode1 = row.getCell(8);
				Cell address2 = row.getCell(9);
				Cell state2 = row.getCell(10);
				Cell city2 = row.getCell(11);
				Cell pincode2 = row.getCell(12);
				Cell mobile1 = row.getCell(13);
				Cell mobile2 = row.getCell(14);
				Cell landline1 = row.getCell(15);
				Cell landline2 = row.getCell(16);
				Cell gender = row.getCell(17);
				Cell dob = row.getCell(18);
				Cell pancard = row.getCell(19);
				Cell email = row.getCell(20);
				Cell dateAdded = row.getCell(21);
				Cell userName = row.getCell(22);
				MemberModel memberModel= new MemberModel();
				if(franchiseeId!=null){
				franchiseeId.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setFranchiseeId(franchiseeId.getStringCellValue());
				}if(franchiseeName!=null){
				franchiseeName.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setFranchiseeName(franchiseeName.getStringCellValue());
				}if(fatherName!=null){
				fatherName.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setFatherName(fatherName.getStringCellValue());
				}if(userId!=null){
				userId.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setMemberId(userId.getStringCellValue());
				}if(fullName!=null){
				fullName.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setMemberName(fullName.getStringCellValue());
				}if(address1!=null){
				address1.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setAddress1(address1.getStringCellValue());
				}if(state1!=null){
				state1.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setState1(stateDao.getStateNameById(state1.getStringCellValue()));
				}if(city1!=null){
				city1.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setCity1(cityDao.fetchCityNameById(city1.getStringCellValue()));
				}if(pincode1!=null){
				pincode1.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setPincode1(pincode1.getStringCellValue());
				}if(address2!=null){
				address2.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setAddress2(address2.getStringCellValue());
				}if(state2!=null){
				state2.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setState2(stateDao.getStateNameById(state2.getStringCellValue()));
				}if(city2!=null){
				city2.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setCity2(cityDao.fetchCityNameById(city2.getStringCellValue()));
				}if(pincode2!=null){
				pincode2.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setPincode2(pincode2.getStringCellValue());
				}if(mobile2!=null){
				mobile2.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setPhone2(mobile2.getStringCellValue());
				}if(mobile1!=null){
				mobile1.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setPhone1(mobile1.getStringCellValue());
				}if(landline1!=null){
				landline1.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setLandline1(landline1.getStringCellValue());
				}if(landline2!=null){
				landline2.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setLandline2(landline2.getStringCellValue());
				}if(gender!=null){
				gender.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setGender(gender.getStringCellValue());
				}if(dob!=null){
//				dob.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setDobForDb(dob.getDateCellValue().getTime());
				}
				if(pancard!=null){
				pancard.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setPancard(pancard.getStringCellValue());
				}
				if(email!=null){
				email.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setEmail(email.getStringCellValue());
				}
//				dateAdded.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setCreatedAt(dateAdded.getDateCellValue().getTime());
				userName.setCellType(Cell.CELL_TYPE_STRING);
				memberModel.setMemberCode(userName.getStringCellValue());
				
				
				memberModel.setCreatedBy("1");
				memberModel.setUpdatedBy("1");
				
				memberModel.setUpdatedAt(DateUtils.nowAsGmtMillisec());
				memberModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
				memberDao.postAddMember(memberModel);
				
			}
			excel++;
		}
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "city";
	}*/
	
	
	
}
