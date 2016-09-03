package com.webapp.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.models.EmployeeModel;
import com.webapp.models.FranchiseModel;
import com.webapp.services.EmployeeService;
import com.webapp.services.FranchiseService;

@Component
public class FranchiseValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private String STRING_PATTERN = "[a-zA-Z ]+";
	private String MOBILE_PATTERN = "[0-9]{10}";
	private String PAN_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
	private String LANDLINE_PATTERN = "\\d{3}[\\s]\\d{3}[\\s]\\d{4}";
	private String PINCODE_PATTERN ="[1-9][0-9]{5}";

	@Autowired
	private FranchiseService franchiseService;

	public boolean supports(Class<?> clazz) {
		return FranchiseModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		FranchiseModel model= (FranchiseModel)obj;
		model = trimAllStringValues(model);
		if(model.getFranchiseeId()==null)
			model.setFranchiseeId("");
		
		if(model.getFranchiseeName()!= null && !"".equalsIgnoreCase(model.getFranchiseeName()))
		{
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(model.getFranchiseeName());
			if (!matcher.matches()) 
			{
				errors.rejectValue("franchiseeName", "franchiseeName.containNonChar","Enter a valid Franchise Name");
			}
		}
		else
		{
			errors.rejectValue("franchiseeName", "required.franchiseeName","Franchise Name is Required");
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
				if(franchiseService.fetchFranByEmail(model)){
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
				if(franchiseService.fetchFranByPhone(model)){
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
		if(model.getLandLine1()!=null && !"".equalsIgnoreCase(model.getLandLine1()))
		{
			pattern = Pattern.compile(LANDLINE_PATTERN);
			matcher = pattern.matcher(model.getLandLine1());
			if (!matcher.matches()) 
			{
				errors.rejectValue("landLine1", "landLine1.incorrect","Enter a correct Landline number");
			}
		}
		if(model.getLandLine2()!=null && !"".equalsIgnoreCase(model.getLandLine2()))
		{
			pattern = Pattern.compile(LANDLINE_PATTERN);
			matcher = pattern.matcher(model.getLandLine2());
			if (!matcher.matches()) 
			{
				errors.rejectValue("landLine2", "landLine2.incorrect","Enter a correct Landline number");
			}
		}
		if(model.getPincode()!=null && !"".equalsIgnoreCase(model.getPincode()))
		{
			pattern = Pattern.compile(PINCODE_PATTERN);
			matcher = pattern.matcher(model.getPincode());
			if (!matcher.matches()) 
			{
				errors.rejectValue("pincode", "pincode.incorrect","Enter a correct Pincode");
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
		if(model.getPan()!=null && !"".equalsIgnoreCase(model.getPan()))
		{
			pattern = Pattern.compile(PAN_PATTERN);
			matcher = pattern.matcher(model.getPan());
			if (!matcher.matches()) 
			{
				errors.rejectValue("pan", "pan.incorrect","Enter a correct Pan Number");
			}else
			{
				if(franchiseService.fetchFranByPan(model)){
					errors.rejectValue("pan", "pan.incorrect","PAN number already Exist");
				}
			}
		}
		else
		{
			errors.rejectValue("pan", "required.pan","PAN Number is Required");
		}
		if(model.getTds()!=0)
		{
			if (!((model.getTds()>0) && (model.getTds()<100))) 
			{
				errors.rejectValue("tds", "tds.incorrect","Enter a correct TDS");
			}
		}
		if(model.getCommissionPercentage()!=0)
		{
			if (!((model.getCommissionPercentage()>0) && (model.getCommissionPercentage()<100))) 
			{
				errors.rejectValue("commissionPercentage", "commissionPercentage.incorrect","Enter a correct Commission %");
			}
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
	
	public FranchiseModel trimAllStringValues(FranchiseModel model){
		if(model.getFranchiseeName()!= null && !"".equalsIgnoreCase(model.getFranchiseeName()))
			model.setFranchiseeName(model.getFranchiseeName().trim());
		if(model.getEmail()!= null && !"".equalsIgnoreCase(model.getEmail()))
			model.setEmail(model.getEmail().trim());
		if(model.getPan()!= null && !"".equalsIgnoreCase(model.getPan()))
			model.setPan(model.getPan().trim());
		if(model.getCity()!= null && !"".equalsIgnoreCase(model.getCity()))
			model.setCity(model.getCity().trim());
		if(model.getState()!= null && !"".equalsIgnoreCase(model.getState()))
			model.setState(model.getState().trim());
		return model;
	}
}