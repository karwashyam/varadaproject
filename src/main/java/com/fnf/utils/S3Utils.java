package com.fnf.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;

public class S3Utils
{
  protected static Logger logger = Logger.getLogger(S3Utils.class);
  private S3Service s3Service;
  
  public S3Utils() {}
  
  public S3Utils(RestS3Service s3Service)
  {
    this.s3Service = s3Service;
  }
  
  public void upload(String bucket, File fileToUpload)
    throws IOException, S3ServiceException, NoSuchAlgorithmException
  {
    if (fileToUpload.exists())
    {
      S3Object s3FileObject = new S3Object(fileToUpload);
      S3Bucket s3Bucket = this.s3Service.getBucket(bucket);
      this.s3Service.putObject(s3Bucket, s3FileObject);
    }
    else
    {
      throw new IOException(fileToUpload.getAbsolutePath() + " does not exists");
    }
  }
  
  /* Error */
  public void download(String bucket, String nameOfFileToDownload, File fileToWrite)
    throws IOException, ServiceException
  {
	  	S3Object objectComplete = this.s3Service.getObject(bucket, nameOfFileToDownload);
	    try
	    {
	      InputStream inputStream = new BufferedInputStream(objectComplete.getDataInputStream());
	      FileOutputStream outputStream = 
                  new FileOutputStream(fileToWrite);
	      if (inputStream != null) {
			    int read = 0;
				byte[] bytes = new byte[1024];
		
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				inputStream.close();
				outputStream.close();
		    }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	com/webapp/fileutils/S3Utils:s3Service	Lorg/jets3t/service/S3Service;
    //   4: aload_1
    //   5: aload_2
    //   6: invokevirtual 99	org/jets3t/service/S3Service:getObject	(Ljava/lang/String;Ljava/lang/String;)Lorg/jets3t/service/model/S3Object;
    //   9: astore 4
    //   11: aconst_null
    //   12: astore 5
    //   14: aconst_null
    //   15: astore 6
    //   17: new 103	java/io/BufferedInputStream
    //   20: dup
    //   21: aload 4
    //   23: invokevirtual 105	org/jets3t/service/model/S3Object:getDataInputStream	()Ljava/io/InputStream;
    //   26: invokespecial 109	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   29: astore 5
    //   31: new 112	java/io/BufferedOutputStream
    //   34: dup
    //   35: new 114	java/io/FileOutputStream
    //   38: dup
    //   39: aload_3
    //   40: invokespecial 116	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   43: invokespecial 117	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   46: astore 6
    //   48: sipush 1024
    //   51: newarray <illegal type>
    //   53: astore 7
    //   55: iconst_m1
    //   56: istore 8
    //   58: goto +13 -> 71
    //   61: aload 6
    //   63: aload 7
    //   65: iconst_0
    //   66: iload 8
    //   68: invokevirtual 120	java/io/BufferedOutputStream:write	([BII)V
    //   71: aload 5
    //   73: aload 7
    //   75: invokevirtual 124	java/io/BufferedInputStream:read	([B)I
    //   78: dup
    //   79: istore 8
    //   81: iconst_m1
    //   82: if_icmpgt -21 -> 61
    //   85: goto +33 -> 118
    //   88: astore 9
    //   90: aload 5
    //   92: ifnull +8 -> 100
    //   95: aload 5
    //   97: invokevirtual 128	java/io/BufferedInputStream:close	()V
    //   100: aload 6
    //   102: ifnull +13 -> 115
    //   105: aload 6
    //   107: invokevirtual 131	java/io/BufferedOutputStream:flush	()V
    //   110: aload 6
    //   112: invokevirtual 134	java/io/BufferedOutputStream:close	()V
    //   115: aload 9
    //   117: athrow
    //   118: aload 5
    //   120: ifnull +8 -> 128
    //   123: aload 5
    //   125: invokevirtual 128	java/io/BufferedInputStream:close	()V
    //   128: aload 6
    //   130: ifnull +13 -> 143
    //   133: aload 6
    //   135: invokevirtual 131	java/io/BufferedOutputStream:flush	()V
    //   138: aload 6
    //   140: invokevirtual 134	java/io/BufferedOutputStream:close	()V
    //   143: return
    // Line number table:
    //   Java source line #66	-> byte code offset #0
    //   Java source line #68	-> byte code offset #11
    //   Java source line #69	-> byte code offset #14
    //   Java source line #72	-> byte code offset #17
    //   Java source line #73	-> byte code offset #31
    //   Java source line #75	-> byte code offset #48
    //   Java source line #77	-> byte code offset #55
    //   Java source line #78	-> byte code offset #58
    //   Java source line #79	-> byte code offset #61
    //   Java source line #78	-> byte code offset #71
    //   Java source line #83	-> byte code offset #85
    //   Java source line #84	-> byte code offset #90
    //   Java source line #85	-> byte code offset #95
    //   Java source line #88	-> byte code offset #100
    //   Java source line #89	-> byte code offset #105
    //   Java source line #90	-> byte code offset #110
    //   Java source line #92	-> byte code offset #115
    //   Java source line #84	-> byte code offset #118
    //   Java source line #85	-> byte code offset #123
    //   Java source line #88	-> byte code offset #128
    //   Java source line #89	-> byte code offset #133
    //   Java source line #90	-> byte code offset #138
    //   Java source line #93	-> byte code offset #143
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	S3Utils
    //   0	144	1	bucket	String
    //   0	144	2	nameOfFileToDownload	String
    //   0	144	3	fileToWrite	File
    //   9	13	4	objectComplete	S3Object
    //   12	112	5	inputStream	BufferedInputStream
    //   15	124	6	ouputStream	java.io.BufferedOutputStream
    //   53	21	7	buffer	byte[]
    //   56	24	8	bytesRead	int
    //   88	28	9	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   17	88	88	finally
  }
  
  /* Error */
  public void downloadInputStream(String bucket, String nameOfFileToDownload)
    throws IOException, ServiceException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	com/webapp/fileutils/S3Utils:s3Service	Lorg/jets3t/service/S3Service;
    //   4: aload_1
    //   5: aload_2
    //   6: invokevirtual 99	org/jets3t/service/S3Service:getObject	(Ljava/lang/String;Ljava/lang/String;)Lorg/jets3t/service/model/S3Object;
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: new 103	java/io/BufferedInputStream
    //   16: dup
    //   17: aload_3
    //   18: invokevirtual 105	org/jets3t/service/model/S3Object:getDataInputStream	()Ljava/io/InputStream;
    //   21: invokespecial 109	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   24: astore 4
    //   26: goto +18 -> 44
    //   29: astore 5
    //   31: aload 4
    //   33: ifnull +8 -> 41
    //   36: aload 4
    //   38: invokevirtual 128	java/io/BufferedInputStream:close	()V
    //   41: aload 5
    //   43: athrow
    //   44: aload 4
    //   46: ifnull +8 -> 54
    //   49: aload 4
    //   51: invokevirtual 128	java/io/BufferedInputStream:close	()V
    //   54: aload 4
    //   56: areturn
    // Line number table:
    //   Java source line #97	-> byte code offset #0
    //   Java source line #99	-> byte code offset #10
    //   Java source line #101	-> byte code offset #13
    //   Java source line #103	-> byte code offset #26
    //   Java source line #104	-> byte code offset #31
    //   Java source line #105	-> byte code offset #36
    //   Java source line #108	-> byte code offset #41
    //   Java source line #104	-> byte code offset #44
    //   Java source line #105	-> byte code offset #49
    //   Java source line #109	-> byte code offset #54
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	S3Utils
    //   0	57	1	bucket	String
    //   0	57	2	nameOfFileToDownload	String
    //   9	9	3	objectComplete	S3Object
    //   11	44	4	inputStream	BufferedInputStream
    //   29	13	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   13	29	29	finally
  }
  
