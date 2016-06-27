package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fnf.utils.CommonUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.SendMessageDto;
import com.webapp.services.FnFTelegramService;

@Controller
@RequestMapping("/send-sms")
public class SendSmsController extends BusinessController {

	@Autowired
	private FnFTelegramService fnFTelegramService;

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
		return "send-sms";
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
		if (sendMessageDto.getPhone().equalsIgnoreCase("all")) {
			List<String> listMobileNo = fnFTelegramService.getAllPhone();
			for (String mobile : listMobileNo) {
				if (mobile!=null && mobile.contains("/")) {
					String[] newMobiles = mobile.split("/");
					for (String string : newMobiles) {
						new CommonUtils().sendSms(string,
								sendMessageDto.getMessage());
						model.addAttribute("msg", "Sms Send Successfully");
					}
				} else {
					new CommonUtils().sendSms(mobile,
							sendMessageDto.getMessage());
					model.addAttribute("msg", "Sms Send Successfully");
				}
			}
		} else {

			new CommonUtils().sendSms(sendMessageDto.getPhone(),
					sendMessageDto.getMessage());
			model.addAttribute("msg", "Sms Send Successfully");
		}

		return "send-sms";
	}

}
