package com.webapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.fnf.utils.FieldError;

public class ValidationError
{
  private List<FieldError> fieldErrors = new ArrayList<FieldError>();
  
  public void addFieldError(String path, String message)
  {
    FieldError error = new FieldError(path, message);
    this.fieldErrors.add(error);
  }
  
  public List<FieldError> getFieldErrors()
  {
    return this.fieldErrors;
  }
}
