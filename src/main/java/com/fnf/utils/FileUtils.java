package com.fnf.utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;

public class FileUtils
{
  private static Logger logger = Logger.getLogger(FileUtils.class);
  private String bucketProgram;
  private S3Utils s3Utils;
  
  public String getBucketProgram()
  {
    return this.bucketProgram;
  }
  
  public void setBucketProgram(String bucketProgram)
  {
    this.bucketProgram = bucketProgram;
  }
  
  
  public S3Utils getS3Utils()
  {
    return this.s3Utils;
  }
  
  public void setS3Utils(S3Utils s3Utils)
  {
    this.s3Utils = s3Utils;
  }
  
  public boolean downloadFromBucket(String bucket, String fileName, File fileToWrite)
  {
    boolean result = false;
    String str;
    switch ((str = this.bucketProgram).hashCode())
    {
    case 2624: 
      if (str.equals("S3"))
      {
        try
        {
          this.s3Utils.download(bucket, fileName, fileToWrite);
        }
        catch (ServiceException e)
        {
          logger.error("Unable to download file,", e);
        }
        catch (IOException e)
        {
          logger.error("Unable to download file,", e);
        }
        result = true;
      }
      break;
    }
//    label143:
//    logger.warn("Invalid bucket program.");
//    label151:
    	return result;
  }
  
  public boolean uploadToBucket(String bucket, File fileToUpload)
  {
    boolean result = false;
    String str;
    switch ((str = this.bucketProgram).hashCode())
    {
    case -878667098: 
      break;
    case 2624: 
      if (str.equals("S3"))
      {
        try
        {
          this.s3Utils.upload(bucket, fileToUpload);
        }
        catch (S3ServiceException e)
        {
          logger.error("Unable to upload file,", e);
        }
        catch (NoSuchAlgorithmException e)
        {
          logger.error("Unable to upload file,", e);
        }
        catch (IOException e)
        {
          logger.error("Unable to upload file,", e);
        }
        result = true;
      }
      break;
    }
    label155:
    logger.warn("Invalid bucket program.");
    return result;
  }
  
  public boolean deleteFromBucket(String bucket, String fileToDelete)
  {
    boolean result = false;
    String str;
    switch ((str = this.bucketProgram).hashCode())
    {
    case -878667098: 
      break;
    case 2624: 
      if (str.equals("S3"))
      {
        result = false;
      }
      break;
    }
    label104:
    logger.warn("Invalid bucket program.");
    return result;
  }
}
