package com.webapp.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.webapp.models.MemberModel;
import com.webapp.services.FranchiseService;
import com.webapp.services.MemberService;

@Component
public class MemberValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private String STRING_PATTERN = "[a-zA-Z ]+";
	private String MOBILE_PATTERN = "[0-9]{10}";
	private String LANDLINE_PATTERN = "\\d{3}[\\s]\\d{3}[\\s]\\d{4}";
	private String PINCODE_PATTERN ="[1-9][0-9]{5}";
	private String PAN_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

	@Autowired
	private MemberService memberService;

	@Autowired
	private FranchiseService franchiseService;
	
	public boolean supports(Class<?> clazz) {
		return MemberModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		MemberModel model= (MemberModel)obj;
		model = trimAllStringValues(model);
		if(model.getMemberId()==null)
			model.setMemberId("");
		
		if(model.getMemberName()!= null && !"".equalsIgnoreCase(model.getMemberName()))
		{
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(model.getMemberName());
			if (!matcher.matches()) 
			{
				errors.rejectValue("memberName", "memberName.containNonChar","Enter a valid Member Name");
			}
		}
		else
		{
			errors.rejectValue("memberName", "required.memberName","Member Name is Required");
		}
		if(model.getFatherName()!= null && !"".equalsIgnoreCase(model.getFatherName()))
		{
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(model.getFatherName());
			if (!matcher.matches()) 
			{
				errors.rejectValue("fatherName", "fatherName.containNonChar","Enter a valid Father's Name");
			}
		}
		if(model.getEmail()!= null && !"".equalsIgnoreCase(model.getEmail()))
		{
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(model.getEmail());
			if (!matcher.matches()) 
			{
				errors.rejectValue("email", "email.incorrect","Enter a correct email");
			}
			else
			{
				if(memberService.fetchMemberByEmail(model)){
					errors.rejectValue("email", "email.incorrect","Email is already Exist");
				}
			}
		}
		else
		{
			errors.rejectValue("email", "required.email","Email is required");
		}
		
		if(model.getPhone1()!=null && !"".equalsIgnoreCase(model.getPhone1()))
		{
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(model.getPhone1());
			if (!matcher.matches()) 
			{
				errors.rejectValue("phone1", "phone1.incorrect","Enter a correct phone number");
			}else
			{
				if(memberService.fetchMemberByPhone(model)){
					errors.rejectValue("phone1", "phone1.incorrect","Phone number already Exist");
				}
			}
		}
		else
		{
			errors.rejectValue("phone1", "required.phone1","Phone Number is Required");
		}
		if(model.getPhone2()!=null && !"".equalsIgnoreCase(model.getPhone2()))
		{
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(model.getPhone2());
			if (!matcher.matches()) 
			{
				errors.rejectValue("phone2", "phone2.incorrect","Enter a correct phone number");
			}
		}
		if(model.getLandline1()!=null && !"".equalsIgnoreCase(model.getLandline1()))
		{
			pattern = Pattern.compile(LANDLINE_PATTERN);
			matcher = pattern.matcher(model.getLandline1());
			if (!matcher.matches()) 
			{
				errors.rejectValue("landline1", "landline1.incorrect","Enter a correct Landline number");
			}
		}
		if(model.getLandline2()!=null && !"".equalsIgnoreCase(model.getLandline2()))
		{
			pattern = Pattern.compile(LANDLINE_PATTERN);
			matcher = pattern.matcher(model.getLandline2());
			if (!matcher.matches()) 
			{
				errors.rejectValue("landline2", "landline1.incorrect","Enter a correct Landline number");
			}
		}
		if(model.getPincode1()!=null && !"".equalsIgnoreCase(model.getPincode1()))
		{
			pattern = Pattern.compile(PINCODE_PATTERN);
			matcher = pattern.matcher(model.getPincode1());
			if (!matcher.matches()) 
			{
				errors.rejectValue("pincode1", "pincode1.incorrect","Enter a correct Pincode");
			}
		}
		if(model.getPincode2()!=null && !"".equalsIgnoreCase(model.getPincode2()))
		{
			pattern = Pattern.compile(PINCODE_PATTERN);
			matcher = pattern.matcher(model.getPincode2());
			if (!matcher.matches()) 
			{
				errors.rejectValue("pincode1", "pincode1.incorrect","Enter a correct Pincode");
			}
		}
		if(model.getFax()!=null && !"".equalsIgnoreCase(model.getFax()))
		{
			pattern = Pattern.compile(LANDLINE_PATTERN);
			matcher = pattern.matcher(model.getFax());
			if (!matcher.matches()) 
			{
				errors.rejectValue("fax", "fax.incorrect","Enter a correct Fax number");
			}
		}
		if(model.getPancard()!=null && !"".equalsIgnoreCase(model.getPancard()))
		{
			pattern = Pattern.compile(PAN_PATTERN);
			matcher = pattern.matcher(model.getPancard());
			if (!matcher.matches()) 
			{
				errors.rejectValue("pancard", "pancard.incorrect","Enter a correct Pan Number");
			}else
			{
				if(memberService.fetchMemberByPan(model)){
					errors.rejectValue("pancard", "pancard.incorrect","PAN number already Exist");
				}
			}
		}
		else
		{
			errors.rejectValue("pancard", "required.pancard","PAN Number is Required");
		}
		if(model.getDob()!=null && !model.getDob().equals("")){
			if(!validateDate(model.getDob()))
				errors.rejectValue("dob", "dob,error","Enter Valid Date");
		}
	}
	
	public boolean validateDate(String date) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	    try {
	        sdf.parse(date);
	        return true;
	    }
	    catch(ParseException ex) {
	        return false;
	    }
	}
	
	public MemberModel trimAllStringValues(MemberModel model){
		if(model.getFranchiseeName()!= null && !"".equalsIgnoreCase(model.getFranchiseeName()))
			model.setFranchiseeName(model.getFranchiseeName().trim());
		if(model.getMemberName()!= null && !"".equalsIgnoreCase(model.getMemberName()))
			model.setMemberName(model.getMemberName().trim());
		if(model.getEmail()!= null && !"".equalsIgnoreCase(model.getEmail()))
			model.setEmail(model.getEmail().trim());
		if(model.getCity1()!= null && !"".equalsIgnoreCase(model.getCity1()))
			model.setCity1(model.getCity1().trim());
		if(model.getState1()!= null && !"".equalsIgnoreCase(model.getState1()))
			model.setState1(model.getState1().trim());
		if(model.getCity2()!= null && !"".equalsIgnoreCase(model.getCity2()))
			model.setCity2(model.getCity2().trim());
		if(model.getState2()!= null && !"".equalsIgnoreCase(model.getState2()))
			model.setState2(model.getState2().trim());
		return model;
	}
}