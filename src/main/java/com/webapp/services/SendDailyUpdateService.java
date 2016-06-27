package com.webapp.services;

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

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.constant.ProjectConstant;
import com.webapp.daos.SendDailyUpdateDao;
import com.webapp.models.DailyUpdateDto;
import com.webapp.models.PostUpdateModel;

@Service("sendDailyUpdateService")
public class SendDailyUpdateService {

	protected static Logger logger = Logger.getLogger(SendDailyUpdateService.class);

	@Autowired
	private SendDailyUpdateDao sendDailyUpdateDao;
	
	@Autowired
	private FnFTelegramService fnFTelegramService;
	
	private static final String URL = ProjectConstant.FNFSAREESBOT;
	private static final String INTRICATE = "113680761";
	private static final String ASHISH = "103686419";
	private static final String SACHIN = "62421877";

	public int postSendMessage(DailyUpdateDto dailyUpdateDto) throws IOException {
		List<String> productTypesForCategory = sendDailyUpdateDao.getProductNames(dailyUpdateDto.getCatalogueId());
		if(productTypesForCategory==null){
			return 0;
		}
		List<Map<String, Object>> productType1 = fnFTelegramService
				.getProductTypes();
		int status=0;
		String message="Catalogue update: "+dailyUpdateDto.getCatalogueId()+" started";
		int ii=0;
		if(true){
			sendAdminsUpdate(productType1, message, INTRICATE);
		}
		if(true){
			sendAdminsUpdate(productType1, message, ASHISH);
		}
		if(true){
			sendAdminsUpdate(productType1, message, SACHIN);
		}
		message="";
		for (String productType : productTypesForCategory) {
			List<String> userIdList = sendDailyUpdateDao.getUserIdListForCategory(productType);
			List<Map<String,Object>> productImagesList = sendDailyUpdateDao.getProductImagesForCatalogue(dailyUpdateDto.getCatalogueId(),productType);
			message += "Catalogue update: "+dailyUpdateDto.getCatalogueId()+" completed\n"+productType+":: users:"+userIdList.size()+" products:"+productImagesList.size();
			for (String userId : userIdList) {
				for (Map<String, Object> map : productImagesList) {
					URL url = new URL(URL + "sendPhoto");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					ObjectMapper mapper = new ObjectMapper();
					DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
					HashMap<String, Object> data1 = new HashMap<String, Object>();
					data1.put("chat_id", userId);
					data1.put("photo", String.valueOf(map.get("product_image")));
					data1.put("caption", String.valueOf(map.get("product_id")));
					mapper.writeValue(wr, data1);
					int responseCode = conn.getResponseCode();
					if(responseCode==200){
						InputStream in = conn.getInputStream();
						PostUpdateModel response = (PostUpdateModel) mapper.readValue(in, PostUpdateModel.class);
						in.close();
						status=1;
						ii++;
					}
				}
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
				data1.put("text", "Don't want to receive images?\nTo stop click here - /stop_images");
				int i = 0;
				for (Map<String, Object> map : productType1) {
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
					PostUpdateModel response = (PostUpdateModel)mapper.readValue(in,
							PostUpdateModel.class);
					in.close();
				}
			}
			message+=" Successfully number of photos sent (Users*total products): "+ii+" \n";
		}
		if(true){
			sendAdminsUpdate(productType1, message, INTRICATE);
		}
		if(true){
			sendAdminsUpdate(productType1, message, ASHISH);
		}
		if(true){
			sendAdminsUpdate(productType1, message, SACHIN);
		}
		return status;
	}

	private void sendAdminsUpdate(List<Map<String, Object>> productType1,
			String message, String chatId) throws MalformedURLException, IOException,
			ProtocolException, JsonGenerationException, JsonMappingException,
			JsonParseException {
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
		data1.put("chat_id", chatId);
		data1.put("text", message);
		int i = 0;
		
		for (Map<String, Object> map : productType1) {
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
		mapper.readValue(in,
				PostUpdateModel.class);
		in.close();
	}
}
