package com.webapp.controllers.secure;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.fnf.utils.DateUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.OrderModel;
import com.webapp.models.PostUpdateModel;
import com.webapp.services.FnFTelegramService;
import com.webapp.services.OrderService;

@Controller
@RequestMapping("/order/add")
public class NewOrderController extends BusinessController {

	@Autowired
	private FnFTelegramService fnFTelegramService;

	private static final String URL = ProjectConstant.FNFSAREESBOT;
	private static final String INTRICATE = "113680761";
	private static final String ASHISH = "103686419";
	private static final String SACHIN = "62421877";
	
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	public String editOrder(Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		OrderModel orderModel =  new OrderModel();
		model.addAttribute("orderModel", orderModel);
		return "new-order";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postEditOrder(Model model, @Validated OrderModel orderModel,
			BindingResult result, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		Map<String, Object> userDetails = fnFTelegramService.fetchUserDetails(Integer.parseInt(orderModel.getUser_id()));
		if (userDetails==null) {
			model.addAttribute("orderModel", orderModel);
			model.addAttribute("msg", "Invalid User Id");
			return "new-order";
		}
		int order_id=0;
		String[] products = orderModel.getProduct_no().split(",");
		int count=0;
		for (String product : products) {
			boolean valid = fnFTelegramService.checkProduct(product
					.trim());
			if (!valid) {
				model.addAttribute("orderModel", orderModel);
				model.addAttribute("msg", "Invalid Product No.: "+product);
				return "new-order";
			}
			Map<String, Object> productDetails = fnFTelegramService.getProductDetails(product.trim());
			if (productDetails == null) {
				model.addAttribute("orderModel", orderModel);
				model.addAttribute("msg", "Product No.: "+product+" is out of stock.");
				return "new-order";
			}else{
				if(count++==0){
					orderModel.setCreated_at(DateUtils.nowAsGmtMillisec());
					orderService.addOrder(orderModel);
					order_id=orderService.getOrderId(orderModel);
					orderModel.setOrder_id(order_id);
					//order_id=Integer.parseInt(orderId);
				}
				fnFTelegramService.addOrderProducts(
						order_id,
						(String) productDetails
								.get("product_id"),
						(int) productDetails
								.get("product_price"),
						(int) productDetails.get("weight"));
			}
		}
		Map<String, Object> output = orderService.getOrderPrice(order_id);
		int total_price = (int) (long) output.get("total_price");
		int total_weight = (int) (long) output.get("total_weight");
		int total_discounted_price = (int) (long) output
				.get("total_discounted_price");
		int total_courier_charge = 0;
		Integer coupon_discount = orderModel.getCoupon_discount();
		Integer referral_discount = 0;
		
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
				fnFTelegramService.addReferralHistory(Integer.parseInt(orderModel.getUser_id()), user_used,
						order_id, amount, history_note);

				fnFTelegramService.updateReferralBalance(Integer.parseInt(orderModel.getUser_id()), 0);
			} else {

				String user_used = null;
				int amount = referral_discount;
				String history_note = "Used in Order No. : " + order_id;
				fnFTelegramService.addReferralHistory(Integer.parseInt(orderModel.getUser_id()), user_used,
						order_id, amount, history_note);

				fnFTelegramService.updateReferralBalance(
						Integer.parseInt(orderModel.getUser_id()),
						(int) (long) userDetails
								.get("referral_balance")
								- referral_discount);
			}
		}
		
