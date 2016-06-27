package com.webapp.controllers.secure;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fnf.utils.CommonUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.models.GetUpdatesModel;
import com.webapp.models.Message;
import com.webapp.models.PostUpdateModel;
import com.webapp.models.Update;
import com.webapp.services.FnFTelegramService;

@SuppressWarnings("unused")
@Controller
@EnableScheduling
public class FnFTelegram {

	@Autowired
	private FnFTelegramService fnFTelegramService;

	private static final String URL = ProjectConstant.FNFSAREESBOT;
	private final static String videoUrl = "BAADBQADCQADtcF3BT-RPgKAgb-_Ag";
	private static final String INTRICATE = "113680761";
	private static final String ASHISH = "103686419";
	private static final String SACHIN = "62421877";

	public static long offset = 1;

	@Scheduled(fixedDelay = 1000)
	@RequestMapping(method = RequestMethod.GET)
	public void schedulerMethod() throws ServletException, IOException {

//		telegramSync();

	}

	public String telegramSync() throws ServletException, IOException {

		URL url = new URL(URL + "getUpdates");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("limit", 10);
		data1.put("offset", offset);
		mapper.writeValue(wr, data1);
		InputStream in = conn.getInputStream();
		GetUpdatesModel response = (GetUpdatesModel) mapper.readValue(in,
				GetUpdatesModel.class);
		in.close();

		if (response.isOk()) {
			for (Update result : response.getResult()) {
				int status = 0;

				Message message = result.getMessage();
				if(message==null){
					message = result.getEdited_message();
				}
				if (message.getText() == null
						|| "".equalsIgnoreCase(message.getText()) || fnFTelegramService.isDeactivated(message.getFrom().getId())) {
					offset = result.getUpdate_id() + 1;
				} else if (fnFTelegramService.getActiveOrderStatus(message
						.getFrom().getId())) {

					switch (message.getText().toLowerCase()) {
					case "confirm the product(s)":
						status = addOrderAddress(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "place order":
						status = completeOrder(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "apply coupon":
						status = applyCoupon(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "cancel order":
						status = cancelOrder(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "back":
						status = backButton(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					default:
						status = addOrderProducts(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					}
				} else {

					switch (message.getText().toLowerCase()) {

					case "upto 1000":
						status = sendCollection(message.getFrom().getId(),
								message.getFrom().getFirst_name(), 1000,
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "upto 2000":
						status = sendCollection(message.getFrom().getId(),
								message.getFrom().getFirst_name(), 2000,
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "upto 3000":
						status = sendCollection(message.getFrom().getId(),
								message.getFrom().getFirst_name(), 3000,
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "upto 4000":
						status = sendCollection(message.getFrom().getId(),
								message.getFrom().getFirst_name(), 4000,
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "upto 5000":
						status = sendCollection(message.getFrom().getId(),
								message.getFrom().getFirst_name(), 5000,
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "above 5000":
						status = sendCollection(message.getFrom().getId(),
								message.getFrom().getFirst_name(), 5001,
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "more":
						status = sendMoreProducts(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "cancel":
						status = responseMethod(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "orders":
						status = orderMethod(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "new order":
						status = newOrderMethod(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "last 5 orders":
						status = sendOrderHistory(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "help":
						status = sendHelp(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "account":
						status = account(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "subscribe/unsubscribe":
					case "/stop_images":
						status = subscribeUnsubscribe(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "invite and earn":
						status = referral(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "profile":
						status = sendProfileDetails(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "wallet":
						status = wallet(message.getFrom().getId(), message
								.getFrom().getFirst_name(), message.getText(),
								result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/start":
						status = startResponseMethod(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/unsubscribe_daily_update":
						status = unsubscribeDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/unsubscribe_saree_daily_update":
						status = unsubscribeSareeDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/unsubscribe_kurti_daily_update":
						status = unsubscribeKurtiDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/unsubscribe_gown_daily_update":
						status = unsubscribeGownDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/unsubscribe_lehenga_daily_update":
						status = unsubscribeLehengaDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/unsubscribe_dress_material_daily_update":
						status = unsubscribeDressMaterialDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/subscribe_daily_update":
						status = subscribeDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/subscribe_saree_daily_update":
						status = subscribeSareeDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/subscribe_kurti_daily_update":
						status = subscribeKurtiDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/subscribe_gown_daily_update":
						status = subscribeGownDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/subscribe_lehenga_daily_update":
						status = subscribeLehengaDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					case "/subscribe_dress_material_daily_update":
						status = subscribeDressMaterialDailyUpdate(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;
						break;
					default:
						status = responseMethod(message.getFrom().getId(),
								message.getFrom().getFirst_name(),
								message.getText(), result.getUpdate_id());
						if (status > 0)
							offset = result.getUpdate_id() + 1;

					}

				}
			}
		}
		return "login";

	}

	private int subscribeDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree= true;
				boolean gown= true;
				boolean kurti= true;
				boolean lehenga= true;
				boolean dressMaterial= true;
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Subscribed to products daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int subscribeSareeDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=true;
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Subscribed to sarees daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int subscribeGownDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= false;
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Subscribed to Gown daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	
	
	private int subscribeKurtiDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= true;
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Subscribed to Kurtis daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int subscribeLehengaDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= true;
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Subscribed to Lehenga daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int subscribeDressMaterialDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= true;
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Subscribed to Dress Material daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int unsubscribeDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree= false;
				boolean gown= false;
				boolean kurti= false;
				boolean lehenga= false;
				boolean dressMaterial= false;
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Unsubscribed from products daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int unsubscribeSareeDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=false;
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Unsubscribed from sarees daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int unsubscribeGownDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= false;
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Unsubscribed from Gown daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	
	
	private int unsubscribeKurtiDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= false;
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Unsubscribed from Kurtis daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int unsubscribeLehengaDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= false;
				boolean dressMaterial= (boolean) userDetails.get("dress_material");
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Unsubscribed from Lehenga daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int unsubscribeDressMaterialDailyUpdate(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				boolean saree=(boolean) userDetails.get("saree");
				boolean gown= (boolean) userDetails.get("gown");
				boolean kurti= (boolean) userDetails.get("kurti");
				boolean lehenga= (boolean) userDetails.get("lehenga");
				boolean dressMaterial= false;
				fnFTelegramService.unsubscribeDailyUpdate(id,saree,gown,kurti,lehenga,dressMaterial);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Successfully Unsubscribed from Dress Material daily updates.\nFeel free to contact our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int sendHelp(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Talk to our Sales / Support @ 9586724633 \non Telegram or Whatsapp \n11:00 AM to 7:00 PM \n(Mon. to Saturday)\n"
								+ "OR click here: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int referral(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				if (true) {
					URL url = new URL(URL + "sendMessage");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					ObjectMapper mapper = new ObjectMapper();
					DataOutputStream wr = new DataOutputStream(
							conn.getOutputStream());
					HashMap<String, Object> data1 = new HashMap<String, Object>();
					data1.put("chat_id", id);
					data1.put(
							"text",
							"Share and earn\n---------------\nInvite and help your friend to Join. \n"
									+ "You Earn Rs. 100 (on completion of first order by ur friend)\n"
									+ "And your Friend Earns Rs. 100 (on joining)\n\n"
									+ "TO START Earning, "
									+ "Simply copy Below MSG and Share with your Friends\n---------------------------\n");
					List<Map<String, Object>> productType = fnFTelegramService
							.getProductTypes();
					JSONObject jsonObject = sendProductTypeButtons(productType);
					data1.put("reply_markup", jsonObject.toString());

					mapper.writeValue(wr, data1);
					int responseCode = conn.getResponseCode();
					InputStream in = conn.getInputStream();
					PostUpdateModel response = (PostUpdateModel) mapper
							.readValue(in, PostUpdateModel.class);
					in.close();
					if (response.isOk()) {
						status = 1;
					}
				}

				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Dear Friend,\n\nHere is an amazing Free Saree, Suit, Kurti Franchisee @ Zero Investment and Flat 50% Margin\n\n"
								+ "Use Referral code:  "
								+ userDetails.get("user_id")
								+ "\nTo get Rs. "
								+ ProjectConstant.REFERRALAMOUNT
								+ " Balance\nDownload and Activate Now\n\nYour Saree Suit Kurti Franchisee\n"
								+ "Download (Telegram) From below link :\n-------------\nANDROID : http://bit.ly/1M6xqBp\nIOS: http://apple.co/1HXSlLX\nWINDOWS: http://bit.ly/1Sg5jam"
								+ "\n\nTo Activate Your  Franchisee after TELEGRAM downloading\n\nSearch for\n@FNFSAREESBOT\n\n"
								+ "or You can use this HELP VIDEO - https://youtu.be/vRf8opfII54 \n\n"
								+ "Do not forget to enter referral code: "
								+ userDetails.get("user_id")
								+ "\nto get Rs. 100 balance in your Account\n\n------------");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int wallet(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				List<Map<String, Object>> outputMap = fnFTelegramService
						.getWalletHistory(id);
				String sendMsgString = "Wallet History:\n----------------\n";
				if (outputMap != null) {
					for (Map<String, Object> map : outputMap) {
						sendMsgString += (String) map.get("history_note")
								+ " - Rs " + map.get("amount") + "\n";
					}
				} else {
					sendMsgString = "";
				}

				data1.put("chat_id", id);
				data1.put(
						"text",
						"Your Wallet Balance: Rs "
								+ userDetails.get("referral_balance")
								+ "\n----------------\n" + sendMsgString);
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int sendProfileDetails(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Profile Details\n------------------"
								+ "\nFull Name : "
								+ userDetails.get("user_full_name")
								+ "\nCity : "
								+ userDetails.get("user_city")
								+ "\nMobile : "
								+ userDetails.get("phone")
								+ "\nEmail : "
								+ userDetails.get("email")
								+ ""
								+ "\n----------------------\n"
								+ "To update profile details, please contact customer help: @IntricateHelp");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int subscribeUnsubscribe(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put(
						"text",
						"Click link to Unsubscribe from\n------------------\n"
								+ "All updates: \n/unsubscribe_daily_update\n"
								+"Updates related to Kurti: \n/unsubscribe_kurti_daily_update\n"
								+"Updates related to Gown: \n/unsubscribe_gown_daily_update\n"
								+"Updates related to Lehenga: \n/unsubscribe_lehenga_daily_update\n"
								+"Updates related to Dress Material: \n/unsubscribe_dress_material_daily_update\n"
								+"Updates related to Saree: \n/unsubscribe_saree_daily_update\n\n"
								+"Click link to Subscribe to\n------------------\n"
								+ "All updates: \n/subscribe_daily_update\n"
								+"Updates related to Kurti: \n/subscribe_kurti_daily_update\n"
								+"Updates related to Gown: \n/subscribe_gown_daily_update\n"
								+"Updates related to Lehenga: \n/subscribe_lehenga_daily_update\n"
								+"Updates related to Dress Material: \n/subscribe_dress_material_daily_update\n"
								+"Updates related to Saree: \n/subscribe_saree_daily_update");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}
	
	private int account(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put("text", "Please select option");
				JSONObject jsonObject = new JSONObject();
				JSONArray outerJsonArray = new JSONArray();
				JSONArray innerJsonArray = new JSONArray();
				JSONArray innerJsonArray2 = new JSONArray();
				JSONArray innerJsonArray3 = new JSONArray();
				int i = 0;
				innerJsonArray.put("Help");
				innerJsonArray2.put("Profile");
				innerJsonArray2.put("Wallet");
				innerJsonArray3.put("Subscribe/Unsubscribe");
				outerJsonArray.put(innerJsonArray);
				outerJsonArray.put(innerJsonArray2);
				outerJsonArray.put(innerJsonArray3);
				jsonObject.put("keyboard", outerJsonArray);

				jsonObject.put("resize_keyboard", true);
				jsonObject.put("one_time_keyboard", true);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int sendOrderHistory(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				List<Map<String, Object>> outputList = fnFTelegramService
						.getOrderHistory(id);
				for (Map<String, Object> output : outputList) {

					String address = (String) output.get("address");
					String phone = (String) output.get("phone");
					String order_status = (String) output.get("order_status");
					int total_discounted_price = (int) output
							.get("total_discounted_price");
					int total_courier_charge = (int) output
							.get("total_courier");
					int total = (int) output.get("total");
					int order_id = (int) output.get("order_id");
					int referral_discount = (int) (long) output
							.get("referral_wallet_discount");
					int coupon_discount = (int) (long) output
							.get("coupon_discount");

					int cod_charge = 0;
					int cod_payment = 0;
					int internet_handling_charge = 0;
					int online_payment = 0;
					String payment_method = "";
					if (output.get("payment_method") != null) {
						payment_method = (String) output.get("payment_method");
					}
					if (output.get("cod_charge") != null) {
						cod_charge = (int) (long) output.get("cod_charge");
					}
					if (output.get("cod_payment") != null) {
						cod_payment = (int) (long) output.get("cod_payment");
					}
					if (output.get("internet_handling_charge") != null) {
						internet_handling_charge = (int) (long) output
								.get("internet_handling_charge");
					}
					if (output.get("online_payment") != null) {
						online_payment = (int) (long) output
								.get("online_payment");
					}

					List<Map<String, Object>> products = fnFTelegramService
							.getOrderProducts(order_id);
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();
					data2.put("chat_id", id);
					String outputProducts = "";
					for (Map<String, Object> map : products) {
						outputProducts += "\nSub Order Id : "
								+ map.get("order_product_id")
								+ "\nProduct No. : " + map.get("product_id")
								+ "\nMRP : " + map.get("price")
								+ "\nFranchisee Price : "
								+ map.get("discounted_price")
								+ "\n------------------------";
					}
					String orderDetailsString = "------------------------\nOrder No. : "
							+ order_id
							+ "\n"
							+ "------------------------"
							+ outputProducts
							+ "\nFranchisee Price total: "
							+ total_discounted_price
							+ "\nDelivery Charge total: "
							+ total_courier_charge;
					if (referral_discount > 0) {
						orderDetailsString += "\nWallet Used : "
								+ referral_discount;
					}
					if (coupon_discount > 0) {
						orderDetailsString += "\nCoupon Discount : "
								+ coupon_discount;
					}
					if (payment_method.equalsIgnoreCase("90% cash on delivery")) {
						orderDetailsString += "\nCOD charge : " + cod_charge
								+ "\n------------------------"
								+ "\nOnline Payment : " + online_payment
								+ "\nCash on Delivery Payment : " + cod_payment;

					} else {
						orderDetailsString += "\n------------------------"
								+ "\nInternet Handling Charge : "
								+ internet_handling_charge;
					}
					String payment_method_string = "";
					if (!"".equalsIgnoreCase(payment_method)) {
						payment_method_string = "\n------------------------"
								+ "\nPayment Method : " + payment_method;
					}
					data2.put(
							"text",
							orderDetailsString
									+ "\n------------------------"
									+ "\nTotal Payable : "
									+ total
									+ payment_method_string
									+ "\n------------------------"
									+ "\nDelivery address : "
									+ address
									+ "\n------------------------"
									+ "\nPhone : "
									+ phone
									+ "\nOrder Status : "
									+ order_status
									+ "\nTo chat about your order and share Bank payment confirmation\nPlease click here: @IntricateHelp");

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("hide_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());
					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}
				}
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put("text", "Order History Fetched Successfully");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				in.close();
				if (response.isOk()) {
					status = 1;
				}

			}
		}

		return status;
	}

	private int completeOrder(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				Map<String, Object> output = fnFTelegramService
						.getOrderPrice(id);
				if (output == null || output.get("total_price") == null) {
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();
					data2.put("chat_id", id);
					data2.put("text",
							"Please enter product numbers seperated by comma (Ex: 323232323,31322132)");
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					innerJsonArray1.put("Cancel Order");
					outerJsonArray.put(innerJsonArray1);
					jsonObject.put("keyboard", outerJsonArray);
					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());
					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}
				}
				int total_price = (int) (long) output.get("total_price");
				int total_discounted_price = (int) (long) output
						.get("total_discounted_price");
				int total_weight = (int) (long) output.get("total_weight");
				String payment_method = (String) output.get("payment_method");
				String courier_method = (String) output.get("courier_method");
				int total_courier_charge = 0;
				if (courier_method.equalsIgnoreCase("fast")
						|| payment_method
								.equalsIgnoreCase("90% cash on delivery")) {
					courier_method = "fast";
					int i = total_weight / 500;
					if (total_weight % 500 == 0) {
						for (int count = 1; count <= i; count++) {
							if (count % 2 != 0) {
								total_courier_charge += 80;
							} else {
								total_courier_charge += 60;
							}
						}
					} else {
						i = i + 1;
						for (int count = 1; count <= i; count++) {
							if (count % 2 != 0) {
								total_courier_charge += 80;
							} else {
								total_courier_charge += 60;
							}
						}
					}
				} else if (courier_method.equalsIgnoreCase("local")) {
					int i = total_weight / 500;
					if (total_weight % 500 == 0) {
						for (int count = 1; count <= i; count++) {
							if (count % 2 != 0) {
								total_courier_charge += 70;
							} else {
								total_courier_charge += 30;
							}
						}
					} else {
						i = i + 1;
						for (int count = 1; count <= i; count++) {
							if (count % 2 != 0) {
								total_courier_charge += 70;
							} else {
								total_courier_charge += 30;
							}
						}
					}
				}

				
				  //int total_courier_charge = fnFTelegramService.getCourierCharge(total_weight);
				 
				int order_id = (int) output.get("order_id");
				String coupon_id = null;
				Integer coupon_discount = 0;
				Integer referral_discount = 0;
				if (output.get("coupon_id") != null) {
					coupon_id = (String) output.get("coupon_id");
					Map<String, Object> couponDetails = fnFTelegramService
							.getCouponDetailsFromCouponId(coupon_id);
					coupon_discount = total_discounted_price
							* (int) (long) couponDetails
									.get("discount_in_percentage") / 100;
					if (coupon_discount > (int) (long) couponDetails
							.get("max_disocunt")) {
						coupon_discount = (int) (long) couponDetails
								.get("max_disocunt");
					}
				}
				if (userDetails.get("referral_balance") != null
						&& (long) userDetails.get("referral_balance") > 0) {
					referral_discount = (int) (total_discounted_price);
					if (referral_discount > (int) (long) userDetails
							.get("referral_balance")) {

						referral_discount = (int) (long) userDetails
								.get("referral_balance");

						String user_used = null;
						int amount = referral_discount;
						String history_note = "Used in Order No. : " + order_id;
						fnFTelegramService.addReferralHistory(id, user_used,
								order_id, amount, history_note);

						fnFTelegramService.updateReferralBalance(id, 0);
					} else {

						String user_used = null;
						int amount = referral_discount;
						String history_note = "Used in Order No. : " + order_id;
						fnFTelegramService.addReferralHistory(id, user_used,
								order_id, amount, history_note);

						fnFTelegramService.updateReferralBalance(
								id,
								(int) (long) userDetails
										.get("referral_balance")
										- referral_discount);
					}
				}

				int total = total_discounted_price + total_courier_charge
						- coupon_discount - referral_discount;
				int internet_handling_charge = 0;
				int online_payment = 0;
				int cod_payment = 0;
				int cod_charge = 0;
				if (payment_method.equalsIgnoreCase("online payment")) {
					internet_handling_charge = (int) ((total / .98) - total);
					total = total + internet_handling_charge;
				} else if (payment_method
						.equalsIgnoreCase("90% cash on delivery")) {
					cod_charge = (int) ((total / .98) - total);
					if (cod_charge < 50) {
						cod_charge = 50;
					}
					total = total + cod_charge;
					// Cash On delivery 10% 20% and 30% on total amount
					if(total<=1000){
						cod_payment = (int) (total * 0.9);
						int amount50roundoff = cod_payment % 50;
						cod_payment = cod_payment - amount50roundoff;
					} else if(total<=2000){
						cod_payment = (int) (total * 0.8);
						int amount50roundoff = cod_payment % 50;
						cod_payment = cod_payment - amount50roundoff;
					} else {
							cod_payment = (int) (total * 0.7);
							int amount50roundoff = cod_payment % 50;
							cod_payment = cod_payment - amount50roundoff;
					}
					
					online_payment = total - cod_payment;
				}

				status = fnFTelegramService.completeOrder(order_id,
						total_price, total_discounted_price,
						total_courier_charge, total, referral_discount,
						coupon_discount, internet_handling_charge,
						online_payment, cod_payment, cod_charge,
						courier_method, payment_method);
				List<Map<String, Object>> products = fnFTelegramService
						.getOrderProducts(order_id);
				URL url1 = new URL(URL + "sendMessage");
				HttpURLConnection conn1 = (HttpURLConnection) url1
						.openConnection();
				conn1.setRequestMethod("POST");
				conn1.setRequestProperty("Content-Type", "application/json");
				conn1.setDoOutput(true);
				ObjectMapper mapper1 = new ObjectMapper();
				DataOutputStream wr1 = new DataOutputStream(
						conn1.getOutputStream());
				HashMap<String, Object> data2 = new HashMap<String, Object>();
				data2.put("chat_id", id);
				String outputProducts = "";
				for (Map<String, Object> map : products) {
					outputProducts += "\nSub Order Id : "
							+ map.get("order_product_id") + "\nProduct No. : "
							+ map.get("product_id") + "\nMRP : "
							+ map.get("price") + "\nFranchisee Price : "
							+ map.get("discounted_price")
							+ "\n------------------------";
				}
				String orderDetailsString = "Order placed successfully\n------------------------\nOrder No. : "
						+ order_id
						+ "\n"
						+ "------------------------"
						+ outputProducts
						+ "\nFranchisee Price Total: "
						+ total_discounted_price
						+ "\nDelivery Charge : "
						+ total_courier_charge;
				if (referral_discount > 0) {
					orderDetailsString += "\nWallet Used : "
							+ referral_discount;
				}
				if (coupon_discount > 0) {
					orderDetailsString += "\nCoupon Discount : "
							+ coupon_discount;
				}
				if (payment_method.equalsIgnoreCase("90% cash on delivery")) {
					orderDetailsString += "\nCOD charge : " + cod_charge
							+ "\n------------------------"
							+ "\nOnline Payment : " + online_payment
							+ "\nCash on Delivery Payment : " + cod_payment;

				} else {
					orderDetailsString += "\n------------------------"
							+ "\nInternet Handling Charge : "
							+ internet_handling_charge;
				}
				data2.put(
						"text",
						orderDetailsString
								+ "\n------------------------"
								+ "\nTotal Payable : "
								+ total
								+ "\n------------------------"
								+ "\nPAYMENT MODE: ONLINE"
								+ "\nPayment Link: http://bit.ly/24mQxUB"
								+ "\nOn Payment Page Please enter:"
								+ "\nAmount = Total Payable"
								+ "\nDescription = Order ID"
								+ "\n------------------------\n"
								+ "\nBank Details"
								+ "\n------------------------\n"
								+ "Account Name : Intricate Fashions India Pvt Ltd\nA/C NO: 1335 102 0000 12582\n"
								+ "IFSC CODE: IBKL0001335\nBank: IDBI BANK LTD\nBranch: SOSYO CIRCLE, UdhanaMagdalla, Surat\n------------------------"
								+ "\nTo chat about your order and share Bank payment confirmation\nPlease click here: @IntricateHelp or call +91 9586724633"
								+ "\nP.S: \n1. Stock is reserved and order is confirmed only after receiving payment.\n"
								+ "2. Shipping charges mentioned are only for INDIA. For out of INDIA shipping pls click here: @INTRICATEHELP");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data2.put("reply_markup", jsonObject.toString());
				mapper1.writeValue(wr1, data2);
				int responseCode1 = conn1.getResponseCode();
				InputStream in1 = conn1.getInputStream();
				PostUpdateModel response1 = (PostUpdateModel) mapper1
						.readValue(in1, PostUpdateModel.class);
				in1.close();
				if (response1.isOk()) {
					status = 1;
				}
				if (true) {
					sendOrderNotification(id, first_name,
							total_discounted_price, total_courier_charge,
							total, order_id, outputProducts, ASHISH,
							referral_discount, coupon_discount,
							internet_handling_charge, payment_method,
							online_payment, cod_charge, cod_payment,
							courier_method);
				}
				if (true) {
					sendOrderNotification(id, first_name,
							total_discounted_price, total_courier_charge,
							total, order_id, outputProducts, SACHIN,
							referral_discount, coupon_discount,
							internet_handling_charge, payment_method,
							online_payment, cod_charge, cod_payment,
							courier_method);

				}
				if (true) {
					sendOrderNotification(id, first_name,
							total_discounted_price, total_courier_charge,
							total, order_id, outputProducts, INTRICATE,
							referral_discount, coupon_discount,
							internet_handling_charge, payment_method,
							online_payment, cod_charge, cod_payment,
							courier_method);

				}
			}
		}

		return status;
	}

	private void sendOrderNotification(Integer id, String first_name,
			int total_discounted_price, int total_courier_charge, int total,
			int order_id, String outputProducts, String userIdNoti,
			int referral_discount, int coupon_discount,
			int internet_handling_charge, String payment_method,
			int online_payment, int cod_charge, int cod_payment,
			String courier_method) throws MalformedURLException, IOException,
			ProtocolException, JsonGenerationException, JsonMappingException,
			JsonParseException {
		URL url3 = new URL(URL + "sendMessage");
		HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
		conn3.setRequestMethod("POST");
		conn3.setRequestProperty("Content-Type", "application/json");
		conn3.setDoOutput(true);
		ObjectMapper mapper3 = new ObjectMapper();
		DataOutputStream wr3 = new DataOutputStream(conn3.getOutputStream());
		HashMap<String, Object> data3 = new HashMap<String, Object>();

		data3.put("chat_id", userIdNoti);
		String orderDetailsString = "--------------------\nNew Order\n--------------------\n"
				+ "User Id : "
				+ id
				+ "\nUser Name : "
				+ first_name
				+ "\n------------------------\nOrder No. : "
				+ order_id
				+ "\n"
				+ "------------------------"
				+ outputProducts
				+ "\nFranchisee Price Total: "
				+ total_discounted_price
				+ "\nDelivery Charge : " + total_courier_charge;
		if (referral_discount > 0) {
			orderDetailsString += "\nWallet Used : " + referral_discount;
		}
		if (coupon_discount > 0) {
			orderDetailsString += "\nCoupon Discount : " + coupon_discount;
		}
		if (payment_method.equalsIgnoreCase("90% cash on delivery")) {
			orderDetailsString += "\nCOD charge : " + cod_charge
					+ "\n------------------------"
					+ "\nOnline Payment : " + online_payment
					+ "\nCash on Delivery Payment : " + cod_payment;

		} else {
			orderDetailsString += "\n------------------------"
					+ "\nInternet Handling Charge : "
					+ internet_handling_charge;
		}
		data3.put("text", orderDetailsString + "\n------------------------"
				+ "\nTotal Payable : " + total + "\n------------------------"
				+ "\nPayment Method : " + payment_method
				+ "\n------------------------" + "\nCourier Method : "
				+ courier_method + "\n------------------------");
		// "91734453" 
		mapper3.writeValue(wr3, data3);
		int responseCode3 = conn3.getResponseCode();
		InputStream in3 = null;
		PostUpdateModel response3 = null;
		if (responseCode3 == 200) {
			in3 = conn3.getInputStream();
			response3 = (PostUpdateModel) mapper3.readValue(in3,
					PostUpdateModel.class);
			in3.close();
		}
	}

	private int addOrderMobile(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		if (text.length() < 10) {
			JSONObject jsonObject = new JSONObject();
			JSONArray outerJsonArray = new JSONArray();
			JSONArray innerJsonArray2 = new JSONArray();
			JSONArray innerJsonArray1 = new JSONArray();
			innerJsonArray2.put("Cancel Order");
			innerJsonArray1.put("Back");
			outerJsonArray.put(innerJsonArray2);
			outerJsonArray.put(innerJsonArray1);
			jsonObject.put("keyboard", outerJsonArray);
			data2.put("chat_id", id);
			data2.put("text",
					"Invalid mobile number. Please enter valid mobile number");

			jsonObject.put("resize_keyboard", true);
			jsonObject.put("one_time_keyboard", true);

			data2.put("reply_markup", jsonObject.toString());
			mapper1.writeValue(wr1, data2);
			int responseCode1 = conn1.getResponseCode();
			InputStream in1 = conn1.getInputStream();
			PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(
					in1, PostUpdateModel.class);
			in1.close();
			if (response1.isOk()) {
				status = 1;
			}

		} else {

			status = fnFTelegramService.addOrderMobile(id, text);
			data2.put("chat_id", id);
			data2.put("text",
					"Please select courier options:\nFAST: Rs. 140/kg\nLocal: Rs. 100/kg");
			JSONObject jsonObject = new JSONObject();
			JSONArray outerJsonArray = new JSONArray();
			JSONArray innerJsonArray2 = new JSONArray();
			JSONArray innerJsonArray3 = new JSONArray();
			JSONArray innerJsonArray4 = new JSONArray();
			JSONArray innerJsonArray = new JSONArray();
			innerJsonArray.put("FAST");
			innerJsonArray.put("Local");

			innerJsonArray4.put("Cancel Order");
			innerJsonArray2.put("Back");
			outerJsonArray.put(innerJsonArray);
			outerJsonArray.put(innerJsonArray4);
			outerJsonArray.put(innerJsonArray2);
			jsonObject.put("keyboard", outerJsonArray);
			jsonObject.put("resize_keyboard", true);
			jsonObject.put("one_time_keyboard", true);

			data2.put("reply_markup", jsonObject.toString());
			mapper1.writeValue(wr1, data2);
			int responseCode1 = conn1.getResponseCode();
			InputStream in1 = conn1.getInputStream();
			PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(
					in1, PostUpdateModel.class);
			
			in1.close();
			if (response1.isOk()) {
				status = 1;
			}

			// shyam do it wisely

		}

		return status;
	}

	private int addCourierMethod(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;
		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		if (text.equalsIgnoreCase("fast") || text.equalsIgnoreCase("local")
				|| text.equalsIgnoreCase("to pay")) {
			status = fnFTelegramService.addCourierMethod(id, text);
			JSONObject jsonObject = new JSONObject();
			JSONArray outerJsonArray = new JSONArray();
			JSONArray innerJsonArray2 = new JSONArray();
			JSONArray innerJsonArray3 = new JSONArray();
			JSONArray innerJsonArray4 = new JSONArray();
			JSONArray innerJsonArray = new JSONArray();
			innerJsonArray.put("Bank Transfer");
			innerJsonArray.put("Online Payment");
			innerJsonArray2.put("90% Cash On Delivery");
			innerJsonArray4.put("Cancel Order");
			outerJsonArray.put(innerJsonArray);
			outerJsonArray.put(innerJsonArray2);
			outerJsonArray.put(innerJsonArray4);
			jsonObject.put("keyboard", outerJsonArray);
			data2.put("chat_id", id);
			data2.put(
					"text",
					"Please select Payment Options:\n--------------\nBank Transfer: Free\n\nOnline Payment: 2% charges PG charges will be added"
							+ "\n\n90% COD Payment : +2% COD Charges(min Rs 50.00) and 10-30% to be paid Online for order booking"
							+ "\n--------------\nNote: COD payment method is available only in FAST courier. COD selection will change courier to FAST");

			jsonObject.put("resize_keyboard", true);
			jsonObject.put("one_time_keyboard", true);

			data2.put("reply_markup", jsonObject.toString());
			mapper1.writeValue(wr1, data2);
			int responseCode1 = conn1.getResponseCode();
			InputStream in1 = conn1.getInputStream();
			PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(
					in1, PostUpdateModel.class);
			
			in1.close();
			if (response1.isOk()) {
				status = 1;
			}

		} else {

			data2.put("chat_id", id);
			data2.put("text", "Please select courier options from the menu");
			JSONObject jsonObject = new JSONObject();
			JSONArray outerJsonArray = new JSONArray();
			JSONArray innerJsonArray2 = new JSONArray();
			JSONArray innerJsonArray3 = new JSONArray();
			JSONArray innerJsonArray4 = new JSONArray();
			JSONArray innerJsonArray = new JSONArray();
			innerJsonArray.put("FAST");
			innerJsonArray.put("Local");
			innerJsonArray4.put("Cancel Order");
			innerJsonArray2.put("Back");
			outerJsonArray.put(innerJsonArray);
			outerJsonArray.put(innerJsonArray4);
			outerJsonArray.put(innerJsonArray2);
			jsonObject.put("keyboard", outerJsonArray);
			jsonObject.put("resize_keyboard", true);
			jsonObject.put("one_time_keyboard", true);

			data2.put("reply_markup", jsonObject.toString());
			mapper1.writeValue(wr1, data2);
			int responseCode1 = conn1.getResponseCode();
			InputStream in1 = conn1.getInputStream();
			PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(
					in1, PostUpdateModel.class);
			
			in1.close();
			if (response1.isOk()) {
				status = 1;
			}

		}

		return status;
	}

	private int addPaymentMethod(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;
		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		if (text.equalsIgnoreCase("bank transfer")
				|| text.equalsIgnoreCase("online payment")
				|| text.equalsIgnoreCase("90% cash on delivery")) {
			status = fnFTelegramService.addPaymentMethod(id, text);

			data2.put("chat_id", id);
			data2.put("text", "Place the order\nOr\nApply Coupon");
			JSONObject jsonObject = new JSONObject();
			JSONArray outerJsonArray = new JSONArray();
			JSONArray innerJsonArray2 = new JSONArray();
			JSONArray innerJsonArray3 = new JSONArray();
			JSONArray innerJsonArray = new JSONArray();
			innerJsonArray.put("Place Order");
			innerJsonArray3.put("Apply Coupon");
			innerJsonArray2.put("Cancel Order");
			outerJsonArray.put(innerJsonArray);
			outerJsonArray.put(innerJsonArray3);
			outerJsonArray.put(innerJsonArray2);
			jsonObject.put("keyboard", outerJsonArray);

			jsonObject.put("resize_keyboard", true);
			jsonObject.put("one_time_keyboard", true);

			data2.put("reply_markup", jsonObject.toString());
			mapper1.writeValue(wr1, data2);
			int responseCode1 = conn1.getResponseCode();
			InputStream in1 = conn1.getInputStream();
			PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(
					in1, PostUpdateModel.class);
			
			in1.close();
			if (response1.isOk()) {
				status = 1;
			}

		} else {

			JSONObject jsonObject = new JSONObject();
			JSONArray outerJsonArray = new JSONArray();
			JSONArray innerJsonArray2 = new JSONArray();
			JSONArray innerJsonArray3 = new JSONArray();
			JSONArray innerJsonArray4 = new JSONArray();
			JSONArray innerJsonArray = new JSONArray();
			innerJsonArray.put("Bank Transfer");
			innerJsonArray.put("Online Payment");
			innerJsonArray2.put("90% Cash On Delivery");
			innerJsonArray4.put("Cancel Order");
			outerJsonArray.put(innerJsonArray);
			outerJsonArray.put(innerJsonArray2);
			outerJsonArray.put(innerJsonArray4);
			jsonObject.put("keyboard", outerJsonArray);
			data2.put("chat_id", id);
			data2.put("text", "Please select Payment Option from the menu");

			jsonObject.put("resize_keyboard", true);
			jsonObject.put("one_time_keyboard", true);

			data2.put("reply_markup", jsonObject.toString());
			mapper1.writeValue(wr1, data2);
			int responseCode1 = conn1.getResponseCode();
			InputStream in1 = conn1.getInputStream();
			PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(
					in1, PostUpdateModel.class);
			
			in1.close();
			if (response1.isOk()) {
				status = 1;
			}

		}

		return status;
	}

	private int addOrderAddressToMobile(Integer id, String first_name,
			String text, Integer update_id) throws IOException {
		int status = 0;

		status = fnFTelegramService.addOrderAddressToMobile(id, text);
		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		data2.put("chat_id", id);
		data2.put("text", "Please enter mobile no.");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray1 = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		innerJsonArray1.put("Cancel Order");
		innerJsonArray2.put("Back");
		outerJsonArray.put(innerJsonArray1);
		outerJsonArray.put(innerJsonArray2);
		jsonObject.put("keyboard", outerJsonArray);
		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data2.put("reply_markup", jsonObject.toString());
		mapper1.writeValue(wr1, data2);
		int responseCode1 = conn1.getResponseCode();
		InputStream in1 = conn1.getInputStream();
		PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(in1,
				PostUpdateModel.class);
		
		in1.close();
		if (response1.isOk()) {
			status = 1;
		}

		return status;
	}

	private int cancelOrder(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				status = fnFTelegramService.cancelNewOrder(id);
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				data1.put("chat_id", id);
				data1.put("text", "Order Cancelled Successfully");
				List<Map<String, Object>> productType = fnFTelegramService
						.getProductTypes();
				JSONObject jsonObject = sendProductTypeButtons(productType);
				data1.put("reply_markup", jsonObject.toString());

				mapper.writeValue(wr, data1);
				int responseCode = conn.getResponseCode();
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				
				in.close();
				if (response.isOk()) {
					status = 1;
				}
			}
		}
		return status;
	}

	private int addOrderAddress(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				status = fnFTelegramService.addAddress(id);
				URL url1 = new URL(URL + "sendMessage");
				HttpURLConnection conn1 = (HttpURLConnection) url1
						.openConnection();
				conn1.setRequestMethod("POST");
				conn1.setRequestProperty("Content-Type", "application/json");
				conn1.setDoOutput(true);
				ObjectMapper mapper1 = new ObjectMapper();
				DataOutputStream wr1 = new DataOutputStream(
						conn1.getOutputStream());
				HashMap<String, Object> data2 = new HashMap<String, Object>();
				data2.put("chat_id", id);
				data2.put("text",
						"Please enter delivery address\nEx: O-501, Abc Colony, Wakad, Pune-411057");
				JSONObject jsonObject = new JSONObject();
				JSONArray outerJsonArray = new JSONArray();
				JSONArray innerJsonArray1 = new JSONArray();
				innerJsonArray1.put("Cancel Order");
				outerJsonArray.put(innerJsonArray1);
				jsonObject.put("keyboard", outerJsonArray);
				jsonObject.put("resize_keyboard", true);
				jsonObject.put("one_time_keyboard", true);
				data2.put("reply_markup", jsonObject.toString());

				mapper1.writeValue(wr1, data2);
				int responseCode1 = conn1.getResponseCode();
				InputStream in1 = conn1.getInputStream();
				PostUpdateModel response1 = (PostUpdateModel) mapper1
						.readValue(in1, PostUpdateModel.class);
				
				in1.close();
				if (response1.isOk()) {
					status = 1;
				}
			}
		}

		return status;
	}

	private int applyCoupon(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);
		List<Map<String, Object>> couponsList = new ArrayList<Map<String, Object>>();
		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				if ((long) userDetails.get("referral_balance") > 0) {
					couponsList = fnFTelegramService
							.getActiveCouponWithReferral();
				} else {
					couponsList = fnFTelegramService.getAllActiveCoupon();
				}
				String coupons = "Coupons\n-----------------\n";
				if (!couponsList.isEmpty()) {
					status = fnFTelegramService.addCouponRecordStatus(id);
					for (Map<String, Object> couponmap : couponsList) {
						coupons += couponmap.get("coupon_code") + " : Get "
								+ couponmap.get("discount_in_percentage");
						coupons += "% discount on Franchisee Price\nMax discount: Rs "
								+ couponmap.get("max_disocunt")
								+"\nMax "+couponmap.get("maximum_time_used")+" times per user"
								+"\nMin Franchisee total to apply this coupon:  Rs "+couponmap.get("discount_on_minimum_price")+""
								+ "\n-----------------\n";
					}
					coupons += "Please enter coupon";
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();

					data2.put("chat_id", id);
					data2.put("text", coupons);
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					innerJsonArray1.put("Cancel Order");
					outerJsonArray.put(innerJsonArray1);

					jsonObject.put("keyboard", outerJsonArray);

					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());

					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}
				} else {
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();

					coupons = "No Active Coupons";
					data2.put("chat_id", id);
					data2.put("text", coupons);
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray2 = new JSONArray();
					JSONArray innerJsonArray = new JSONArray();
					innerJsonArray.put("Place Order");
					innerJsonArray2.put("Cancel Order");
					outerJsonArray.put(innerJsonArray);
					outerJsonArray.put(innerJsonArray2);
					jsonObject.put("keyboard", outerJsonArray);

					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);

					data2.put("reply_markup", jsonObject.toString());
					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}
				}

			}
		}

		return status;
	}

	private int backButton(Integer id, String first_name, String text,
			Integer update_id) throws IOException {
		int status = 0;

		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);
		List<Map<String, Object>> couponsList = new ArrayList<Map<String, Object>>();
		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				String recordStatusOrder = fnFTelegramService
						.getRecordStatus(id);
				if ("C".equalsIgnoreCase(recordStatusOrder)) {
					status = fnFTelegramService.addAddress(id);
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();
					data2.put("chat_id", id);
					data2.put("text",
							"Please re-enter delivery address\nEx: O-501, Abc Colony, Wakad, Pune-411057");
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					JSONArray innerJsonArray2 = new JSONArray();
					innerJsonArray1.put("Cancel Order");
					
					outerJsonArray.put(innerJsonArray1);
					
					jsonObject.put("keyboard", outerJsonArray);
					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());

					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}
				} else if ("S".equalsIgnoreCase(recordStatusOrder)) {
					status = fnFTelegramService.backToMobile(id);
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();
					data2.put("chat_id", id);
					data2.put("text", "Please re-enter mobile number");
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					JSONArray innerJsonArray2 = new JSONArray();
					innerJsonArray1.put("Cancel Order");
					innerJsonArray2.put("Back");
					outerJsonArray.put(innerJsonArray1);
					outerJsonArray.put(innerJsonArray2);
					jsonObject.put("keyboard", outerJsonArray);
					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());

					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}
				}
			}
		}

		return status;
	}

	private int addOrderProducts(Integer id, String first_name, String text,
			Integer update_id) throws IOException {

		int status = 0;
		int validCount = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				String recordStatusOrder = fnFTelegramService
						.getRecordStatus(id);
				if ("B".equalsIgnoreCase(recordStatusOrder)) {
					status = addOrderAddressToMobile(id, first_name, text,
							update_id);
					return status;
				} else if ("C".equalsIgnoreCase(recordStatusOrder)) {
					status = addOrderMobile(id, first_name, text, update_id);
					return status;
				} else if ("S".equalsIgnoreCase(recordStatusOrder)) {
					status = addCourierMethod(id, first_name, text, update_id);
					return status;
				} else if ("P".equalsIgnoreCase(recordStatusOrder)) {
					status = addPaymentMethod(id, first_name, text, update_id);
					return status;
				} else if ("G".equalsIgnoreCase(recordStatusOrder)) {
					Map<String, Object> couponDetails = fnFTelegramService
							.getCouponDetailsFromCouponCode(id,text);
					if (couponDetails == null) {
						status = sendInvalidCouponNotification(id, status);
						return status;
					} else {
						
						boolean couponBlocked=fnFTelegramService.isCouponBlocked(id,(String) couponDetails.get("coupon_id"));
						if(couponBlocked){
							status = sendCouponBlockedNotification(id, status);
							return status;
						}else{
							int noOfTimesUsed = fnFTelegramService.noOfTimesUsed(id,(String) couponDetails.get("coupon_id"));
							if(noOfTimesUsed>=(long)couponDetails.get("maximum_time_used")){
								status = sendmaxTimeCouponUsedNotification(id, status);
								return status;
							}else if((long)couponDetails.get("total_discounted_price")<(long)couponDetails.get("discount_on_minimum_price")){
								status = sendlowMinPayableTotalCouponUsedNotification(id, status);
								return status;
							}else{
								status = sendSuccessfullyAppliedCouponNotification(id,
										status, couponDetails);
								return status;
							}
						}
					}

				} else if ("E".equalsIgnoreCase(recordStatusOrder)) {
					status = sendConfirmProductButton(id, status);
				} else if ("F".equalsIgnoreCase(recordStatusOrder)) {
					status = sendPlaceOrderAndApplyCouponButton(id, status);
				} else {
					String[] products = text.split(",");
					for (String product : products) {
						boolean valid = fnFTelegramService.checkProduct(product
								.trim());
						// fnFTelegramService.getQuantity(product.trim());
						if (!valid) {
							validCount++;
							status = sendInvalidProductNotification(id, status,
									product);
						}
					}

					int availableProducts = 0;
					if (validCount == 0)
						for (String product : products) {
							Map<String, Object> productDetails = fnFTelegramService
									.getProductDetails(product.trim());
							if (productDetails == null) {
								status = sendProductOutOfStockNotification(id,
										status, product);
							} else {
								availableProducts++;
								String orderId = fnFTelegramService
										.getOrderId(id);// product_image
								status = fnFTelegramService.addOrderProducts(
										Integer.parseInt(orderId),
										(String) productDetails
												.get("product_id"),
										(int) productDetails
												.get("product_price"),
										(int) productDetails.get("weight"));
								if (true) {
									status = sendProductPhotoForConfirmation(
											id, status, productDetails);
								}
								status = sendProductMRPAndFranchiseePrice(id,
										status, productDetails);
							}
						}// for
					if (availableProducts > 0) {
						status = sendConfirmProductButton(id);
					}

				}
			}
		}
		return status;
	}

	private int sendConfirmProductButton(Integer id)
			throws MalformedURLException, IOException, ProtocolException,
			JsonGenerationException, JsonMappingException, JsonParseException {
		int status;
		status = fnFTelegramService.confirmProducts(id);
		URL url = new URL(URL + "sendMessage");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("chat_id", id);
		data1.put("text", "Confirm the product(s)");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		JSONArray innerJsonArray1 = new JSONArray();
		int i = 0;
		innerJsonArray.put("Confirm the product(s)");
		innerJsonArray1.put("Cancel Order");

		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray1);

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendProductMRPAndFranchiseePrice(Integer id, int status,
			Map<String, Object> productDetails) throws MalformedURLException,
			IOException, ProtocolException, JsonGenerationException,
			JsonMappingException, JsonParseException {
		URL url = new URL(URL + "sendMessage");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("chat_id", id);
		data1.put(
				"text",
				"Product No. : " + (String) productDetails.get("product_id")
						+ "\nMRP: Rs "
						+ (int) productDetails.get("product_price")
						+ "\nFranchisee Price: Rs "
						+ (int) productDetails.get("product_price") / 2);
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendProductPhotoForConfirmation(Integer id, int status,
			Map<String, Object> productDetails) throws MalformedURLException,
			IOException, ProtocolException, JsonGenerationException,
			JsonMappingException, JsonParseException {
		URL url = new URL(URL + "sendPhoto");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("chat_id", id);
		data1.put("photo", String.valueOf(productDetails.get("product_image")));
		if (productDetails.get("caption") == null
				|| "".equalsIgnoreCase((String) productDetails.get("caption"))) {
			data1.put("caption",
					String.valueOf(productDetails.get("product_id")));
		} else {
			data1.put("caption", (String) productDetails.get("caption") + "\n"
					+ String.valueOf(productDetails.get("product_id")));
		}

		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendProductOutOfStockNotification(Integer id, int status,
			String product) throws MalformedURLException, IOException,
			ProtocolException, JsonGenerationException, JsonMappingException,
			JsonParseException {
		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		data2.put("chat_id", id);
		data2.put("text", "Product number: '" + product.trim()
				+ "' is Out of Stock.\nPlease enter another product number.");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();

		innerJsonArray.put("Cancel Order");

		outerJsonArray.put(innerJsonArray);

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data2.put("reply_markup", jsonObject.toString());
		mapper1.writeValue(wr1, data2);
		int responseCode1 = conn1.getResponseCode();
		InputStream in1 = conn1.getInputStream();
		PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(in1,
				PostUpdateModel.class);
		
		in1.close();
		if (response1.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendInvalidProductNotification(Integer id, int status,
			String product) throws MalformedURLException, IOException,
			ProtocolException, JsonGenerationException, JsonMappingException,
			JsonParseException {
		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		data2.put("chat_id", id);
		data2.put("text", "Invalid product number: '" + product.trim()
				+ "'\nPlease check and enter again");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray1 = new JSONArray();
		int i = 0;

		innerJsonArray1.put("Cancel Order");

		outerJsonArray.put(innerJsonArray1);

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data2.put("reply_markup", jsonObject.toString());
		mapper1.writeValue(wr1, data2);
		int responseCode1 = conn1.getResponseCode();
		InputStream in1 = conn1.getInputStream();
		PostUpdateModel response1 = (PostUpdateModel) mapper1.readValue(in1,
				PostUpdateModel.class);
		
		in1.close();
		if (response1.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendPlaceOrderAndApplyCouponButton(Integer id, int status)
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
		data1.put("chat_id", id);
		data1.put("text", "please select from the menu options");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		JSONArray innerJsonArray3 = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		innerJsonArray.put("Place Order");
		innerJsonArray3.put("Apply Coupon");
		innerJsonArray2.put("Cancel Order");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray3);
		outerJsonArray.put(innerJsonArray2);
		int i = 0;

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendConfirmProductButton(Integer id, int status)
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
		data1.put("chat_id", id);
		data1.put("text", "please select from the menu options");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		JSONArray innerJsonArray1 = new JSONArray();
		int i = 0;
		innerJsonArray.put("Confirm the product(s)");
		innerJsonArray1.put("Cancel Order");

		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray1);

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendSuccessfullyAppliedCouponNotification(Integer id,
			int status, Map<String, Object> couponDetails)
			throws MalformedURLException, IOException, ProtocolException,
			JsonGenerationException, JsonMappingException, JsonParseException {
		String coupon_id = (String) couponDetails.get("coupon_id");
		Long max_discount = (long) couponDetails.get("max_disocunt");
		Long discount_in_percentage = (long) couponDetails
				.get("discount_in_percentage");
		String coupon_code = (String) couponDetails.get("coupon_code");
		fnFTelegramService.addCouponCode(coupon_id, id);
		URL url = new URL(URL + "sendMessage");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("chat_id", id);
		data1.put("text", "Coupon Applied Successfully.\nPlease place order.");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		innerJsonArray.put("Place Order");
		innerJsonArray2.put("Cancel Order");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray2);
		int i = 0;

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int sendInvalidCouponNotification(Integer id, int status)
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
		data1.put("chat_id", id);
		data1.put("text", "Invalid coupon. Please try again\nOr Place Order");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		JSONArray innerJsonArray3 = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		innerJsonArray.put("Place Order");
		innerJsonArray3.put("Apply Coupon");
		innerJsonArray2.put("Cancel Order");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray3);
		outerJsonArray.put(innerJsonArray2);
		int i = 0;

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}
	
	private int sendlowMinPayableTotalCouponUsedNotification(Integer id, int status)
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
		data1.put("chat_id", id);
		data1.put("text", "Your total amount is below minimum amount to apply this coupon.");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		JSONArray innerJsonArray3 = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		innerJsonArray.put("Place Order");
		innerJsonArray3.put("Apply Coupon");
		innerJsonArray2.put("Cancel Order");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray3);
		outerJsonArray.put(innerJsonArray2);
		int i = 0;

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}
	
	
	private int sendCouponBlockedNotification(Integer id, int status)
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
		data1.put("chat_id", id);
		data1.put("text", "This coupon is expired for non payment of previous order.\nPlease apply another coupon OR Place Order");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		JSONArray innerJsonArray3 = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		innerJsonArray.put("Place Order");
		innerJsonArray3.put("Apply Coupon");
		innerJsonArray2.put("Cancel Order");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray3);
		outerJsonArray.put(innerJsonArray2);
		int i = 0;

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}
	
	private int sendmaxTimeCouponUsedNotification(Integer id, int status)
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
		data1.put("chat_id", id);
		data1.put("text", "You have exceeded maximum number of times this coupon can be used.\nPlease apply another coupon OR Place Order");
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
		JSONArray innerJsonArray3 = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		innerJsonArray.put("Place Order");
		innerJsonArray3.put("Apply Coupon");
		innerJsonArray2.put("Cancel Order");
		outerJsonArray.put(innerJsonArray);
		outerJsonArray.put(innerJsonArray3);
		outerJsonArray.put(innerJsonArray2);
		int i = 0;

		jsonObject.put("keyboard", outerJsonArray);

		jsonObject.put("resize_keyboard", true);
		jsonObject.put("one_time_keyboard", true);
		data1.put("reply_markup", jsonObject.toString());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
				PostUpdateModel.class);
		
		in.close();
		if (response.isOk()) {
			status = 1;
		}
		return status;
	}

	private int newOrderMethod(Integer id, String first_name, String text,
			Integer update_id) throws IOException {

		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				status = fnFTelegramService.addNewOrder(id);
				URL url1 = new URL(URL + "sendMessage");
				HttpURLConnection conn1 = (HttpURLConnection) url1
						.openConnection();
				conn1.setRequestMethod("POST");
				conn1.setRequestProperty("Content-Type", "application/json");
				conn1.setDoOutput(true);
				ObjectMapper mapper1 = new ObjectMapper();
				DataOutputStream wr1 = new DataOutputStream(
						conn1.getOutputStream());
				HashMap<String, Object> data2 = new HashMap<String, Object>();
				data2.put("chat_id", id);
				data2.put("text",
						"Please enter product numbers seperated by comma (Ex: 323232323,31322132)");
				JSONObject jsonObject = new JSONObject();
				JSONArray outerJsonArray = new JSONArray();
				JSONArray innerJsonArray1 = new JSONArray();
				innerJsonArray1.put("Cancel Order");
				outerJsonArray.put(innerJsonArray1);
				jsonObject.put("keyboard", outerJsonArray);
				jsonObject.put("resize_keyboard", true);
				jsonObject.put("one_time_keyboard", true);
				data2.put("reply_markup", jsonObject.toString());
				mapper1.writeValue(wr1, data2);
				int responseCode1 = conn1.getResponseCode();
				InputStream in1 = conn1.getInputStream();
				PostUpdateModel response1 = (PostUpdateModel) mapper1
						.readValue(in1, PostUpdateModel.class);
				
				in1.close();
				if (response1.isOk()) {
					status = 1;
				}
			}
		}
		return status;
	}

	private int orderMethod(Integer id, String first_name, String text,
			Integer update_id) throws IOException {

		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				URL url1 = new URL(URL + "sendMessage");
				HttpURLConnection conn1 = (HttpURLConnection) url1
						.openConnection();
				conn1.setRequestMethod("POST");
				conn1.setRequestProperty("Content-Type", "application/json");
				conn1.setDoOutput(true);
				ObjectMapper mapper1 = new ObjectMapper();
				DataOutputStream wr1 = new DataOutputStream(
						conn1.getOutputStream());
				HashMap<String, Object> data2 = new HashMap<String, Object>();
				data2.put("chat_id", id);
				data2.put("text", "Select Type");
				JSONObject jsonObject = new JSONObject();
				JSONArray outerJsonArray = new JSONArray();
				JSONArray innerJsonArray = new JSONArray();
				JSONArray innerJsonArray1 = new JSONArray();
				int i = 0;
				innerJsonArray.put("New Order");
				innerJsonArray1.put("Last 5 Orders");

				outerJsonArray.put(innerJsonArray);
				outerJsonArray.put(innerJsonArray1);

				jsonObject.put("keyboard", outerJsonArray);

				jsonObject.put("resize_keyboard", true);
				jsonObject.put("one_time_keyboard", true);
				data2.put("reply_markup", jsonObject.toString());
				mapper1.writeValue(wr1, data2);
				int responseCode1 = conn1.getResponseCode();
				InputStream in1 = conn1.getInputStream();
				PostUpdateModel response1 = (PostUpdateModel) mapper1
						.readValue(in1, PostUpdateModel.class);
				
				in1.close();
				if (response1.isOk()) {
					status = 1;
				}
			}
		}
		return status;
	}

	private int sendMoreProducts(Integer id, String first_name, String text,
			Integer update_id) throws IOException {

		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {
				Map<String, Object> productCode = fnFTelegramService
						.getUserQueries(id);
				int count = (Integer) productCode.get("count") + 1;
				int priceRange = (Integer) productCode.get("price_range");
				int userqueriesStatus = fnFTelegramService.addUserQueriesPrice(
						id, priceRange, count);
				int offset = count * 50;
				List<Map<String, Object>> productList = null;
				if (priceRange < 5001) {
					productList = fnFTelegramService.getProductImages(
							(String) productCode.get("product_type_id"),
							priceRange, offset);
				} else {
					productList = fnFTelegramService
							.getProductImagesHIgherPrice(
									(String) productCode.get("product_type_id"),
									priceRange, offset);
				}
				if (productList.size() < 1) {
					URL url = new URL(URL + "sendMessage");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					ObjectMapper mapper = new ObjectMapper();
					DataOutputStream wr = new DataOutputStream(
							conn.getOutputStream());
					HashMap<String, Object> data1 = new HashMap<String, Object>();
					data1.put("chat_id", id);
					data1.put("text", "No more product available");
					List<Map<String, Object>> productType = fnFTelegramService
							.getProductTypes();
					JSONObject jsonObject = sendProductTypeButtons(productType);
					data1.put("reply_markup", jsonObject.toString());

					mapper.writeValue(wr, data1);
					int responseCode = conn.getResponseCode();
					InputStream in = conn.getInputStream();
					PostUpdateModel response = (PostUpdateModel) mapper
							.readValue(in, PostUpdateModel.class);
					
					in.close();
					if (response.isOk()) {
						status = 1;
					}

				} else {
					for (Map<String, Object> map : productList) {
						URL url = new URL(URL + "sendPhoto");
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type",
								"application/json");
						conn.setDoOutput(true);
						ObjectMapper mapper = new ObjectMapper();
						DataOutputStream wr = new DataOutputStream(
								conn.getOutputStream());
						HashMap<String, Object> data1 = new HashMap<String, Object>();
						data1.put("chat_id", id);
						data1.put("photo",
								String.valueOf(map.get("product_image")));
						if (map.get("caption") == null
								|| "".equalsIgnoreCase((String) map
										.get("caption"))) {
							data1.put("caption",
									String.valueOf(map.get("product_id")));
						} else {
							String str=String.valueOf(map.get("caption"))
									+ "\n"
									+ String.valueOf(map
											.get("product_id"));
							data1.put(
									"caption",str);
						}
						mapper.writeValue(wr, data1);
						int responseCode = conn.getResponseCode();
						InputStream in = conn.getInputStream();
						PostUpdateModel response = (PostUpdateModel) mapper
								.readValue(in, PostUpdateModel.class);
						
						in.close();
						if (response.isOk()) {
							status = 1;
						}
						
						URL url2 = new URL(URL + "sendMessage");
						HttpURLConnection conn2 = (HttpURLConnection) url2
								.openConnection();
						conn2.setRequestMethod("POST");
						conn2.setRequestProperty("Content-Type",
								"application/json");
						conn2.setDoOutput(true);
						ObjectMapper mapper2 = new ObjectMapper();
						DataOutputStream wr2 = new DataOutputStream(
								conn2.getOutputStream());
						HashMap<String, Object> data2 = new HashMap<String, Object>();
						data2.put("chat_id", id);
						data2.put("text",
								String.valueOf(map.get("product_id")));
						mapper2.writeValue(wr2, data2);
						int responseCode2 = conn2.getResponseCode();
						InputStream in2 = conn2.getInputStream();
						PostUpdateModel response2 = (PostUpdateModel) mapper2
								.readValue(in2, PostUpdateModel.class);
						
						in2.close();
						if (response2.isOk()) {
							status = 1;
						}
					}
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();
					data2.put("chat_id", id);
					data2.put("text", "Do you want more products");
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					int i = 0;
					innerJsonArray.put("More");
					innerJsonArray1.put("Cancel");

					outerJsonArray.put(innerJsonArray);
					outerJsonArray.put(innerJsonArray1);

					jsonObject.put("keyboard", outerJsonArray);

					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());
					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}

				}

			}
		}
		return status;
	}

	private int sendCollection(Integer id, String first_name, int priceRange,
			Integer update_id) throws IOException {

		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id,
					String.valueOf(priceRange)) > 0) {
				int userqueriesStatus = fnFTelegramService.addUserQueriesPrice(
						id, priceRange, 0);
				Map<String, Object> productCode = fnFTelegramService
						.getProductCode(id);
				List<Map<String, Object>> productList = null;
				int offset = 0 * 50;
				if (priceRange < 5001) {
					productList = fnFTelegramService.getProductImages(
							(String) productCode.get("product_type_id"),
							priceRange, offset);
				} else {
					productList = fnFTelegramService
							.getProductImagesHIgherPrice(
									(String) productCode.get("product_type_id"),
									priceRange, offset);
				}
				if (productList.size() < 1) {
					URL url = new URL(URL + "sendMessage");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					ObjectMapper mapper = new ObjectMapper();
					DataOutputStream wr = new DataOutputStream(
							conn.getOutputStream());
					HashMap<String, Object> data1 = new HashMap<String, Object>();
					data1.put("chat_id", id);
					data1.put("text", "No more product available");
					List<Map<String, Object>> productType = fnFTelegramService
							.getProductTypes();
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
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
					JSONArray innerJsonArray2 = new JSONArray();

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
					InputStream in = conn.getInputStream();
					PostUpdateModel response = (PostUpdateModel) mapper
							.readValue(in, PostUpdateModel.class);
					
					in.close();
					if (response.isOk()) {
						status = 1;
					}
				} else {
					for (Map<String, Object> map : productList) {
						URL url = new URL(URL + "sendPhoto");
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type",
								"application/json");
						conn.setDoOutput(true);
						ObjectMapper mapper = new ObjectMapper();
						DataOutputStream wr = new DataOutputStream(
								conn.getOutputStream());
						HashMap<String, Object> data1 = new HashMap<String, Object>();
						data1.put("chat_id", id);
						data1.put("photo",
								String.valueOf(map.get("product_image")));
						if (map.get("caption") == null
								|| "".equalsIgnoreCase((String) map
										.get("caption"))) {
							data1.put("caption",
									String.valueOf(map.get("product_id")));
						} else {
							String str=String.valueOf(map.get("caption"))
									+ "\n"
									+ String.valueOf(map
											.get("product_id"));
							data1.put(
									"caption",str);
						}
						mapper.writeValue(wr, data1);
						int responseCode = conn.getResponseCode();
						InputStream in = conn.getInputStream();
						PostUpdateModel response = (PostUpdateModel) mapper
								.readValue(in, PostUpdateModel.class);
						
						in.close();
						if (response.isOk()) {
							status = 1;
						}
						
						
						URL url2 = new URL(URL + "sendMessage");
						HttpURLConnection conn2 = (HttpURLConnection) url2
								.openConnection();
						conn2.setRequestMethod("POST");
						conn2.setRequestProperty("Content-Type",
								"application/json");
						conn2.setDoOutput(true);
						ObjectMapper mapper2 = new ObjectMapper();
						DataOutputStream wr2 = new DataOutputStream(
								conn2.getOutputStream());
						HashMap<String, Object> data2 = new HashMap<String, Object>();
						data2.put("chat_id", id);
						data2.put("text",
								String.valueOf(map.get("product_id")));
						mapper2.writeValue(wr2, data2);
						int responseCode2 = conn2.getResponseCode();
						InputStream in2 = conn2.getInputStream();
						PostUpdateModel response2 = (PostUpdateModel) mapper2
								.readValue(in2, PostUpdateModel.class);
						
						in2.close();
						if (response2.isOk()) {
							status = 1;
						}
					}
					URL url1 = new URL(URL + "sendMessage");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					conn1.setRequestMethod("POST");
					conn1.setRequestProperty("Content-Type", "application/json");
					conn1.setDoOutput(true);
					ObjectMapper mapper1 = new ObjectMapper();
					DataOutputStream wr1 = new DataOutputStream(
							conn1.getOutputStream());
					HashMap<String, Object> data2 = new HashMap<String, Object>();
					data2.put("chat_id", id);
					data2.put("text", "Do you want more products");
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					int i = 0;
					innerJsonArray.put("More");
					innerJsonArray1.put("Cancel");

					outerJsonArray.put(innerJsonArray);
					outerJsonArray.put(innerJsonArray1);

					jsonObject.put("keyboard", outerJsonArray);

					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data2.put("reply_markup", jsonObject.toString());
					mapper1.writeValue(wr1, data2);
					int responseCode1 = conn1.getResponseCode();
					InputStream in1 = conn1.getInputStream();
					PostUpdateModel response1 = (PostUpdateModel) mapper1
							.readValue(in1, PostUpdateModel.class);
					
					in1.close();
					if (response1.isOk()) {
						status = 1;
					}

				}

			}
		}

		return status;
	}

	private int responseMethod(Integer id, String first_name, String text,
			int update_id) throws IOException {

		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				int counter = 0;
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();

				if (userDetails == null) {
					System.out.println("==========="+"register");
					data1.put("chat_id", id);
					data1.put("text", "Hi " + first_name
							+ ", please enter your full name");
					
					counter++;
				} else if (userDetails.get("user_full_name") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("user_full_name"))) {
					System.out.println("==========="+"register1");
					status = fnFTelegramService.addFullName(id, text);
					data1.put("chat_id", id);
					data1.put("text", "Welcome " + text
							+ ", Please enter your mobile number");
					
					counter++;
				} else if (userDetails.get("phone") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("phone"))) {

					if (text.length() < 10) {
						data1.put("chat_id", id);
						data1.put("text",
								"Invalid mobile number. Please enter valid mobile number");
						
						counter++;
					} else {
						status = fnFTelegramService.addPhone(id, text);
						data1.put("chat_id", id);
						data1.put("text", "Please enter your city name");
						
						counter++;
					}
				} else if (userDetails.get("user_city") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("user_city"))) {
					status = fnFTelegramService.addCity(id, text);
					data1.put("chat_id", id);
					data1.put("text", "Please enter your email id");
					
					counter++;
				} else if (userDetails.get("email") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("email"))) {
					boolean emailValid = emailValidate(text);
					if (!emailValid) {
						data1.put("chat_id", id);
						data1.put("text",
								"Invalid email format. Please enter valid email id");
						
						counter++;
					} else {
						status = fnFTelegramService.addEmail(id, text);
						data1.put("chat_id", id);
						data1.put(
								"text",
								"Please enter referral code if any\nOr\nSelect Complete Registration button to complete registration");
						JSONObject jsonObject = new JSONObject();
						JSONArray innerJsonArray = new JSONArray();
						JSONArray outerJsonArray = new JSONArray();
						innerJsonArray.put("Complete Registration");

						outerJsonArray.put(innerJsonArray);
						jsonObject.put("keyboard", outerJsonArray);

						jsonObject.put("resize_keyboard", true);
						jsonObject.put("one_time_keyboard", true);
						data1.put("reply_markup", jsonObject.toString());
						counter++;
					}
				} else if (userDetails.get("referrer_coupon") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("referrer_coupon"))) {
					if (text.equalsIgnoreCase("Complete Registration")) {
						status = fnFTelegramService.addReferralCodeAndWallet(
								id, "-1", 0);
						data1.put("chat_id", id);
						data1.put(
								"text",
								"Registration Completed\n\nPlease select the category\nOR\nSelect Orders for placing order\n\nFor customer help\nPlease click here: @IntricateHelp");
						List<Map<String, Object>> productType = fnFTelegramService
								.getProductTypes();
						JSONObject jsonObject = sendProductTypeButtons(productType);
						data1.put("reply_markup", jsonObject.toString());

						counter++;
						if (true) {
							sendRegistrationNotification(id, first_name, text,
									userDetails, SACHIN);
						}
						if (true) {
							sendRegistrationNotification(id, first_name, text,
									userDetails, INTRICATE);

						}
						if (true) {
							sendRegistrationNotification(id, first_name, text,
									userDetails, ASHISH);

						}
						sendTutorialVideo(id);
						counter++;
					} else {
						boolean couponValid = fnFTelegramService
								.checkReferralCode(text);
						if (couponValid) {
							status = fnFTelegramService
									.addReferralCodeAndWallet(id, text,
											ProjectConstant.REFERRALAMOUNT);
							String user_used = text;
							Integer order_id = null;
							int amount = ProjectConstant.REFERRALAMOUNT;
							String history_note = "Registration using Referral Code: "
									+ user_used;
							fnFTelegramService.addReferralHistory(id,
									user_used, order_id, amount, history_note);
							data1.put("chat_id", id);
							data1.put(
									"text",
									"Registration Completed\n\nCongratulations Rs "
											+ ProjectConstant.REFERRALAMOUNT
											+ " has been credited to your Wallet\n\n"
											+ "Please select the category\nOR\nSelect Orders for placing order\n\nFor customer help\nPlease click here: @IntricateHelp");
							List<Map<String, Object>> productType = fnFTelegramService
									.getProductTypes();
							JSONObject jsonObject = sendProductTypeButtons(productType);
							data1.put("reply_markup", jsonObject.toString());

							counter++;
							if (true) {
								sendRegistrationNotification(id, first_name,
										text, userDetails, SACHIN);
							}
							if (true) {
								sendRegistrationNotification(id, first_name,
										text, userDetails, INTRICATE);
							}
							if (true) {
								sendRegistrationNotification(id, first_name,
										text, userDetails, ASHISH);

							}
							sendTutorialVideo(id);
							counter++;
						} else {
							data1.put("chat_id", id);
							data1.put(
									"text",
									"Invalid referral code. Please enter valid referral code\nOr\nSelect Complete Registration button to complete registration");
							JSONObject jsonObject = new JSONObject();
							JSONArray innerJsonArray = new JSONArray();
							JSONArray outerJsonArray = new JSONArray();
							innerJsonArray.put("Complete Registration");

							outerJsonArray.put(innerJsonArray);
							jsonObject.put("keyboard", outerJsonArray);

							jsonObject.put("resize_keyboard", true);
							jsonObject.put("one_time_keyboard", true);
							data1.put("reply_markup", jsonObject.toString());
							counter++;
						}
					}

				}
				/*s*/
				 
				if (counter == 0) {
					List<Map<String, Object>> productType = fnFTelegramService
							.getProductTypes();
					List<String> productList = new ArrayList<String>();
					int userqueriesStatus = 0;
					for (Map<String, Object> map : productType) {
						productList.add((String) map.get("product_type_name"));
					}
					if (productList.contains(text)) {
						userqueriesStatus = fnFTelegramService.addUserQueries(
								id, text);
					}
					JSONObject jsonObject = new JSONObject();
					JSONArray outerJsonArray = new JSONArray();
					JSONArray innerJsonArray = new JSONArray();
					JSONArray innerJsonArray1 = new JSONArray();
					JSONArray innerJsonArray2 = new JSONArray();
					if (userqueriesStatus > 0) {

						data1.put("chat_id", id);
						data1.put("text", "Please select price range");
						innerJsonArray.put("Upto 1000");
						innerJsonArray.put("Upto 2000");
						outerJsonArray.put(innerJsonArray);
						innerJsonArray1.put("Upto 3000");
						innerJsonArray1.put("Upto 4000");
						outerJsonArray.put(innerJsonArray1);
						innerJsonArray2.put("Upto 5000");
						innerJsonArray2.put("Above 5000");
						outerJsonArray.put(innerJsonArray2);
					} else {
						data1.put("chat_id", id);
						data1.put("text",
								"Please select the category\nOR\nSelect Orders for placing order");
						int i = 0;
						for (Map<String, Object> map : productType) {
							if (i < 2) {
								innerJsonArray
										.put(map.get("product_type_name"));
								i++;
							} else {
								innerJsonArray1.put(map
										.get("product_type_name"));
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

					}

					jsonObject.put("keyboard", outerJsonArray);

					jsonObject.put("resize_keyboard", true);
					jsonObject.put("one_time_keyboard", true);
					data1.put("reply_markup", jsonObject.toString());
				}

				mapper.writeValue(wr, data1);

				int responseCode = conn.getResponseCode();
				if (responseCode == 200){
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				
				in.close();
				
				status = 1;
				}else {
					// remove update_message
				}
			}
		}
		return status;

	}

	private JSONObject sendProductTypeButtons(
			List<Map<String, Object>> productType) {
		JSONObject jsonObject = new JSONObject();
		JSONArray outerJsonArray = new JSONArray();
		JSONArray innerJsonArray = new JSONArray();
		JSONArray innerJsonArray1 = new JSONArray();
		JSONArray innerJsonArray2 = new JSONArray();
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
		return jsonObject;
	}

	private void sendTutorialVideo(Integer id) throws MalformedURLException,
			IOException, ProtocolException, JsonGenerationException,
			JsonMappingException, JsonParseException {
		URL url1 = new URL(URL + "sendVideo");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();

		data2.put("chat_id", id);
		data2.put("video", videoUrl);
		data2.put("caption", "How to use - fnfsareesbot");
		 //"91734453" 
		mapper1.writeValue(wr1, data2);
		int responseCode1 = conn1.getResponseCode();
		InputStream in1 = null;
		PostUpdateModel response1 = null;
		if (responseCode1 == 200) {
			in1 = conn1.getInputStream();
			response1 = (PostUpdateModel) mapper1.readValue(in1,
					PostUpdateModel.class);
			in1.close();
		}
	}

	private void sendRegistrationNotification(Integer id, String first_name,
			String text, Map<String, Object> userDetails, String userIdNoti)
			throws MalformedURLException, IOException, ProtocolException,
			JsonGenerationException, JsonMappingException, JsonParseException {
		URL url1 = new URL(URL + "sendMessage");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("POST");
		conn1.setRequestProperty("Content-Type", "application/json");
		conn1.setDoOutput(true);
		ObjectMapper mapper1 = new ObjectMapper();
		DataOutputStream wr1 = new DataOutputStream(conn1.getOutputStream());
		HashMap<String, Object> data2 = new HashMap<String, Object>();

		data2.put("chat_id", userIdNoti);
		data2.put("text",
				"--------------------\nNew User\n--------------------\n"
						+ "User Id : " + id + "\nUser Name : " + first_name
						+ "\nFull Name : " + userDetails.get("user_full_name")
						+ "\nCity : " + userDetails.get("user_city")
						+ "\nMobile : " + userDetails.get("phone")
						+ "\nEmail : " + text + ""
						+ "\n----------------------\n");
		 //"91734453" 
		mapper1.writeValue(wr1, data2);
		int responseCode1 = conn1.getResponseCode();
		InputStream in1 = null;
		PostUpdateModel response1 = null;
		if (responseCode1 == 200) {
			in1 = conn1.getInputStream();
			response1 = (PostUpdateModel) mapper1.readValue(in1,
					PostUpdateModel.class);
			in1.close();
		}
	}

	private int startResponseMethod(Integer id, String first_name, String text,
			int update_id) throws IOException {

		int status = 0;
		Map<String, Object> userDetails = fnFTelegramService
				.fetchUserDetails(id);
		if (userDetails == null) {
			status = fnFTelegramService.addUserDetails(id, first_name);
		}
		String updateStatus = fnFTelegramService.checkUpdate(update_id);

		if (updateStatus == null || "".equalsIgnoreCase(updateStatus)) {
			if (fnFTelegramService.addUpdate(id, update_id, text) > 0) {

				int counter = 0;
				URL url = new URL(URL + "sendMessage");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				ObjectMapper mapper = new ObjectMapper();
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				HashMap<String, Object> data1 = new HashMap<String, Object>();

				if (userDetails == null) {

					data1.put("chat_id", id);
					data1.put("text", "Hi " + first_name
							+ ", please enter your full name");
					
					counter++;
				} else if (userDetails.get("user_full_name") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("user_full_name"))) {
					data1.put("chat_id", id);
					data1.put("text", "Hi " + first_name
							+ ", please enter your full name");
					
					counter++;
				} else if (userDetails.get("phone") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("phone"))) {
					data1.put("chat_id", id);
					data1.put("text", "Welcome " + text
							+ ", Please enter your mobile number");
					
					counter++;
				} else if (userDetails.get("user_city") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("user_city"))) {
					data1.put("chat_id", id);
					data1.put("text", "Please enter your city name");
					
					counter++;
				} else if (userDetails.get("email") == null
						|| "".equalsIgnoreCase((String) userDetails
								.get("email"))) {
					boolean emailValid = emailValidate(text);
					if (!emailValid) {
						data1.put("chat_id", id);
						data1.put("text", "Please enter valid email id");
						
						counter++;
					} else {
						data1.put("chat_id", id);
						data1.put("text", "Please enter valid email id");
						
						counter++;
					}
				} else {
					
					 
					data1.put("chat_id", id);
					data1.put(
							"text",
							"\nPlease select the category\nOR\nSelect Orders for placing order\n\nFor customer help\nPlease click here: @IntricateHelp");
					List<Map<String, Object>> productType = fnFTelegramService
							.getProductTypes();
					JSONObject jsonObject = sendProductTypeButtons(productType);
					data1.put("reply_markup", jsonObject.toString());
				}

				mapper.writeValue(wr, data1);

				int responseCode = conn.getResponseCode();
				if (responseCode == 200){
				InputStream in = conn.getInputStream();
				PostUpdateModel response = (PostUpdateModel) mapper.readValue(
						in, PostUpdateModel.class);
				
				in.close();
				
					status = 1;
				}
				else {
					// remove update_message
					status=1;
				}
			}
		}
		return status;

	}

	private final String emailRegExp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

	public boolean emailValidate(String email) {

		if (email != null && !email.toString().equals("")) {

			Pattern pattern = Pattern.compile(emailRegExp,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email.trim());
			return matcher.matches();
		}
		return false;

	}

}
