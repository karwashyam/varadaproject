package com.webapp.services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.CommonUtils;
import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.MailUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.LeadFromDao;
import com.webapp.dto.LeadJsonDto;
import com.webapp.models.LeadRegisterDto;
import com.webapp.models.PostUpdateModel;
import com.webapp.models1.LeadModel;

@Service("leadFormService")
public class LeadFormService {

	protected static Logger logger = Logger.getLogger(LeadFormService.class);
	
	private static final String URL = ProjectConstant.FNFSAREESBOT;

	private static final String INTRICATE = "113680761";
	private static final String ASHISH = "103686419";
	private static final String SACHIN = "62421877";
	
	@Autowired
	private LeadFromDao leadFromDao;
	
	@Autowired
	private FnFTelegramService fnFTelegramService;
	
	
	@Autowired
	private MailUtils mailUtils;

	@Transactional
	public void addLead(LeadRegisterDto leadRegisterDto) throws IOException {
		LeadModel leadModel = new LeadModel();	
		leadModel.setLeadId(UUIDGenerator.generateUUID());
		leadModel.setEmail(leadRegisterDto.getEmail());
		leadModel.setPhone(leadRegisterDto.getPhone());
		leadModel.setLeadFrom(leadRegisterDto.getLeadFrom());
		leadModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
		leadFromDao.addLead(leadModel);
		List<Map<String, Object>> productType = fnFTelegramService.getProductTypes();
		new CommonUtils().sendSms(leadRegisterDto.getPhone(), "Welcome to Intricate Fashions\n\nTo get started with ur prd discovery download: telegram.org/dl\n\nThen click: telegram.me/fnfsareesbot");
//		new CommonUtils().sendSms(leadRegisterDto.getPhone(), "Welcome to Intricate Fashions\nFor Retail Cust:www.fnfsarees.com\n\nFor Traders: telegram.org/dl\nThen click: telegram.me/fnfsareesbot");
		
		String subject = "Franchisee Welcome and Terms of Trade - Intricate Fashions India";
		
		String body = "<div class='gmail_default' style='font-family:verdana,sans-serif;font-size:small;color:rgb(111,168,220)'><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'>Dear Franchisee,</p><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'>WELCOME to INTRICATE FASHIONS INDIA PVT LTD, SURAT.</p><h1 style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif'>STEPS TO START RECEIVING IMAGES ON PHONE:</h1><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'><strong>STEP1:</strong>&nbsp;DOWNLOAD OUR CATALOG ON PHONE APP&nbsp;to receive &nbsp;WHATSAPP shareable images</p><ul style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'><li style='padding-left:60px'><a href='https://play.google.com/store/apps/details?id=org.telegram.messenger' target='_blank'>Android App</a></li><li style='padding-left:60px'><a href='https://itunes.apple.com/in/app/telegram-messenger/id686449807?mt=8' target='_blank'>IOS App</a></li></ul><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'><strong>STEP2:</strong>&nbsp;Click on&nbsp;<strong><a href='http://telegram.me/fnfsareesbot' target='_blank'>telegram.me/fnfsareesbot</a>&nbsp;</strong>or search for<strong>&nbsp;@fnfsareesbot&nbsp;</strong>and click JOIN&nbsp;in the App</p><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'>FOR SUPPORT Please &nbsp;<strong>WHATSAPP ON: +91 9586724633</strong>&nbsp;or&nbsp;</p><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'>==============================<wbr>==============================<wbr>============================</p><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'><strong>Key Benefits of FRANCHISEE:</strong><br># 2000 NEW DESIGNS of SAREE SUIT KURTI EVERY MONTH<br># 100% Original Product directly from company<br># Pick and choose order as per your customer order<br># PRICES MENTIONED ON IMAGES is MRP. As a registered FRANCHISEE have 50% margin on this.<br>(Your buying price is MRP – 50% + COURIER)<br># U have RETURNS FACILITY &nbsp;helping you avoid DEAD STOCK (Needs to be Activated. pls read below)<br># SHIPPING/Courier charges Extra as per Actuals<br># U can pass on discount to your customers initially to build your own loyal customer base.</p><p style='color:rgb(51,51,51);font-family:Georgia,'Times New Roman','Bitstream Charter',Times,serif;font-size:16px;line-height:24px'><strong>RETURN POLICY<br></strong>If after delivery for whatever reason you or your customer doesn’t like the product you have the option to RETURN IT BACK for 100% REFUND in form of Credit Note. Such returns can only be sent <span class='aBn' data-term='goog_906832944' tabindex='0'><span class='aQJ'>within 2 days</span></span> after delivery. This way the business is ZERO RISK ZERO INVESTMENT. RETURN COURIER CHARGES WILL BE yours/ your's customer.</p><div><br></div></div>";
		
		mailUtils.sendTextMail(leadRegisterDto.getEmail(),subject, body);
		
		String message= "==========\nNew Lead\n==========\nEmail: "+leadRegisterDto.getEmail()+"\nPhone: "+leadRegisterDto.getPhone()+"\nCalling purpose: +91"+leadRegisterDto.getPhone();
		if(true){
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
			data1.put("chat_id", INTRICATE);
			data1.put("text", message);
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
		if(true){
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
			data1.put("chat_id", ASHISH);
			data1.put("text", message);
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
		if(true){
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
			data1.put("chat_id", SACHIN);
			data1.put("text", message);
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

	public List<LeadJsonDto> fetchLeads(JQTableUtils tableUtils, String userId) {
		return leadFromDao.fetchLeads(tableUtils);
	}

	public long fetchTotalLeads(JQTableUtils tableUtils, String userId) {
		return leadFromDao.fetchTotalLeads(tableUtils);
	}
	
}
