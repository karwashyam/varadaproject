package com.utils.constant;


public interface ErrorConstant  {


	public static final int ERROR_EMAIL_SENDING_FAILED = 4001;

	public static final int ERROR_ACCESS_TOKEN = 4002;
	public static final String TECHNICAL_ERROR_TYPE = "ERR-TECH";
	  public static final String BUSSINES_ERROR_TYPE = "ERR-BUSS";
	  public static final String SUCCESS_MESSAGE_TYPE = "SUCCESS";
	  public static final int ERROR_CODE_UNAUTHORIZED_ACCESS = 401;
	  public static final String ERROR_MSG_UNAUTHORIZED_ACCESS = "Unauthorized access, please check header 'x-api-key'.";
	  public static final String ERROR_MSG_UNAUTHORIZED_ACCESS_AJAX = "It seems that you are currently not logged in to system, please login again.";
	  public static final int ERROR_CODE_NO_ACCESS = 403;
	  public static final String ERROR_MSG_NO_ACCESS = "Sorry, you dont have access for this function.";
	  public static final int ERROR_CODE_UNEXPECTED = 500;
	  public static final String ERROR_MSG_UNEXPECTED = "Unexpected error occurred, please try again after some time.";
	  public static final int ERROR_NULL_CHECK = 1001;
	  public static final int ERROR_ALPHA_CHARACTERS = 1002;
	  public static final int ERROR_REQUIRE_LONG = 1003;
	  public static final int ERROR_REQUIRE_INT = 1004;
	  public static final int ERROR_REQUIRE_DECIMAL = 1005;
	  public static final int ERROR_REQUIRE_STRING = 1006;
	  public static final int ERROR_INVALID_DEVICE_TYPE = 1007;
	  public static final int ERROR_INVALID_EMAIL_ID = 1008;
	  public static final int ERROR_EMAIL_ALREADY_EXISTS = 1009;
	  public static final int ERROR_INVALID_EMAIL_DOMAIN = 1010;
	  public static final int ERROR_PASSWORD_SHOULD_MINIMUM_6 = 1011;
	  public static final int ERROR_REQUIRE_BOOLEAN = 1012;
	  public static final int ERROR_INVALID_RECORD_STATUS = 1013;
	  public static final int ERROR_EXCEED_MAX_LENGTH = 1015;
	  public static final int ERROR_ATLEAST_ONE_ALPHA_CHARACTER = 1016;
	  public static final int ERROR_ALPHA_NUMERIC_DASH_CHARACTERS = 1017;
	  public static final int ERROR_ALPHA_NUMERIC_CHARACTERS = 1018;
	  public static final int ERROR_NUMERIC_CHARACTERS = 1019;
	  public static final int ERROR_ACCOUNT_NOT_FOUND_WITH_EMAIL = 2001;
	  public static final int ERROR_INVALID_DEVICE_TYPE_VERSION = 2002;
	  public static final int ERROR_CHECK_EMAIL_AND_PASSWORD = 2003;
	  public static final int ERROR_UNABLE_TO_REGISTERED_DEVICE = 2004;
	  public static final int ERROR_INVALID_FILE_EXTENSION = 2005;
	  public static final int ERROR_INVALID_FILE_TYPE = 2006;
	  public static final int ERROR_PDF_SIZE_NOT_GREATER_THAN_100 = 2007;
	  public static final int ERROR_IMAGE_SIZE_NOT_GREATER_THAN_10 = 2008;
	  public static final int ERROR_IMAGE_RESOLUTION_GREATER_EQUAL_200_200 = 2009;
	  public static final int ERROR_IMAGE_RESOLUTION_EXACT = 2010;
	  public static final int ERROR_UNABLE_TO_UPLOAD_FILE = 2011;
	  public static final int ERROR_IS_SPACE_PASSWORD = 2012;
}
