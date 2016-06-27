package com.fnf.utils;

import java.util.StringTokenizer;

public class StringUtils
{
  public static String[] split(String toSplit, String separator)
  {
    StringTokenizer tokenizer = new StringTokenizer(toSplit, separator);
    int count = tokenizer.countTokens();
    String[] tokens = new String[count];
    for (int i = 0; i < count; i++) {
      tokens[i] = tokenizer.nextToken();
    }
    return tokens;
  }
  
  public static String capatalize(String str)
  {
    if (str.length() == 0) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }
  
  public static String removePrefix(String str, String prefix)
  {
    if (str.length() == prefix.length()) {
      return "";
    }
    return str.substring(prefix.length());
  }
  
  public static String getTrimmedTo(String string, int trimLen)
  {
    String trimString = string;
    int upperletterCounter = 0;
    if (string.length() > trimLen)
    {
      trimString = string.substring(0, trimLen);
      if (trimString.length() > 0)
      {
        char[] chars = trimString.toCharArray();
        char[] arrayOfChar1;
        int j = (arrayOfChar1 = chars).length;
        for (int i = 0; i < j; i++)
        {
          char c = arrayOfChar1[i];
          if (Character.isUpperCase(c)) {
            upperletterCounter++;
          }
        }
      }
      if (upperletterCounter > 0)
      {
        trimLen = trimLen - upperletterCounter / 2 - 3;
        trimString = trimString.substring(0, trimLen);
      }
      else
      {
        trimString = trimString.substring(0, trimLen - 3);
      }
      trimString = trimString + "...";
    }
    return trimString;
  }
  
  public static String getExtension(String fileName)
  {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1) {
      return "";
    }
    String extension = fileName.substring(lastIndexOfDot);
    return extension;
  }
}
