package com.fnf.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class CookieUtils
{
  public static final String DELETED_COOKIE_VALUE = "finished";
  public static final String JSESSIONID = "JSESSIONID";
  protected static Logger logger = Logger.getLogger(CookieUtils.class);
  
  public static Cookie getCookie(String cookieName, HttpServletRequest request)
  {
    Cookie cookie = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals(cookieName)) {
          cookie = cookies[i];
        }
      }
    }
    return cookie;
  }
  
  public static void setCookie(Cookie cookie, HttpServletRequest request, HttpServletResponse response)
  {
    response.addCookie(cookie);
  }
  
  public static void deleteCookie(Cookie cookie, HttpServletRequest request, HttpServletResponse response)
  {
    cookie.setValue(null);
    cookie.setMaxAge(0);
    cookie.setPath(request.getContextPath());
    response.addCookie(cookie);
  }
  
  public static void deleteJSessionCookie(HttpServletRequest request, HttpServletResponse response)
  {
    Cookie jsession = getCookie("JSESSIONID", request);
    if (jsession != null)
    {
      jsession.setValue(null);
      jsession.setMaxAge(0);
      jsession.setPath(request.getContextPath() + "/");
      response.addCookie(jsession);
    }
  }
}
