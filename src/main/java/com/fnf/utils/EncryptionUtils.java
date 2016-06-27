package com.fnf.utils;

import java.security.SecureRandom;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtils
{
  public static String encryptPassword(String password)
  {
    String salt = BCrypt.gensalt(31, new SecureRandom());
    return BCrypt.hashpw(password, salt);
  }
  
  public static boolean isValidPassword(String password, String encryptedPassword)
  {
    return BCrypt.checkpw(password, encryptedPassword);
  }
  
 
  public static void main(String[] args) {
	  System.out.println(encryptPassword("1234"));
}
}
