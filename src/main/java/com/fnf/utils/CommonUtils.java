package com.fnf.utils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.URIUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.utils.constant.ProjectConstant;
import com.webapp.models.PostUpdateModel;
import com.webapp.services.FnFTelegramService;

public class CommonUtils {

	private static final String URL = ProjectConstant.FNFSAREESBOT;

	private static final String SMSURL = "http://sms.adskaro.com/api/v3/index.php";
	@Autowired
	FnFTelegramService fnFTelegramService;

	public void sendCustomerNote(String userId, String message) {

		try {
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
			data1.put("chat_id", userId);
			data1.put("text", message);
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
			
			/*try {
				
			} catch (Exception e) {
				
			}*/
			PostUpdateModel response = (PostUpdateModel) mapper.readValue(in,
					PostUpdateModel.class);
			in.close();
			
		} catch (Exception e) {
		}
	}

	public void sendSms(String userPhone, String message) {
		try {
		String urlString =SMSURL+"?method=sms&api_key=9504q1pac6npxyj75b&to="+userPhone+"&sender=FNFSAR&message="+message+"&unicode=0"; 
		urlString=URIUtil.encodeQuery(urlString);
		URL url = new URL(urlString);
//		org.apache.commons.httpclient.util.URIUtil
	    
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		int responseCode = conn.getResponseCode();
		InputStream in = null;
		if (responseCode == 200) {
			in = conn.getInputStream();
		}
		in.close();
		} catch (Exception e) {
		}
	}
}
