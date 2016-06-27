package com.webapp.services;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.EncryptionUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.AppUserDao;
import com.webapp.dto.SignupDto;
import com.webapp.models1.AppUser;
import com.webapp.models1.SignupModel;

@Service("signupService")
public class SignupService {

	protected static Logger logger = Logger.getLogger(SignupService.class);

	@Autowired
	private AppUserDao appUserDao;

	@Transactional
	public AppUser addUser(SignupDto signupDto) {
		long currentTime = DateUtils.nowAsGmtMillisec();
		
		SignupModel signupModel = new SignupModel();
		signupModel.setUserId(UUIDGenerator.generateUUID());
		signupModel.setFullName(signupDto.getFullName());
		signupModel.setEmail(signupDto.getEmail());
		signupModel.setPhone(signupDto.getPhone());
		signupModel.setPassword(EncryptionUtils.encryptPassword(signupDto.getPassword()));
		signupModel.setCity(signupDto.getCity());
		signupModel.setCreatedAt(currentTime);
		signupModel.setUpdatedAt(currentTime);
		signupModel.setCreatedBy(signupModel.getUserId());
		signupModel.setUpdatedBy(signupModel.getUserId());
		signupModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		signupModel.setReferralCode(simpleRandomString(6));
		if(signupDto.getInvitedBy()!=null && !"".equalsIgnoreCase(signupDto.getInvitedBy())){
			signupModel.setInvitedBy(signupDto.getInvitedBy());
			//get Invite an Earn details from db and update
			signupModel.setWalletBalance(100);
			signupModel.setInviteAndEarnId(null);
		}else{
			signupModel.setInvitedBy(null);
			signupModel.setWalletBalance(0);
		}
		appUserDao.addUser(signupModel);
		signupModel.setPassword("");
		AppUser appUser = appUserDao.getUserAccountDetailsById(signupModel.getUserId());
		return appUser;
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