  /* Error */
  public void download(String bucket, String nameOfFileToDownload, java.io.OutputStream outputStreamToWrite)
    throws IOException, ServiceException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	com/webapp/fileutils/S3Utils:s3Service	Lorg/jets3t/service/S3Service;
    //   4: aload_1
    //   5: aload_2
    //   6: invokevirtual 99	org/jets3t/service/S3Service:getObject	(Ljava/lang/String;Ljava/lang/String;)Lorg/jets3t/service/model/S3Object;
    //   9: astore 4
    //   11: aconst_null
    //   12: astore 5
    //   14: aconst_null
    //   15: astore 6
    //   17: new 103	java/io/BufferedInputStream
    //   20: dup
    //   21: aload 4
    //   23: invokevirtual 105	org/jets3t/service/model/S3Object:getDataInputStream	()Ljava/io/InputStream;
    //   26: invokespecial 109	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   29: astore 5
    //   31: new 112	java/io/BufferedOutputStream
    //   34: dup
    //   35: aload_3
    //   36: invokespecial 117	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   39: astore 6
    //   41: sipush 1024
    //   44: newarray <illegal type>
    //   46: astore 7
    //   48: iconst_m1
    //   49: istore 8
    //   51: goto +13 -> 64
    //   54: aload 6
    //   56: aload 7
    //   58: iconst_0
    //   59: iload 8
    //   61: invokevirtual 120	java/io/BufferedOutputStream:write	([BII)V
    //   64: aload 5
    //   66: aload 7
    //   68: invokevirtual 124	java/io/BufferedInputStream:read	([B)I
    //   71: dup
    //   72: istore 8
    //   74: iconst_m1
    //   75: if_icmpgt -21 -> 54
    //   78: goto +33 -> 111
    //   81: astore 9
    //   83: aload 5
    //   85: ifnull +8 -> 93
    //   88: aload 5
    //   90: invokevirtual 128	java/io/BufferedInputStream:close	()V
    //   93: aload 6
    //   95: ifnull +13 -> 108
    //   98: aload 6
    //   100: invokevirtual 131	java/io/BufferedOutputStream:flush	()V
    //   103: aload 6
    //   105: invokevirtual 134	java/io/BufferedOutputStream:close	()V
    //   108: aload 9
    //   110: athrow
    //   111: aload 5
    //   113: ifnull +8 -> 121
    //   116: aload 5
    //   118: invokevirtual 128	java/io/BufferedInputStream:close	()V
    //   121: aload 6
    //   123: ifnull +13 -> 136
    //   126: aload 6
    //   128: invokevirtual 131	java/io/BufferedOutputStream:flush	()V
    //   131: aload 6
    //   133: invokevirtual 134	java/io/BufferedOutputStream:close	()V
    //   136: return
    // Line number table:
    //   Java source line #113	-> byte code offset #0
    //   Java source line #115	-> byte code offset #11
    //   Java source line #116	-> byte code offset #14
    //   Java source line #119	-> byte code offset #17
    //   Java source line #120	-> byte code offset #31
    //   Java source line #122	-> byte code offset #41
    //   Java source line #124	-> byte code offset #48
    //   Java source line #125	-> byte code offset #51
    //   Java source line #126	-> byte code offset #54
    //   Java source line #125	-> byte code offset #64
    //   Java source line #130	-> byte code offset #78
    //   Java source line #131	-> byte code offset #83
    //   Java source line #132	-> byte code offset #88
    //   Java source line #135	-> byte code offset #93
    //   Java source line #136	-> byte code offset #98
    //   Java source line #137	-> byte code offset #103
    //   Java source line #139	-> byte code offset #108
    //   Java source line #131	-> byte code offset #111
    //   Java source line #132	-> byte code offset #116
    //   Java source line #135	-> byte code offset #121
    //   Java source line #136	-> byte code offset #126
    //   Java source line #137	-> byte code offset #131
    //   Java source line #140	-> byte code offset #136
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	137	0	this	S3Utils
    //   0	137	1	bucket	String
    //   0	137	2	nameOfFileToDownload	String
    //   0	137	3	outputStreamToWrite	java.io.OutputStream
    //   9	13	4	objectComplete	S3Object
    //   12	105	5	inputStream	BufferedInputStream
    //   15	117	6	ouputStream	java.io.BufferedOutputStream
    //   46	21	7	buffer	byte[]
    //   49	24	8	bytesRead	int
    //   81	28	9	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   17	81	81	finally
  }
  
  public void download(String bucket, String nameOfFileToDownload, InputStream inputStream)
    throws IOException, ServiceException
  {
    S3Object objectComplete = this.s3Service.getObject(bucket, nameOfFileToDownload);
    try
    {
      inputStream = new BufferedInputStream(objectComplete.getDataInputStream());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}

