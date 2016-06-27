package com.webapp.controllers.secure;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.CommonUtils;
import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.SubOrderJsonDto;
import com.webapp.models.OrderModel;
import com.webapp.models.PostUpdateModel;
import com.webapp.models.SubOrderModel;
import com.webapp.models1.OrdersHistory;
import com.webapp.models1.UserReferralHistory;
import com.webapp.services.FnFTelegramService;
import com.webapp.services.OrderService;
import com.webapp.services.UsersService;

@Controller
@RequestMapping("/order/edit")
public class OrderDetailsController extends BusinessController {

	@Autowired
	private FnFTelegramService fnFTelegramService;

	// shyambot
	// private static final String URL =
	// "https://api.telegram.org/bot126641750:AAHIG-cUtvqRX3DcOZecQkrxehtYcyIYQ1U/";

	// fnfSareesbot
	//private static final String URL = "https://api.telegram.org/bot178408325:AAGnaiTjbwOd59ee9xNYSVsa2D41khNoCXI/";
	
	private static final String URL = ProjectConstant.FNFSAREESBOT;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UsersService usersService;

	@RequestMapping(method = RequestMethod.GET)
	public String getEditOrderForm(@QueryParam("order_id") int order_id, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		OrderModel orderModel = orderService.fetchOrderDetails(order_id);
		List<SubOrderModel> suborderModel = orderService.fetchSubOrderDetailsList(order_id);
		if (orderModel.getCreated_at() == 0) {

		} else {
			orderModel.setCreated_date(DateUtils.dbTimeStampToSesionDate(
					orderModel.getCreated_at(), "IST", "dd/MM/yyyy hh:mm a"));
		}
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("suborderModel", suborderModel);
		return "order-details";
	}

	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<SubOrderJsonDto> fetchSubOrders(
			@RequestParam int sEcho, @RequestParam("orderId") String orderId,
			@RequestParam String sSearch, HttpServletRequest req,
			HttpServletResponse res) {

		DataTablesTO<SubOrderJsonDto> dt = new DataTablesTO<SubOrderJsonDto>();
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");

		DbSession dbSession = DbSession.getSession(req, res, sessionService,
				sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID,
				sessionService);

		int order_id = Integer.parseInt(orderId);
		List<SubOrderJsonDto> accts = orderService.fetchSubOrder(tableUtils,
				userId, order_id);
		for (SubOrderJsonDto suborderJson : accts) {
			suborderJson.setAction("<a href='" + req.getContextPath()
					+ "/suborder/edit.do?sub_order_id="
					+ suborderJson.getOrder_product_id() + "'>edit</a>");
		}

		long count = orderService.fetchSubTotalOrders(tableUtils, userId,
				order_id);

		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		
		return dt;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editOrderDetails(Model model, @Validated OrderModel orderModel,
			BindingResult result, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		
		long currentTime = DateUtils.nowAsGmtMillisec();

		String order_status = orderModel.getOrder_status();
		String previous_order_status = orderModel.getPrevious_order_status();
		if (previous_order_status.equalsIgnoreCase("cancel")
				|| previous_order_status.equalsIgnoreCase("cancelled (not yet paid by customer)")) {

		}else
		if (order_status.equalsIgnoreCase("cancel")
				|| order_status.equalsIgnoreCase("cancelled (not yet paid by customer)")) {
			
			if (orderModel.getReferral_wallet_discount() != 0) {
				UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
				userReferralHistoryModel
						.setReferralBalanceHistoryId(UUIDGenerator
								.generateUUID());
				userReferralHistoryModel.setAmount(orderModel
						.getReferral_wallet_discount());
				userReferralHistoryModel.setUserId(orderModel.getUser_id());
				userReferralHistoryModel
						.setHistoryNote("Refund from Order No. "
								+ orderModel.getOrder_id() + " cancellation");
				
				usersService.addWalletBalanceOfUser(userReferralHistoryModel);
			}
			if (order_status.equalsIgnoreCase("cancelled (not yet paid by customer)")) {
				if (orderModel.getCoupon_id() != null && !"".equalsIgnoreCase(orderModel.getCoupon_id())) {
					orderService.disableCouponForUser(orderModel.getCoupon_id(),orderModel.getUser_id(),orderModel.getOrder_id(),UUIDGenerator.generateUUID());
				}
			}
			
		} 
		
		if (previous_order_status.equalsIgnoreCase("completed")
				|| previous_order_status.equalsIgnoreCase("partially completed")) {

		} else {
			if (order_status.equalsIgnoreCase("completed")
					|| order_status.equalsIgnoreCase("partially completed")) {
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				String referrerCoupon = orderService
						.getFirstBuyDetails(orderModel.getUser_id());
				if (referrerCoupon != null
						&& !"-1".equalsIgnoreCase(referrerCoupon)) {
					UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
					userReferralHistoryModel
							.setReferralBalanceHistoryId(UUIDGenerator
									.generateUUID());
					userReferralHistoryModel.setAmount(ProjectConstant.REFERRALAMOUNT);
					userReferralHistoryModel.setUserId(referrerCoupon);
					userReferralHistoryModel.setHistoryNote("Referral completion added Rs "+ProjectConstant.REFERRALAMOUNT);
					usersService.addWalletBalanceOfUser(userReferralHistoryModel);
					
					/*orderService.addReferralBonus(referrerCoupon,
							orderModel.getUser_id());*/
					
					orderService.updateFirstBuy(orderModel.getUser_id());
					
					URL url = new URL(URL + "sendMessage");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					ObjectMapper mapper = new ObjectMapper();
					DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
					HashMap<String, Object> data1 = new HashMap<String, Object>();
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					JSONArray innerJsonArray2 = new JSONArray();

					data1.put("chat_id", referrerCoupon);
					data1.put("text", "Hurrah!\n\nYour Friend XYZ, made his/her first purchase.\n\n"
							+ "And you receive "+ProjectConstant.REFERRALAMOUNT+" INR as your Referral Bonus.\n\n"
							+ "Refer More Friends ( Link) or Check Wallet Balance or I too will Shop."
							+ "\n\nFor Help buzz@9586724633\n\nThanks\nTeam Intricate");
					/* "91734453" */
					int i = 0;
					for (Map<String, Object> map : productType) {
						if (i < 2) {
							innerJsonArray.put(map.get("product_type_name"));
							i++;
						} else {
							innerJsonArray1.put(map.get("product_type_name"));
							i++;
						}
					}
					innerJsonArray2.put("Orders");
					innerJsonArray2.put("Account");
					JSONArray innerJsonArray3 = new JSONArray();
					innerJsonArray3.put("Invite and Earn");
					outerJsonArray.put(innerJsonArray);
					outerJsonArray.put(innerJsonArray1);
					outerJsonArray.put(innerJsonArray2);
					outerJsonArray.put(innerJsonArray3);
					jsonObject.put("keyboard", outerJsonArray);

					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data1.put("reply_markup", jsonObject.toString());
					mapper.writeValue(wr, data1);
					int responseCode = conn.getResponseCode();
					InputStream in = null;
					if(responseCode==200){
						in = conn.getInputStream();
					}
					PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
							PostUpdateModel.class);
					in.close();
					
					/*new CommonUtils().sendCustomerNote(referrerCoupon, "Hurrah!\n\nYour Friend XYZ, made his/her first purchase.\n\n"
							+ "And you receive "+ProjectConstant.REFERRALAMOUNT+" INR as your Referral Bonus.\n\n"
							+ "Refer More Friends ( Link) or Check Wallet Balance or I too will Shop."
							+ "\n\nFor Help buzz@9586724633\n\nThanks\nTeam Intricate");
					*/
					
					Map<String, Object> userDetails1 = fnFTelegramService
							.fetchUserDetailsByUserId(referrerCoupon);
					new CommonUtils().sendSms((String)userDetails1.get("phone"),"Dear User,\nYour friend just completed first order."
							+ "\nReferral Rs "+ProjectConstant.REFERRALAMOUNT+" has been credited to your wallet.");
				}

				// update cumulative discount history
				long orderDate = orderModel.getCreated_at();
				OrderModel cumulativeDiscountOrderModel = new OrderModel();
				cumulativeDiscountOrderModel.setCumulativeDiscountOrderId(UUIDGenerator.generateUUID());
				cumulativeDiscountOrderModel.setOrder_id(orderModel.getOrder_id());
				cumulativeDiscountOrderModel.setUser_id(orderModel.getUser_id());
				cumulativeDiscountOrderModel.setAmount(orderModel.getTotal_discounted_price()
						- orderModel.getReferral_wallet_discount()
						- orderModel.getCoupon_discount());
				cumulativeDiscountOrderModel.setCreated_at(orderDate);
				cumulativeDiscountOrderModel.setUpdatedAt(orderDate);
				cumulativeDiscountOrderModel.setRecordStatus("A");
				
				if (!orderService.checkDiscountOrder(cumulativeDiscountOrderModel)) {
					orderService.addCumulativeDiscountOrderHistory(cumulativeDiscountOrderModel);
				}

				// get orders within last 15 days of this order
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(orderDate);
				cal.add(Calendar.DATE, -60);
				long prevSixtyDayTime = cal.getTimeInMillis();

				cal.setTimeInMillis(orderDate);
				cal.add(Calendar.DATE, -15);
				long prevFifteenDayTime = cal.getTimeInMillis();

				cal.setTimeInMillis(orderDate);
				cal.add(Calendar.DATE, -30);
				long prevThirtyDayTime = cal.getTimeInMillis();

				cal.setTimeInMillis(orderDate);
				cal.add(Calendar.DATE, -45);
				long prevFortyFiveDayTime = cal.getTimeInMillis();

				HashMap<String, Object> parametersMap = new HashMap<String, Object>();
				parametersMap.put("userId", orderModel.getUser_id());
				parametersMap.put("prevDate", prevSixtyDayTime);
				parametersMap.put("orderDate", orderDate);
				List<OrderModel> prevOrders = orderService.getOrderDetailsOfUserForTimePeriod(parametersMap);
				int amountTotal15 = 0;
				int amountTotal30 = 0;
				int amountTotal45 = 0;
				int amountTotal60 = 0;

				for (OrderModel prevOrder : prevOrders) {

					if (prevOrder.getCreated_at() >= prevFifteenDayTime) {
						amountTotal15 += prevOrder.getAmount();
					}
					if (prevOrder.getCreated_at() >= prevThirtyDayTime) {
						amountTotal30 += prevOrder.getAmount();
					}
					if (prevOrder.getCreated_at() >= prevFortyFiveDayTime) {
						amountTotal45 += prevOrder.getAmount();
					}
					if (prevOrder.getCreated_at() >= prevSixtyDayTime) {
						amountTotal60 += prevOrder.getAmount();
					}
				}

				if (amountTotal15 >= ProjectConstant.CUMULATIVE_DISCOUNT_MIN_TXN_AMOUNT) {

					int walletBonus = (int) (amountTotal15 * 0.04);
					UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
					userReferralHistoryModel
							.setReferralBalanceHistoryId(UUIDGenerator
									.generateUUID());
					userReferralHistoryModel.setAmount(walletBonus);
					userReferralHistoryModel.setUserId(orderModel.getUser_id());
					userReferralHistoryModel.setHistoryNote("4% of Rs"
							+ amountTotal15 + "  wallet bonus added.");
					int status = usersService
							.addWalletBalanceOfUser(userReferralHistoryModel);
					if (status > 0) {
						orderService
								.updateCumulativeDiscountOrderHistory(orderModel);
						
						URL url = new URL(URL + "sendMessage");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json");
						conn.setDoOutput(true);
						ObjectMapper mapper = new ObjectMapper();
						DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
						HashMap<String, Object> data1 = new HashMap<String, Object>();
						JSONObject jsonObject = new JSONObject();
						JSONArray outerJsonArray = new JSONArray();
						JSONArray innerJsonArray = new JSONArray();
						JSONArray innerJsonArray1 = new JSONArray();
						JSONArray innerJsonArray2 = new JSONArray();

						data1.put("chat_id", orderModel.getUser_id());
						data1.put("text", "Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal15+" Purchase within 15 days\nPlace New Orders or Special Offers or Check Wallet or Check Sales Leaderboard");
						/* "91734453" */
						int i = 0;
						for (Map<String, Object> map : productType) {
							if (i < 2) {
								innerJsonArray.put(map.get("product_type_name"));
								i++;
							} else {
								innerJsonArray1.put(map.get("product_type_name"));
								i++;
							}
						}
						innerJsonArray2.put("Orders");
						innerJsonArray2.put("Account");
						JSONArray innerJsonArray3 = new JSONArray();
						innerJsonArray3.put("Invite and Earn");
						outerJsonArray.put(innerJsonArray);
						outerJsonArray.put(innerJsonArray1);
						outerJsonArray.put(innerJsonArray2);
						outerJsonArray.put(innerJsonArray3);
						jsonObject.put("keyboard", outerJsonArray);

						jsonObject.put("resize_keyboard", true);
						jsonObject.put("one_time_keyboard", true);
						data1.put("reply_markup", jsonObject.toString());
						mapper.writeValue(wr, data1);
						int responseCode = conn.getResponseCode();
						InputStream in = null;
						if(responseCode==200){
							in = conn.getInputStream();
						}
						PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
								PostUpdateModel.class);
						in.close();
						
						/*new CommonUtils().sendCustomerNote(referrerCoupon, "Hurrah!\n\nYour Friend XYZ, made his/her first purchase.\n\n"
								+ "And you receive "+ProjectConstant.REFERRALAMOUNT+" INR as your Referral Bonus.\n\n"
								+ "Refer More Friends ( Link) or Check Wallet Balance or I too will Shop."
								+ "\n\nFor Help buzz@9586724633\n\nThanks\nTeam Intricate");
						*/
						
						Map<String, Object> userDetails1 = fnFTelegramService
								.fetchUserDetailsByUserId(orderModel.getUser_id());
						new CommonUtils().sendSms((String)userDetails1.get("phone"),"Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal15+" Purchase within 15 days");
					
					}

				} else if (amountTotal30 >= ProjectConstant.CUMULATIVE_DISCOUNT_MIN_TXN_AMOUNT) {

					int walletBonus = (int) (amountTotal30 * 0.03);
					UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
					userReferralHistoryModel
							.setReferralBalanceHistoryId(UUIDGenerator
									.generateUUID());
					userReferralHistoryModel.setAmount(walletBonus);
					userReferralHistoryModel.setUserId(orderModel.getUser_id());
					userReferralHistoryModel.setHistoryNote("3% of Rs"
							+ amountTotal30 + " wallet bonus added");
					int status = usersService
							.addWalletBalanceOfUser(userReferralHistoryModel);
					if (status > 0) {
						orderService
								.updateCumulativeDiscountOrderHistory(orderModel);
						
						URL url = new URL(URL + "sendMessage");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json");
						conn.setDoOutput(true);
						ObjectMapper mapper = new ObjectMapper();
						DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
						HashMap<String, Object> data1 = new HashMap<String, Object>();
						JSONObject jsonObject = new JSONObject();
						JSONArray outerJsonArray = new JSONArray();
						JSONArray innerJsonArray = new JSONArray();
						JSONArray innerJsonArray1 = new JSONArray();
						JSONArray innerJsonArray2 = new JSONArray();

						data1.put("chat_id", orderModel.getUser_id());
						data1.put("text", "Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal30+" Purchase within 30 days\nPlace New Orders or Special Offers or Check Wallet or Check Sales Leaderboard");
						/* "91734453" */
						int i = 0;
						for (Map<String, Object> map : productType) {
							if (i < 2) {
								innerJsonArray.put(map.get("product_type_name"));
								i++;
							} else {
								innerJsonArray1.put(map.get("product_type_name"));
								i++;
							}
						}
						innerJsonArray2.put("Orders");
						innerJsonArray2.put("Account");
						JSONArray innerJsonArray3 = new JSONArray();
						innerJsonArray3.put("Invite and Earn");
						outerJsonArray.put(innerJsonArray);
						outerJsonArray.put(innerJsonArray1);
						outerJsonArray.put(innerJsonArray2);
						outerJsonArray.put(innerJsonArray3);
						jsonObject.put("keyboard", outerJsonArray);

						jsonObject.put("resize_keyboard", true);
						jsonObject.put("one_time_keyboard", true);
						data1.put("reply_markup", jsonObject.toString());
						mapper.writeValue(wr, data1);
						int responseCode = conn.getResponseCode();
						InputStream in = null;
						if(responseCode==200){
							in = conn.getInputStream();
						}
						PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
								PostUpdateModel.class);
						in.close();
						
						/*new CommonUtils().sendCustomerNote(referrerCoupon, "Hurrah!\n\nYour Friend XYZ, made his/her first purchase.\n\n"
								+ "And you receive "+ProjectConstant.REFERRALAMOUNT+" INR as your Referral Bonus.\n\n"
								+ "Refer More Friends ( Link) or Check Wallet Balance or I too will Shop."
								+ "\n\nFor Help buzz@9586724633\n\nThanks\nTeam Intricate");
						*/
						
						Map<String, Object> userDetails1 = fnFTelegramService
								.fetchUserDetailsByUserId(orderModel.getUser_id());
						new CommonUtils().sendSms((String)userDetails1.get("phone"),"Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal30+" Purchase within 30 days");
					
					}

				} else if (amountTotal45 >= ProjectConstant.CUMULATIVE_DISCOUNT_MIN_TXN_AMOUNT) {

					int walletBonus = (int) (amountTotal45 * 0.02);
					UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
					userReferralHistoryModel
							.setReferralBalanceHistoryId(UUIDGenerator
									.generateUUID());
					userReferralHistoryModel.setAmount(walletBonus);
					userReferralHistoryModel.setUserId(orderModel.getUser_id());
					userReferralHistoryModel.setHistoryNote("2% of Rs"
							+ amountTotal45 + "  wallet bonus added.");
					int status = usersService
							.addWalletBalanceOfUser(userReferralHistoryModel);
					if (status > 0) {
						orderService
								.updateCumulativeDiscountOrderHistory(orderModel);
						
						URL url = new URL(URL + "sendMessage");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json");
						conn.setDoOutput(true);
						ObjectMapper mapper = new ObjectMapper();
						DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
						HashMap<String, Object> data1 = new HashMap<String, Object>();
						JSONObject jsonObject = new JSONObject();
						JSONArray outerJsonArray = new JSONArray();
						JSONArray innerJsonArray = new JSONArray();
						JSONArray innerJsonArray1 = new JSONArray();
						JSONArray innerJsonArray2 = new JSONArray();

						data1.put("chat_id", orderModel.getUser_id());
						data1.put("text", "Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal45+" Purchase within 45 days\nPlace New Orders or Special Offers or Check Wallet or Check Sales Leaderboard");
						/* "91734453" */
						int i = 0;
						for (Map<String, Object> map : productType) {
							if (i < 2) {
								innerJsonArray.put(map.get("product_type_name"));
								i++;
							} else {
								innerJsonArray1.put(map.get("product_type_name"));
								i++;
							}
						}
						innerJsonArray2.put("Orders");
						innerJsonArray2.put("Account");
						JSONArray innerJsonArray3 = new JSONArray();
						innerJsonArray3.put("Invite and Earn");
						outerJsonArray.put(innerJsonArray);
						outerJsonArray.put(innerJsonArray1);
						outerJsonArray.put(innerJsonArray2);
						outerJsonArray.put(innerJsonArray3);
						jsonObject.put("keyboard", outerJsonArray);

						jsonObject.put("resize_keyboard", true);
						jsonObject.put("one_time_keyboard", true);
						data1.put("reply_markup", jsonObject.toString());
						mapper.writeValue(wr, data1);
						int responseCode = conn.getResponseCode();
						InputStream in = null;
						if(responseCode==200){
							in = conn.getInputStream();
						}
						PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
								PostUpdateModel.class);
						in.close();
						
						/*new CommonUtils().sendCustomerNote(referrerCoupon, "Hurrah!\n\nYour Friend XYZ, made his/her first purchase.\n\n"
								+ "And you receive "+ProjectConstant.REFERRALAMOUNT+" INR as your Referral Bonus.\n\n"
								+ "Refer More Friends ( Link) or Check Wallet Balance or I too will Shop."
								+ "\n\nFor Help buzz@9586724633\n\nThanks\nTeam Intricate");
						*/
						
						Map<String, Object> userDetails1 = fnFTelegramService
								.fetchUserDetailsByUserId(orderModel.getUser_id());
						new CommonUtils().sendSms((String)userDetails1.get("phone"),"Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal45+" Purchase within 45 days");

					}
				} else if (amountTotal60 >= ProjectConstant.CUMULATIVE_DISCOUNT_MIN_TXN_AMOUNT) {

					int walletBonus = (int) (amountTotal60 * 0.01);
					UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
					userReferralHistoryModel
							.setReferralBalanceHistoryId(UUIDGenerator
									.generateUUID());
					userReferralHistoryModel.setAmount(walletBonus);
					userReferralHistoryModel.setUserId(orderModel.getUser_id());
					userReferralHistoryModel.setHistoryNote("1% of Rs"
							+ amountTotal60 + "  wallet bonus added.");
					int status = usersService
							.addWalletBalanceOfUser(userReferralHistoryModel);
					if (status > 0) {
						orderService
								.updateCumulativeDiscountOrderHistory(orderModel);
						
						URL url = new URL(URL + "sendMessage");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json");
						conn.setDoOutput(true);
						ObjectMapper mapper = new ObjectMapper();
						DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
						HashMap<String, Object> data1 = new HashMap<String, Object>();
						JSONObject jsonObject = new JSONObject();
						JSONArray outerJsonArray = new JSONArray();
						JSONArray innerJsonArray = new JSONArray();
						JSONArray innerJsonArray1 = new JSONArray();
						JSONArray innerJsonArray2 = new JSONArray();

						data1.put("chat_id", orderModel.getUser_id());
						data1.put("text", "Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal60+" Purchase within 60 days\nPlace New Orders or Special Offers or Check Wallet or Check Sales Leaderboard");
						/* "91734453" */
						int i = 0;
						for (Map<String, Object> map : productType) {
							if (i < 2) {
								innerJsonArray.put(map.get("product_type_name"));
								i++;
							} else {
								innerJsonArray1.put(map.get("product_type_name"));
								i++;
							}
						}
						innerJsonArray2.put("Orders");
						innerJsonArray2.put("Account");
						JSONArray innerJsonArray3 = new JSONArray();
						innerJsonArray3.put("Invite and Earn");
						outerJsonArray.put(innerJsonArray);
						outerJsonArray.put(innerJsonArray1);
						outerJsonArray.put(innerJsonArray2);
						outerJsonArray.put(innerJsonArray3);
						jsonObject.put("keyboard", outerJsonArray);

						jsonObject.put("resize_keyboard", true);
						jsonObject.put("one_time_keyboard", true);
						data1.put("reply_markup", jsonObject.toString());
						mapper.writeValue(wr, data1);
						int responseCode = conn.getResponseCode();
						InputStream in = null;
						if(responseCode==200){
							in = conn.getInputStream();
						}
						PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
								PostUpdateModel.class);
						in.close();
						
						/*new CommonUtils().sendCustomerNote(referrerCoupon, "Hurrah!\n\nYour Friend XYZ, made his/her first purchase.\n\n"
								+ "And you receive "+ProjectConstant.REFERRALAMOUNT+" INR as your Referral Bonus.\n\n"
								+ "Refer More Friends ( Link) or Check Wallet Balance or I too will Shop."
								+ "\n\nFor Help buzz@9586724633\n\nThanks\nTeam Intricate");
						*/
						
						Map<String, Object> userDetails1 = fnFTelegramService
								.fetchUserDetailsByUserId(orderModel.getUser_id());
						new CommonUtils().sendSms((String)userDetails1.get("phone"),"Wohoo! Just Received cashback of Rs "+walletBonus+" on completion of Rs "+amountTotal60+" Purchase within 60 days");

					}
				}
			}
		}

		String customer_note = orderModel.getCustomer_note();
		if (customer_note != null && !"".equalsIgnoreCase(customer_note)) {
			orderModel.setCustomer_note_new(customer_note);
		}
		
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String loggedInUserId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		
		OrderModel orderDetailsFromDB = orderService.fetchOrderDetails(orderModel.getOrder_id());
		checkAndAddOrderChangeHistory(orderModel, orderDetailsFromDB, currentTime, loggedInUserId);
		
		int status = orderService.updateOrders(orderModel);
		if (status > 0) {
			model.addAttribute("msg", "Order Details Updated Successfully");
		} else {
			model.addAttribute("msg", "Failed to update");
		}
		
		if (orderDetailsFromDB.getCreated_at() == 0) {

		} else {
			orderModel.setCreated_date(DateUtils.dbTimeStampToSesionDate(
					orderDetailsFromDB.getCreated_at(), "IST", "dd/MM/yyyy hh:mm a"));
		}
		model.addAttribute("orderModel", orderModel);
		
		
		if (customer_note != null && !"".equalsIgnoreCase(customer_note)) {
			sendCustomerNote(orderModel, customer_note);
		}
		
		return "order-details";
	}

	private void checkAndAddOrderChangeHistory(OrderModel orderModel,
			OrderModel orderDetailsFromDB, long currentTime, String loggedInUserId) {
		
		boolean isOrderDetailsChanged = false;
		String changesDetail = "";
		
		if (orderModel.getAddress() != null && !orderModel.getAddress().equals("") && !orderModel.getAddress().equals(orderDetailsFromDB.getAddress())) {
			isOrderDetailsChanged = true;
			changesDetail = "Delivery address "; // TODO set logged in users name instead of admin
			if (orderDetailsFromDB.getAddress() != null && !orderDetailsFromDB.getAddress().equals("")) {
				changesDetail += "from " + orderDetailsFromDB.getAddress() + " ";
			}
			changesDetail += "to " + orderModel.getAddress() + ", ";
		}
		
		if (orderModel.getDelivery_phone() != null && !orderModel.getDelivery_phone().equals("") && !orderModel.getDelivery_phone().equals(orderDetailsFromDB.getDelivery_phone())) {
			isOrderDetailsChanged = true;
			changesDetail += "Delivery Phone ";
			if (orderDetailsFromDB.getDelivery_phone() != null && !orderDetailsFromDB.getDelivery_phone().equals("")) {
				changesDetail += "from " + orderDetailsFromDB.getDelivery_phone() + " ";
			}
			changesDetail += "to " + orderModel.getDelivery_phone() + ", ";
		}
		
		if (orderModel.getTotal_courier() != orderDetailsFromDB.getTotal_courier()) {
			isOrderDetailsChanged = true;
			changesDetail += "Total Courier ";
			changesDetail += "from " + orderDetailsFromDB.getTotal_courier() + " ";
			changesDetail += "to " + orderModel.getTotal_courier() + ", ";
		}
		
		if (orderModel.getAdjustment_amount() != orderDetailsFromDB.getAdjustment_amount()) {
			isOrderDetailsChanged = true;
			changesDetail += "Adjustment amount ";
			changesDetail += "from " + orderDetailsFromDB.getAdjustment_amount() + " ";
			changesDetail += "to " + orderModel.getAdjustment_amount() + ", ";
		}
		
		if (orderModel.getAdjustment_note() != null && !orderModel.getAdjustment_note().equals("") && !orderModel.getAdjustment_note().equals(orderDetailsFromDB.getAdjustment_note())) {
			isOrderDetailsChanged = true;
			changesDetail += "Adjustment Note ";
			if (orderDetailsFromDB.getAdjustment_note() != null && !orderDetailsFromDB.getAdjustment_note().equals("")) {
				changesDetail += "from " + orderDetailsFromDB.getAdjustment_note() + " ";
			}
			changesDetail += "to " + orderModel.getAdjustment_note() + ", ";
		}
		
		if (orderModel.getOrder_note() != null && !orderModel.getOrder_note().equals("") && !orderModel.getOrder_note().equals(orderDetailsFromDB.getOrder_note())) {
			isOrderDetailsChanged = true;
			changesDetail += "Order Note ";
			if (orderDetailsFromDB.getOrder_note() != null && !orderDetailsFromDB.getOrder_note().equals("")) {
				changesDetail += "from " + orderDetailsFromDB.getOrder_note() + " ";
			}
			changesDetail += "to " + orderModel.getOrder_note() + ", ";
		}
		
		if (orderModel.getOrder_status() != null && !orderModel.getOrder_status().equals("") && !orderModel.getOrder_status().equals(orderDetailsFromDB.getOrder_status())) {
			isOrderDetailsChanged = true;
			changesDetail += "Order Status ";
			if (orderDetailsFromDB.getOrder_status() != null && !orderDetailsFromDB.getOrder_status().equals("")) {
				changesDetail += "from " + orderDetailsFromDB.getOrder_status() + " ";
			}
			changesDetail += "to " + orderModel.getOrder_status() + ", ";
		}
		
		if (orderModel.getCustomer_note() != null && !orderModel.getCustomer_note().equals("") && !orderModel.getCustomer_note().equals(orderDetailsFromDB.getCustomer_note())) {
			isOrderDetailsChanged = true;
			changesDetail += "Customer Note ";
			if (orderDetailsFromDB.getCustomer_note() != null && !orderDetailsFromDB.getCustomer_note().equals("")) {
				changesDetail += "from " + orderDetailsFromDB.getCustomer_note() + " ";
			}
			changesDetail += "to " + orderModel.getCustomer_note() + ", ";
		}
		
		if (!changesDetail.equals("")) {
			changesDetail = "Admin changed " + changesDetail;
		}
		if (changesDetail.endsWith(", ")) {
			changesDetail = changesDetail.substring(0, changesDetail.length() - 2);
		}
		//System.out.println("changes details = " + changesDetail);
		
		if (isOrderDetailsChanged) {
			OrdersHistory orderChangeHistory = new OrdersHistory();
			orderChangeHistory.setOrderHistoryId(UUIDGenerator.generateUUID());
			orderChangeHistory.setOrderId(orderModel.getOrder_id());
			orderChangeHistory.setChangesDetail(changesDetail);
			orderChangeHistory.setCreatedAt(currentTime);
			orderChangeHistory.setCreatedBy(loggedInUserId);
			orderService.insertOrderChangeHistory(orderChangeHistory);
		}
	}

	private void sendCustomerNote(OrderModel orderModel, String customer_note)
			throws MalformedURLException, IOException, ProtocolException,
			JsonGenerationException, JsonMappingException, JsonParseException {
		URL url = new URL(URL + "sendMessage");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		JSONArray innerJsonArray1 = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();

		data1.put("chat_id", orderModel.getUser_id());
		data1.put("text", "---------------\nStatus for Order Id : "
				+ orderModel.getOrder_id() + "\n" + customer_note
				+ "\n--------------");
		/* "91734453" */
		int i = 0;
		List<Map<String, Object>> productType = fnFTelegramService
				.getProductTypes();
		for (Map<String, Object> map : productType) {
			if (i < 2) {
				innerJsonArray.put(map.get("product_type_name"));
				i++;
			} else {
				innerJsonArray1.put(map.get("product_type_name"));
				i++;
			}
		}
		innerJsonArray2.put("Orders");
		innerJsonArray2.put("Account");
		JSONArray innerJsonArray3 = new JSONArray();
		innerJsonArray3.put("Invite and Earn");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray1);
		outerJsonArray.put(innerJsonArray2);
		outerJsonArray.put(innerJsonArray3);

		jsonObject.put("keyboard", outerJsonArray);
		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = null;
		if (responseCode == 200) {
			in = conn.getInputStream();
			mapper.readValue(in,
					PostUpdateModel.class);
			in.close();
		}
	}

	@Override
	protected String[] requiredJs() {
		return new String[] { "js/suborder.js" };
	}
}