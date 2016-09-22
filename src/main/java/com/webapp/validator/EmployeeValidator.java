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
import com.webapp.services.EmployeeService;

@Component
public class EmployeeValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private String STRING_PATTERN = "[a-zA-Z ]+";
	private String MOBILE_PATTERN = "[0-9]{10}";
	private static final String PASSWORD_PATTERN =".{4,50}";

	@Autowired
	private EmployeeService employeeSerivce;

	public boolean supports(Class<?> clazz) {
		return EmployeeModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		EmployeeModel model= (EmployeeModel)obj;
		model = trimAllStringValues(model);
		if(model.getUserId()==null)
			model.setUserId("");
		if(model.getBirthDateForModel()!=null && !"".equalsIgnoreCase(model.getBirthDateForModel()))
		{
			boolean valid= validateDate(model.getBirthDateForModel());
			if(!valid){
				errors.rejectValue("birthDate", "birthDate.invalidDateOfBirth","Enter a valid Birth Date");
			}
		}
		if(model.getFullName()!= null && !"".equalsIgnoreCase(model.getFullName()))
		{
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(model.getFullName());
			if (!matcher.matches()) 
			{
				errors.rejectValue("fullName", "fullName.containNonChar","Enter a valid Full Name");
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
				if(employeeSerivce.fetchEmpByEmail(model)){
					errors.rejectValue("email", "email.incorrect","Email already Exist");
				}
			}
		}
		else
		{
			errors.rejectValue("email", "required.email","Email is required");
		}
		
		if(model.getPhone()!=null && !"".equalsIgnoreCase(model.getPhone()))
		{
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(model.getPhone());
			if (!matcher.matches()) 
			{
				errors.rejectValue("phone", "phone.incorrect","Enter a correct phone number");
			}else
			{
				if(employeeSerivce.fetchEmpByPhone(model)){
					errors.rejectValue("phone", "phone.incorrect","Phone number already Exist");
				}
			}
		}
		else
		{
			errors.rejectValue("phone", "required.phone","Phone Number is Required");
		}
		if(model.getUserId()==null && !"".equalsIgnoreCase(model.getUserId()))
		{
			if(model.getPassword()!=null && !"".equalsIgnoreCase(model.getPassword())){
				pattern = Pattern.compile(PASSWORD_PATTERN);
				matcher = pattern.matcher(model.getPassword());
				if (!matcher.matches()) 
				{
					errors.rejectValue("password", "password.incorrect","Please enter Atleast 4 characters");
				}
				else
				{
					if(model.getConfirmPassword()!=null && !"".equalsIgnoreCase(model.getConfirmPassword())){
						if(!model.getPassword().equals(model.getConfirmPassword())){
							errors.rejectValue("confirmPassword", "confirmPassword.incorrect","Enter a Valid Confirm Password");
						}
					}
					else
					{
						errors.rejectValue("confirmPassword", "required.confirmPassword","Enter the Confirm Password");
					}
				}
			}
			else
			{
				errors.rejectValue("password", "required.password","Enter the Password");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName","required.fullName");
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
	
	public EmployeeModel trimAllStringValues(EmployeeModel model){
		if(model.getFullName()!= null && !"".equalsIgnoreCase(model.getFullName()))
			model.setFullName(model.getFullName().trim());
		if(model.getEmail()!= null && !"".equalsIgnoreCase(model.getEmail()))
			model.setEmail(model.getEmail().trim());
		return model;
	}
}