		String payment_method = orderModel.getPayment_method();
		String courier_method = orderModel.getCourier_method();
		total_courier_charge = 0;
		if (courier_method.equalsIgnoreCase("fast")
				|| payment_method
						.equalsIgnoreCase("90% cash on delivery")) {
			courier_method = "fast";
			int i = total_weight / 500;
			if (total_weight % 500 == 0) {
				for (int counter = 1; counter <= i; counter++) {
					if (counter % 2 != 0) {
						total_courier_charge += 80;
					} else {
						total_courier_charge += 60;
					}
				}
			} else {
				i = i + 1;
				for (int counter = 1; counter <= i; counter++) {
					if (counter % 2 != 0) {
						total_courier_charge += 80;
					} else {
						total_courier_charge += 60;
					}
				}
			}
		} else if (courier_method.equalsIgnoreCase("local")) {
			int i = total_weight / 500;
			if (total_weight % 500 == 0) {
				for (int counter = 1; counter <= i; counter++) {
					if (counter % 2 != 0) {
						total_courier_charge += 70;
					} else {
						total_courier_charge += 30;
					}
				}
			} else {
				i = i + 1;
				for (int counter = 1; counter <= i; counter++) {
					if (counter % 2 != 0) {
						total_courier_charge += 70;
					} else {
						total_courier_charge += 30;
					}
				}
			}
		}
		int internet_handling_charge = 0;
		int online_payment = 0;
		int cod_payment = 0;
		int cod_charge = 0;
		int total = total_discounted_price + total_courier_charge
				- coupon_discount - referral_discount;
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
			
			
			/*cod_payment = (int) (total * 0.9);
			int amount50roundoff = cod_payment % 50;
			cod_payment = cod_payment - amount50roundoff;*/
			online_payment = total - cod_payment;
		}
		//shyam
		int status = fnFTelegramService.completeOrder(order_id,
				total_price, total_discounted_price,
				total_courier_charge, total, referral_discount,
				coupon_discount, internet_handling_charge,
				online_payment, cod_payment, cod_charge,courier_method,payment_method);
		
		
		List<Map<String, Object>> latestproducts = fnFTelegramService
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
		data2.put("chat_id", orderModel.getUser_id());
		String outputProducts = "";
		for (Map<String, Object> map : latestproducts) {
			outputProducts += "\nSub Order Id : "
					+ map.get("order_product_id") + "\nProduct No. : "
					+ map.get("product_id") + "\nMRP : "
					+ map.get("price") + "\nFranchisee Price : "
					+ map.get("discounted_price")
					+ "\n------------------------";
		}
		String orderDetailsString = "New Order Created\n------------------------\nOrder No. : "
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
					+ "\nOnline Payment (10%) : " + online_payment
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
		System.out.println(response1);
		in1.close();
		if (response1.isOk()) {
			status = 1;
		}
		
		if (true) {
			sendOrderNotification(Integer.parseInt(orderModel.getUser_id()), (String)userDetails.get("user_name"),
					total_discounted_price, total_courier_charge,
					total, order_id, outputProducts, ASHISH,
					referral_discount, coupon_discount,
					internet_handling_charge, payment_method,
					online_payment, cod_charge, cod_payment,
					courier_method);
		}
		if (true) {
			sendOrderNotification(Integer.parseInt(orderModel.getUser_id()), (String)userDetails.get("user_name"),
					total_discounted_price, total_courier_charge,
					total, order_id, outputProducts, SACHIN,
					referral_discount, coupon_discount,
					internet_handling_charge, payment_method,
					online_payment, cod_charge, cod_payment,
					courier_method);

		}
		if (true) {
			sendOrderNotification(Integer.parseInt(orderModel.getUser_id()), (String)userDetails.get("user_name"),
					total_discounted_price, total_courier_charge,
					total, order_id, outputProducts, INTRICATE,
					referral_discount, coupon_discount,
					internet_handling_charge, payment_method,
					online_payment, cod_charge, cod_payment,
					courier_method);

		}
		
		
		String customer_note = orderModel.getCustomer_note();
		if (customer_note != null && !"".equalsIgnoreCase(customer_note)) {
			URL url = new URL(URL + "sendMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			ObjectMapper mapper = new ObjectMapper();
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			HashMap<String, Object> data1 = new HashMap<String, Object>();
			data1.put("chat_id", orderModel.getUser_id());
			data1.put("text", "---------------\nStatus for Order Id : "
					+ orderModel.getOrder_id() + "\n" + customer_note
					+ "\n--------------");
			data1.put("reply_markup", jsonObject.toString());
			mapper.writeValue(wr, data1);
			int responseCode = conn.getResponseCode();
			InputStream in = null;
			PostUpdateModel response = null;
			if (responseCode == 200) {
				in = conn.getInputStream();
				response = (PostUpdateModel) mapper.readValue(in,
						PostUpdateModel.class);
				in.close();
			}
		}

		orderModel = orderService.fetchOrderDetails(orderModel.getOrder_id());
		if (orderModel.getCreated_at() == 0) {

		} else {
			orderModel.setCreated_date(DateUtils.dbTimeStampToSesionDate(
					orderModel.getCreated_at(), "IST", "dd/MM/yyyy hh:mm a"));
		}
		model.addAttribute("orderModel", orderModel);

		if (status > 0) {
			model.addAttribute("msg", "New Order Created Successfully");
		} else {
			model.addAttribute("msg", "Failed to add new order");
		}
		return "order-details";
	}

	private void sendOrderNotification(Integer id, String first_name,
			int total_discounted_price, int total_courier_charge, int total,
			int order_id, String outputProducts, String userIdNoti,
			int referral_discount, int coupon_discount, int internet_handling_charge, String payment_method, int online_payment, int cod_charge, int cod_payment, String courier_method)
			throws MalformedURLException, IOException, ProtocolException,
			JsonGenerationException, JsonMappingException, JsonParseException {
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
					+ "\nOnline Payment (10%) : " + online_payment
					+ "\nCash on Delivery Payment : " + cod_payment;

		} else {
			orderDetailsString += "\n------------------------"
					+ "\nInternet Handling Charge : "
					+ internet_handling_charge;
		}
		data3.put("text", orderDetailsString + "\n------------------------"
				+ "\nTotal Payable : " + total + "\n------------------------");
		/* "91734453" */
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
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/new-order.js","js/suborder.js" };
	}
}
