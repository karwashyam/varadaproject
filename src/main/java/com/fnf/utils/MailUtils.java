package com.fnf.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtils
{
  private static Logger logger = Logger.getLogger(MailUtils.class);
  private String mailHost;
  private int mailServerPort;
  private String defaultUsername;
  private String defaultPassword;
  private String defaultFrom;
  private boolean usingGmail;
  
  public boolean sendTextMail(String to, String subject, String message)
  {
    try
    {
      if (this.usingGmail)
      {
        Properties props = new Properties();
        
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", this.mailHost);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        
        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
        transport.connect(this.mailHost, this.mailServerPort, this.defaultUsername, this.defaultPassword);
        
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(message, "text/html");
        
        mimeMessage.setFrom(new InternetAddress(this.defaultFrom));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        
        transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
        transport.close();
        
        return true;
      }
      Properties props = new Properties();
      
      props.put("mail.smtp.host", this.mailHost);
      props.put("mail.smtp.port", Integer.valueOf(this.mailServerPort));
      props.put("mail.smtp.user", this.defaultUsername);
      props.put("mail.debug", "true");
      
      logger.debug("mailHost : " + this.mailHost);
      logger.debug("mailServerPort : " + this.mailServerPort);
      logger.debug("efaultUsername : " + this.defaultUsername);
      
      Session session = Session.getInstance(props);
      
      Message msg = new MimeMessage(session);
      
      msg.setFrom(new InternetAddress(this.defaultFrom));
      InternetAddress[] address = { new InternetAddress(to) };
      msg.setRecipients(Message.RecipientType.TO, address);
      msg.setSubject(subject);
      msg.setSentDate(new Date());
      msg.setContent(message, "text/html");
      
      msg.saveChanges();
      
      Transport bus = session.getTransport("smtp");
      bus.connect(this.mailHost, this.mailServerPort, this.defaultUsername, this.defaultPassword);
      bus.sendMessage(msg, address);
      
      bus.close();
      
      return true;
    }
    catch (MessagingException m)
    {
      logger.debug("Sending mail failed due to MessagingException");
      m.printStackTrace();
      return false;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.debug("Sending mail failed", e);
    }
    return false;
  }
  
  public String getMailHost()
  {
    return this.mailHost;
  }
  
  public void setMailHost(String mailHost)
  {
    this.mailHost = mailHost;
  }
  
  public int getMailServerPort()
  {
    return this.mailServerPort;
  }
  
  public void setMailServerPort(int mailServerPort)
  {
    this.mailServerPort = mailServerPort;
  }
  
  public String getDefaultUsername()
  {
    return this.defaultUsername;
  }
  
  public void setDefaultUsername(String defaultUsername)
  {
    this.defaultUsername = defaultUsername;
  }
  
  public String getDefaultPassword()
  {
    return this.defaultPassword;
  }
  
  public void setDefaultPassword(String defaultPassword)
  {
    this.defaultPassword = defaultPassword;
  }
  
  public String getDefaultFrom()
  {
    return this.defaultFrom;
  }
  
  public void setDefaultFrom(String defaultFrom)
  {
    this.defaultFrom = defaultFrom;
  }
  
  public boolean isUsingGmail()
  {
    return this.usingGmail;
  }
  
  public void setUsingGmail(boolean usingGmail)
  {
    this.usingGmail = usingGmail;
  }
}
