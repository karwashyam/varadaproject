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

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.NotificationDao;
import com.webapp.dto.NotificationDto;
import com.webapp.fb.models.Attachment;
import com.webapp.fb.models.ImageAttachment;
import com.webapp.fb.models.ImagePayload;
import com.webapp.fb.models.Message;
import com.webapp.fb.models.Messaging;
import com.webapp.fb.models.PostUpdateFbModel;
import com.webapp.fb.models.Recipient;
import com.webapp.fb.models.SendImage;
import com.webapp.fb.models.SendMessage;

@Service("notificationSerivce")
public class NotificationSerivce {


	@Autowired
	private NotificationDao notificationDao;
	
	private static Logger logger = Logger.getLogger(NotificationSerivce.class);

	public HashMap<String, Object> getNotification() {
			
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> notificationList =  notificationDao.getNotification();
		outputMap.put("notifications", notificationList);
		return outputMap;
	}

	public int addNotification(NotificationDto notificationDto) {
		
		 return notificationDao.addNotification(UUIDGenerator.generateUUID(),DateUtils.nowAsGmtMillisec(),notificationDto.getText());
	}

	public Map<String, Object> receivedMessage(Messaging messaging) throws IOException {
			String senderID = messaging.getSender().getId();
			String recipientID = messaging.getRecipient().getId();
		  long timeOfMessage = messaging.getTimestamp();
		  Message message = messaging.getMessage();


		  String messageId = message.getMid();

		  // You may get a text or attachment but not both
		  String messageText = message.getText();
		  List<Attachment> messageAttachments = message.getAttachments();

		  if (messageText!=null && !"".equalsIgnoreCase(messageText)) {

		    switch (messageText) {
		      case "image":
		        sendImageMessage(senderID, messageText);
		        break;

		      case "button":
		      case "buttons":
			        sendButtonsMessage(senderID, messageText);
			        break;

		      case "generic":
		        sendGenericMessage(senderID, messageText);
		        break;

		      case "receipt":
		        sendReceiptMessage(senderID, messageText);
		        break;

		      default:
		        sendTextMessage(senderID, messageText);
		    }
		  } else if (messageAttachments!=null && !messageAttachments.isEmpty()) {
		    sendTextMessage(senderID, "Message with attachment received");
		  }
		  return new HashMap<String,Object>();
	}
	
	
	public void sendTextMessage(String recipientId, String messageText) throws IOException {
		
		Recipient recipient = new Recipient();
		recipient.setId(recipientId);
		SendMessage sendMessage= new SendMessage();
		sendMessage.setText(messageText);
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("recipient", recipient);
		data1.put("message", sendMessage);
		callSendAPI(data1);
	}
	
	public void sendImageMessage(String recipientId, String messageText) throws IOException {
		
		Recipient recipient = new Recipient();
		recipient.setId(recipientId);
		ImagePayload payload = new ImagePayload();
		payload.setUrl("http://messengerdemo.parseapp.com/img/rift.png");
		
		ImageAttachment attachment= new ImageAttachment();
		attachment.setType("image");
		attachment.setPayload(payload);
		SendImage sendImage= new SendImage();
		sendImage.setAttachment(attachment);
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("recipient", recipient);
		data1.put("message", sendImage);
		callSendAPI(data1);
	}
	
	

