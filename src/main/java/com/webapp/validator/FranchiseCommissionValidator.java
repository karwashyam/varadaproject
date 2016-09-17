package com.webapp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.webapp.models.FranchiseCommissionModel;

@Component
public class FranchiseCommissionValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return FranchiseCommissionModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		FranchiseCommissionModel model= (FranchiseCommissionModel)obj;
		model = trimAllStringValues(model);
		
		if(model.getFranchiseeId()== null || "".equalsIgnoreCase(model.getFranchiseeId()))
		{
			errors.rejectValue("franchiseeId", "required.franchiseeId","Franchise Name is Required");
		}
		
		if(model.getTds()!=0)
		{
			if (!((model.getTds()>0) && (model.getTds()<100))) 
			{
				errors.rejectValue("tds", "tds.incorrect","Enter a correct TDS");
			}
		}
		
	}
	
	public FranchiseCommissionModel trimAllStringValues(FranchiseCommissionModel model){
		if(model.getFranchiseeName()!= null && !"".equalsIgnoreCase(model.getFranchiseeName()))
			model.setFranchiseeName(model.getFranchiseeName().trim());
		if(model.getPan()!= null && !"".equalsIgnoreCase(model.getPan()))
			model.setPan(model.getPan().trim());
		return model;
	}
}