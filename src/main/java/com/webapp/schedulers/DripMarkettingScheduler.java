package com.webapp.schedulers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.fnf.utils.DateUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.dto.DripMarkettingDto;
import com.webapp.dto.UsersJsonDto;
import com.webapp.models.PostUpdateModel;
import com.webapp.services.DripMarkettingService;
import com.webapp.services.FnFTelegramService;
import com.webapp.services.UsersService;

@Controller
public class DripMarkettingScheduler {

	@Autowired
	UsersService usersService;
	
	@Autowired
	DripMarkettingService dripMarkettingService;
	
	@Autowired
	FnFTelegramService fnFTelegramService;

	private static final String URL = ProjectConstant.FNFSAREESBOT;
	
	@Scheduled(cron = "0 0 12 * * *") 
	public void schedulerMethod() throws ServletException, IOException {

		doFollowUpOfOrders();

	}

	private void doFollowUpOfOrders() throws IOException {


		List<Map<String, Object>> productType = fnFTelegramService.getProductTypes();
		List<DripMarkettingDto> dripMarkettingDetails = dripMarkettingService.getDripMarketDetails();
		Map<Long,String> dripMap= new HashMap<Long, String>();
		List<UsersJsonDto> accts = usersService.fetchAllUsers();
		
		for (DripMarkettingDto dripMarketing : dripMarkettingDetails) {
			dripMap.put(dripMarketing.getInDays(), dripMarketing.getMarketingText());
		}
		for (UsersJsonDto usersJsonDto : accts) {
			long milisecDiff = DateUtils.nowAsGmtMillisec()-usersJsonDto.getCreated_at();
			long daysDiff = (int) (milisecDiff/ProjectConstant.ONE_DAY_IN_MILISEC);
			if(dripMap.containsKey(daysDiff)){
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
				data1.put("chat_id", usersJsonDto.getUser_id());
//				data1.put("chat_id","152708937");
				data1.put("text", dripMap.get(daysDiff));
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
				mapper.readValue(in,
						PostUpdateModel.class);
				in.close();
			}
		}
		
		
		
	}
}