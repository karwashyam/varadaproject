package com.fnf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateUtils {


	  protected static Logger logger = Logger.getLogger(DateUtils.class);
		public static final String GMT = "GMT";
		public static final String SiMPLE_DATE_FORMAT = "MM/dd/yyyy";
	  public static final String DATE_FORMAT_STR_FOR_12_HOUR = "yyyy/MM/dd hh:mm:ss a";
	  public static final String ONLY_DATE_FORMAT_STR = "yyyy-MM-dd";
	  public static final String ONLY_TIME_FORMAT_STR = "HH:mm:ss";
	  
	  public static String gmtTimeStampStr(Date date)
	  {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    String dateStr = dateFormat.format(date);
	    return dateStr;
	  }
	  
	  public static long nowAsGmtMillisec()
	  {
	    return new Date().getTime();
	  }
	  
	  public static String dbTimeStampToSesionDate(long time, String timeZone, String sessionDateFormatStr)
	  {
	    Date gmtDate = gmtDateFromDbDateTime(new Date(time));
	    
	    SimpleDateFormat currentDateFormat = new SimpleDateFormat(sessionDateFormatStr);
	    currentDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
	    String dateStr = currentDateFormat.format(gmtDate);
	    return dateStr;
	  }
	  
	  private static Date gmtDateFromDbDateTime(Date time)
	  {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
	    String gmtDateStr = dateFormat.format(time);
	    SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
	    parseFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    Date gmtDate = null;
	    try
	    {
	      gmtDate = parseFormat.parse(gmtDateStr);
	    }
	    catch (Exception e)
	    {
	      logger.error("Exception occured during getting gmt date from DBDateTime: ", e);
	      throw new RuntimeException(e);
	    }
	    return gmtDate;
	  }
	
	  public static long getMilesecFromDateStr(String dateStr, String dateFormat, String timezone) {

			if(dateStr== null || dateFormat == null || timezone == null){
				return 0l;
			}
			
			Date dateValue = null;
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setTimeZone(TimeZone.getTimeZone(timezone));
			
			try {
				dateValue = formatter.parse(dateStr);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			return dateValue.getTime();
		}
}
