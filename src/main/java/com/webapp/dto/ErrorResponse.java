package com.webapp.dto;

import java.util.List;

import com.fnf.utils.ErrorMessage;

public class ErrorResponse
{
  private String type;
  private List<ErrorMessage> messages;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public List<ErrorMessage> getMessages()
  {
    return this.messages;
  }
  
  public void setMessages(List<ErrorMessage> messages)
  {
    this.messages = messages;
  }
}