	public void sendButtonsMessage(String recipientId, String messageText) throws IOException {
		
		Recipient recipient = new Recipient();
		recipient.setId(recipientId);
		JSONObject jsonObject = new JSONObject();
		
		JSONArray outerJsonArray = new JSONArray();
		
		JSONObject jsonInnerObject = new JSONObject();
		jsonInnerObject.put("title", "!st title");
		jsonInnerObject.put("url", "http://www.fnfsarees.com");
		jsonInnerObject.put("type", "web_url");
		outerJsonArray.put(jsonInnerObject);
		
		JSONObject jsonInnerObject1 = new JSONObject();
		jsonInnerObject1.put("title", "!11st title");
		jsonInnerObject1.put("payload", "111st_payload");
		jsonInnerObject1.put("type", "postback");
		outerJsonArray.put(jsonInnerObject1);
		
		jsonObject.put("template_type", "button");
		jsonObject.put("text", "button right?");
		jsonObject.put("buttons", outerJsonArray);
		
		JSONObject attachment = new JSONObject();
		attachment.put("type", "template");
		attachment.put("payload", jsonObject);

		JSONObject message = new JSONObject();
		message.put("attachment", attachment);
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("recipient", recipient);
		data1.put("message", message.toString());
		logger.info(message.toString());
		callSendAPI(data1);
	}
	
	public void sendGenericMessage(String recipientId, String messageText) throws IOException {
			
			Recipient recipient = new Recipient();
			recipient.setId(recipientId);
			
			
			JSONArray outerJsonArray = new JSONArray();
			
			JSONObject jsonInnerObject = new JSONObject();
			jsonInnerObject.put("title", "!st title");
			jsonInnerObject.put("url", "http://www.fnfsarees.com");
			jsonInnerObject.put("type", "web_url");
			outerJsonArray.put(jsonInnerObject);
			
			JSONObject jsonInnerObject1 = new JSONObject();
			jsonInnerObject1.put("title", "!11st title");
			jsonInnerObject1.put("payload", "111st_payload");
			jsonInnerObject1.put("type", "postback");
			outerJsonArray.put(jsonInnerObject1);
			
			JSONArray elements = new JSONArray();
			
			JSONObject element = new JSONObject();
			element.put("buttons", outerJsonArray);
			element.put("title", "1st image");
			element.put("subtitle","1st subtitle");
			element.put("image_url", "http://messengerdemo.parseapp.com/img/rift.png");
			element.put("item_url", "admin.intricatefashions.com");
			elements.put(element);
			JSONObject element1 = new JSONObject();
			element1.put("buttons", outerJsonArray);
			element1.put("title", "11st image");
			element1.put("subtitle","11st subtitle");
			element1.put("image_url", "http://messengerdemo.parseapp.com/img/rift.png");
			elements.put(element1);
			
			JSONObject payload = new JSONObject();
			
			payload.put("template_type","generic");
			payload.put("elements",elements);
			
			JSONObject attachment= new JSONObject();
			attachment.put("type","template");
			attachment.put("payload",payload);
			
			JSONObject sendMessage= new JSONObject();
			sendMessage.put("attachment",attachment);
			HashMap<String, Object> data1 = new HashMap<String, Object>();
			data1.put("recipient", recipient);
			data1.put("message", sendMessage.toString());
			logger.info(sendMessage.toString());
			callSendAPI(data1);
		}
	
	public void sendReceiptMessage(String recipientId, String messageText) throws IOException {
		
		Recipient recipient = new Recipient();
		recipient.setId(recipientId);
		SendMessage sendMessage= new SendMessage();
		sendMessage.setText(messageText);
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("recipient", recipient);
		data1.put("message", sendMessage);
		callSendAPI(data1);
	}
	
	public void callSendAPI(HashMap<String, Object> data1) throws IOException{
		
		URL url = new URL(ProjectConstant.FBURL + ProjectConstant.FNF_PAGE_TOKEN);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		
		InputStream in = null;
		if(responseCode==200){
			in = conn.getInputStream();
			PostUpdateFbModel response = (PostUpdateFbModel)mapper.readValue(in,
					PostUpdateFbModel.class);
			if(response.getError()!=null){
				logger.info("============="+response.getError().getCode()+"\n"+response.getError().getError_data()+"\n"+
						response.getError().getMessage()+"\n"+response.getError().getType());
			}
			in.close();
		}else{
			String errorMessage1 = conn.getResponseMessage();
			logger.info("======"+responseCode+"-----"+errorMessage1);
		}
	}
	
	

	

}