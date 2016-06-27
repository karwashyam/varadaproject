package com.webapp.controllers.api.messenger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.controllers.BusinessApiController;
import com.webapp.fb.models.BaseFb;
import com.webapp.fb.models.Entry;
import com.webapp.fb.models.Messaging;
import com.webapp.services.NotificationSerivce;

@RestController
@RequestMapping("/api/messenger-webhook")
public class MessengerWebhookApiController extends BusinessApiController {

	@Autowired
	private NotificationSerivce notificationSerivce;
	
	private static Logger logger = Logger.getLogger(NotificationSerivce.class);
	
	/*@Autowired
	private LoginApiValidator loginApiValidator;

	@InitBinder("loginApi")
	private void initLoginBinder(WebDataBinder binder) {
		binder.setValidator(loginApiValidator);
	}*/

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addNotification(@RequestBody BaseFb baseFb, HttpServletRequest req, HttpServletResponse res) throws IOException {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		String object= baseFb.getObject();
		if(object.equalsIgnoreCase("page")){
			for (Entry entry : baseFb.getEntry()) {
				String pageId=entry.getId();
				long time=entry.getTime();
				for (Messaging messaging : entry.getMessaging()) {
					if(messaging.getOptin()!=null){
//						outputMap=notificationSerivce.receivedAuthentication(messaging);
						logger.info("=========Optin\n"+messaging.getOptin()+"\n"+messaging.getOptin().getRef());
					}else if(messaging.getMessage()!=null){
						logger.info("=========getMessage\n"+messaging.getMessage()+"\n"+messaging.getMessage().getText());
						outputMap=notificationSerivce.receivedMessage(messaging);
					}else if(messaging.getDelivery()!=null){
//						outputMap=notificationSerivce.receivedDeliveryConfirmation(messaging);
						logger.info("=========getDelivery\n"+messaging.getDelivery()+"\n"+messaging.getDelivery().getMids());
					}else if(messaging.getPostback()!=null){
//						outputMap=notificationSerivce.receivedPostback(messaging);
						logger.info("=========getPostback\n"+messaging.getPostback()+"\n"+messaging.getPostback().getPayload());
					}else {
//				          console.log("Webhook received unknown messagingEvent: ", messagingEvent);
			        }
				}
			}
		}
		
		return getOutputResponse(outputMap);

	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Object getNotification( HttpServletRequest req, HttpServletResponse res) {
		if(req.getParameter("hub.mode").equalsIgnoreCase("subscribe") && req.getParameter("hub.verify_token").equalsIgnoreCase("123")){
			return req.getParameter("hub.challenge");
		}else{
			
			return "error";
		}
		
		
	}


}