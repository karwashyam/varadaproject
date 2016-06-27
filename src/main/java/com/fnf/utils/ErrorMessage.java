package com.fnf.utils;

public class ErrorMessage
{
  private int code;
  private String message;
  private String fieldName;
  
  public ErrorMessage() {}
  
  public ErrorMessage(int code, String message, String fieldName)
  {
    this.code = code;
    this.message = message;
    this.fieldName = fieldName;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public String getFieldName()
  {
    return this.fieldName;
  }
  
  public void setFieldName(String fieldName)
  {
    this.fieldName = fieldName;
  }
}
