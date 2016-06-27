package com.fnf.utils;

public class FieldError
{
  private String field;
  private String message;
  
  public FieldError(String field, String message)
  {
    this.field = field;
    this.message = message;
  }
  
  public String getField()
  {
    return this.field;
  }
  
  public String getMessage()
  {
    return this.message;
  }
}
