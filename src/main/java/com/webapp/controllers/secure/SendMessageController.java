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

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.PostUpdateModel;
import com.webapp.models.SendMessageDto;
import com.webapp.services.FnFTelegramService;

@Controller
@RequestMapping("/send-message")
public class SendMessageController extends BusinessController {

	@Autowired
	private FnFTelegramService fnFTelegramService;

	private static final String URL = ProjectConstant.FNFSAREESBOT;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		SendMessageDto sendMessageDto = new SendMessageDto();
		model.addAttribute(sendMessageDto);
		return "send-message";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postSendMessage(Model model, SendMessageDto sendMessageDto,
			BindingResult result, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		model.addAttribute(sendMessageDto);
		List<Map<String, Object>> productType = fnFTelegramService
				.getProductTypes();
		if (sendMessageDto.getUserId().equalsIgnoreCase("all")) {
			List<String> listUserId = fnFTelegramService.getAllUserId();
			for (String string : listUserId) {
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
				JSONObject jsonObject = new JSONObject();
				JSONArray outerJsonArray = new JSONArray();
				JSONArray innerJsonArray = new JSONArray();
				JSONArray innerJsonArray1 = new JSONArray();
				JSONArray innerJsonArray2 = new JSONArray();

				data1.put("chat_id", string);
				data1.put("text", sendMessageDto.getMessage());
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
				PostUpdateModel response = null;
				if(responseCode==200){
					in = conn.getInputStream();
					response= (PostUpdateModel) mapper.readValue(
							in, PostUpdateModel.class);
					in.close();
				}else{
					model.addAttribute("msg", "Failed to send message");
				}
				
				if (response!=null&&response.isOk()) {
					model.addAttribute("msg", "Message Send Successfully");
				} else {
					model.addAttribute("msg", "Failed to send message");
				}

			}
		} else {

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

			data1.put("chat_id", sendMessageDto.getUserId());
			data1.put("text", sendMessageDto.getMessage());
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
			}else{
				model.addAttribute("msg", "Failed to send message");
				return "send-message";
			}
			
			/*try {
				
			} catch (Exception e) {
				
			}*/
			PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
					PostUpdateModel.class);
			in.close();
			if (response.isOk()) {
				model.addAttribute("msg", "Message Send Successfully");
			} else {
				model.addAttribute("msg", "Failed to send message");
			}
		}

		return "send-message";
	}

}
