package com.webapp.controllers.secure;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

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
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.UsersJsonDto;
import com.webapp.models.PostUpdateModel;
import com.webapp.models1.UserReferralHistory;
import com.webapp.services.FnFTelegramService;
import com.webapp.services.UsersService;

@Controller
@RequestMapping("/users/edit")
public class UsersDetailsController extends BusinessController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FnFTelegramService fnFTelegramService;
	
	private static final String URL = ProjectConstant.FNFSAREESBOT;

	@RequestMapping(method = RequestMethod.GET)
	public String editOrder(@QueryParam("user_id") String user_id, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		UsersJsonDto usersJsonDto = usersService.fetchUserDetails(user_id);
		if (usersJsonDto.getCreated_at() == 0) {

		} else {
			usersJsonDto.setCreated_date(DateUtils.dbTimeStampToSesionDate(
					usersJsonDto.getCreated_at(), "IST", "dd/MM/yyyy hh:mm a"));
		}
		model.addAttribute("userModel", usersJsonDto);
		return "user-details";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postEditOrder(Model model,
			@Validated UsersJsonDto usersJsonDto, BindingResult result,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		int status = usersService.updateUsers(usersJsonDto);
		usersJsonDto = usersService.fetchUserDetails(usersJsonDto.getUser_id());
		if (usersJsonDto.getCreated_at() == 0) {

		} else {
			usersJsonDto.setCreated_date(DateUtils.dbTimeStampToSesionDate(
					usersJsonDto.getCreated_at(), "IST", "dd/MM/yyyy hh:mm a"));
		}
		model.addAttribute("userModel", usersJsonDto);
		if (status > 0) {
			model.addAttribute("msg", "User Details Updated Successfully");
		} else {
			model.addAttribute("msg", "Failed to update");
		}
		return "user-details";
	}

	@RequestMapping(value = "/referral-history", method = RequestMethod.GET)
	public String initForm(@RequestParam String userId, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		model.addAttribute("userId", userId);

		return "user-referral-history";
	}

	@RequestMapping(value = "/referral-history/list", produces = "application/json")
	public @ResponseBody DataTablesTO<UserReferralHistory> showUsers(
			@RequestParam String userId, @RequestParam int sEcho,
			@RequestParam String sSearch, HttpServletRequest req,
			HttpServletResponse res) {

		DataTablesTO<UserReferralHistory> dt = new DataTablesTO<UserReferralHistory>();
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");

		// DbSession dbSession = DbSession.getSession(req, res, sessionService,
		// sessionCookieName, false);
		// String userId = dbSession.getAttribute(DbSession.USER_ID,
		// sessionService);

		List<UserReferralHistory> accts = usersService
				.getReferralHistoryOfUser(tableUtils, userId);
		for (UserReferralHistory referralHistory : accts) {
			// referralHistory.setAction("<a href='"+req.getContextPath()+"/users/edit.do?user_id="+userJson.getUser_id()+"'>edit</a>");
			referralHistory
					.setUsedDate(DateUtils.dbTimeStampToSesionDate(
							referralHistory.getCreatedAt(), "IST",
							"dd/MM/yyyy hh:mm a"));
		}

		long count = usersService.fetchTotalUsers(tableUtils, userId);

		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // //
																			// the
																			// total
																			// data
																			// in
																			// db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the
																		// total
																		// data
																		// in db
																		// for
		dt.setsEcho(sEcho);

		return dt;
	}

	@RequestMapping(value = "/referral-history/add", method = RequestMethod.GET)
	public String getAddWalletBalanceForm(@RequestParam String userId,
			Model model, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		UserReferralHistory userReferralHistoryModel = new UserReferralHistory();
		userReferralHistoryModel.setUserId(userId);
		model.addAttribute("userReferralHistoryModel", userReferralHistoryModel);

		return "add-wallet-balance";
	}

	@RequestMapping(value = "/referral-history/add", method = RequestMethod.POST)
	public String addWalletBalanceForm(
			UserReferralHistory userReferralHistoryModel, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		model.addAttribute("userReferralHistoryModel", userReferralHistoryModel);

		// fnFTelegramService.addReferralHistory(referralId, "", "",
		// userReferralHistoryModel.getAmount(),
		// userReferralHistoryModel.getHistoryNote(););
		if (userReferralHistoryModel.getAmount() != 0) {
			usersService.addWalletBalanceOfUser(userReferralHistoryModel);
			
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

			data1.put("chat_id", userReferralHistoryModel.getUserId());
			if (userReferralHistoryModel.getAmount() > 0) {
				data1.put("text", "Dear User, \n\nYour Wallet has been added with Rs "+userReferralHistoryModel.getAmount()+".\n\nFor Queries Buzz@ 9586724633\n\nThanks\nTeam Intricate");
			}else{
				data1.put("text", "Dear User, \n\nYour Wallet has been substracted with Rs "+userReferralHistoryModel.getAmount()+".\n\nFor Queries Buzz@ 9586724633\n\nThanks\nTeam Intricate");
			}
			
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
					.fetchUserDetailsByUserId(userReferralHistoryModel.getUserId());
			if (userReferralHistoryModel.getAmount() > 0) {
				new CommonUtils().sendSms((String)userDetails1.get("phone"),"Dear User, Your Wallet has been added with Rs "+userReferralHistoryModel.getAmount()+". For Queries Buzz@ 9586724633");
			}else{
				new CommonUtils().sendSms((String)userDetails1.get("phone"),"Dear User, Your Wallet has been substracted with Rs "+userReferralHistoryModel.getAmount()+". For Queries Buzz@ 9586724633");
			}
			

		}

		return "redirect:/users/edit/referral-history.do?userId="
				+ userReferralHistoryModel.getUserId();
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String orderDetails(@RequestParam(required = false) String userId,
			Model model, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		model.addAttribute("userId", userId);
		return "user-order";
	}

	@Override
	protected String[] requiredJs() {
		return new String[] { "js/user-referral-history.js", "js/order.js" };
	}
}