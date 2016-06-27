package com.fnf.utils;


import java.util.UUID;

public class UUIDGenerator
{
  public static final String generateUUID()
  {
    UUID uuid = UUID.randomUUID();
    return uuid.toString().replace("-", "");
  }
}
