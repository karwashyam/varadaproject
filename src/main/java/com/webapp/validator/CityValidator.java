package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.CityDto;
import com.webapp.models.CityModel;
import com.webapp.services.CityService;

@Component
public class CityValidator implements Validator {

	@Autowired
	private CityService citySerivce;

	public boolean supports(Class<?> clazz) {
		return CityModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		CityModel cityModel = (CityModel) obj;

		if (cityModel.getCityName() != null && cityModel.getStateId() != null
				&& !("").equals(cityModel.getCityName())) {
			if(cityModel.getCityId() == null)
				cityModel.setCityId("");
			boolean cityPresent = citySerivce.fetchCityByName(cityModel);
			if (cityPresent) {
				errors.rejectValue("validCity", "city.exists");
			}
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateId",
					"required.stateId");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName",
					"required.cityName");
		}

	}

}