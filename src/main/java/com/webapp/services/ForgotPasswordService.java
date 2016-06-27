package com.webapp.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.CommonUtils;
import com.fnf.utils.DateUtils;
import com.fnf.utils.EncryptionUtils;
import com.fnf.utils.MailUtils;
import com.webapp.daos.AppUserDao;
import com.webapp.models1.AppUser;

@Service("forgotPasswordService")
public class ForgotPasswordService {

	@Autowired
	private AppUserDao appUserDao;

	@Autowired
	private MailUtils mailUtils;

	@Transactional
	public int setNewPassword(AppUser user) {
		long currDate = DateUtils.nowAsGmtMillisec();
		AppUser userModel = new AppUser();
		int status=0;
		userModel.setUpdatedAt(currDate);
		String password = simpleRandomString(5);
		userModel.setPassword(EncryptionUtils.encryptPassword(password));
		if(user.getEmail()!=null&&!"".equalsIgnoreCase(user.getEmail())){
			userModel.setEmail(user.getEmail());
			status = appUserDao.setNewPassword(userModel);
			userModel = appUserDao.getUserAccountDetailsById(user.getEmail());
			
		}else if(user.getPhone()!=null&&!"".equalsIgnoreCase(user.getPhone())){
			userModel.setPhone(user.getPhone());
			status = appUserDao.setNewPasswordByPhone(userModel);
			userModel = appUserDao.getUserAccountDetailsByPhone(user.getPhone());
		}
		if (status > 0) {
			if(user.getEmail()!=null&&!"".equalsIgnoreCase(user.getEmail())){
				String message = "Hello, <br/>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <br/>Your Password has been reset.<br/> Your New Password is " + password;
				message += "<br/><br/><br/>Thanks, <br/>Team Intricate";
				mailUtils.sendTextMail(user.getEmail(), "Temporary Password Intricate Fashions", message);
			}
			if(user.getPhone()!=null&&!"".equalsIgnoreCase(user.getPhone())){
				new CommonUtils().sendSms(user.getPhone(), "Your Password has been reset.<br/> Your New Password is " + password+" -Team Intricate");
			}
		}
		return status;
	}

	/*@Transactional
	public int setMemberNewPassword(String userId) {

		long currDate = DateUtils.nowAsGmtMillisec();
		MemberSyncModel memberModel = userDao.getUserMemberAccountDetailsByUserId(userId);
		User userModel = new User();
		userModel.setUpdatedAt(currDate);
		String password = simpleRandomString(4);
		userModel.setPassword(EncryptionUtils.encryptPassword(password));
		userModel.setUserId(userId);
		int status = userDao.setNewPasswordById(userModel);
		String name[]= memberModel.getFullName().split(" ");
		String capitalizedName=name[0].substring(0, 1).toUpperCase()+name[0].substring(1, name[0].length());
		if (status > 0) {
			if (memberModel.getEmail() != null && !"".equalsIgnoreCase(memberModel.getEmail())) {
				String message = "Dear " + capitalizedName + ",<br><br>" + "Thank you for registering with us." + "<br><br>You may login on www.thestylist.in with the following information: " + "<br><br>Member Code: " + memberModel.getMemberCode()
						+ "<br>" + "Temporary Password: " + password + "<br><br>" + "For any further assistance, mail us at " + "info@purplestylelabs.com<br><br>" + "Best Regards,<br>Team PSL";
				mailUtils.setDefaultFrom("no-reply@purplestylelabs.com");
				mailUtils.setDefaultUsername("PSL Alerts");
				mailUtils.sendTextMail(memberModel.getEmail(), "Your PSL/The Stylist Account Password", message);
			}
			if (memberModel.getPhone() != null && !"".equalsIgnoreCase(memberModel.getPhone())) {
				String message="Dear "+capitalizedName+", your temporary password for PSL Member code ("+memberModel.getUserName()+") is: "+password+". Login to www.thestylist.in using the same.";
				new SendMessageUtils().sendSms(message, memberModel.getPhone().replace("-",""));
			}
		}

		return status;
	}*/

	static String randomString(int len) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		final String ALPHAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final String SPECIAL = "!@#$%&*+?";

		Random rnd = new Random();
		int oneNumeric = rnd.nextInt(9);
		char oneAlpha = ALPHAS.charAt(rnd.nextInt(ALPHAS.length()));
		char oneSymbol = SPECIAL.charAt(rnd.nextInt(SPECIAL.length()));

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));

		return sb.toString() + "" + oneSymbol + "" + oneNumeric + "" + oneAlpha;
	}

	static String simpleRandomString(int len) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		Random rnd = new Random();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));

		return sb.toString();
	}
}
