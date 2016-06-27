package com.utils.constant;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.imgscalr.Scalr;

//import org.apache.log4j.Logger;

public class ImageUtils {
	//	private static final Logger logger = Logger.getLogger(ImageUtils.class);

	public static void saveScaledImage(String filePath, String outputFile, String tempDirectory) {
		try {

			BufferedImage sourceImage = ImageIO.read(new File(tempDirectory, filePath));
			int width = sourceImage.getWidth();
			int height = sourceImage.getHeight();

			if (width > height) {
				float extraSize = height - 100;
				float percentHight = (extraSize / height) * 100;
				float percentWidth = width - ((width / 100) * percentHight);
				BufferedImage img = new BufferedImage((int) percentWidth, 100, BufferedImage.TYPE_INT_RGB);
				Image scaledImage = sourceImage.getScaledInstance((int) percentWidth, 100, Image.SCALE_SMOOTH);
				img.createGraphics().drawImage(scaledImage, 0, 0, null);
				BufferedImage img2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				img2 = img.getSubimage((int) ((percentWidth - 100) / 2), 0, 100, 100);

				ImageIO.write(img2, "jpg", new File(tempDirectory, outputFile));
			} else {
				float extraSize = width - 100;
				float percentWidth = (extraSize / width) * 100;
				float percentHight = height - ((height / 100) * percentWidth);
				BufferedImage img = new BufferedImage(100, (int) percentHight, BufferedImage.TYPE_INT_RGB);
				Image scaledImage = sourceImage.getScaledInstance(100, (int) percentHight, Image.SCALE_SMOOTH);
				img.createGraphics().drawImage(scaledImage, 0, 0, null);
				BufferedImage img2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				img2 = img.getSubimage(0, (int) ((percentHight - 100) / 2), 100, 100);

				ImageIO.write(img2, "jpg", new File(tempDirectory, outputFile));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void resizeImage(File srcPath, File destPath, String fileExtension) {

		float quality = 0.001f;
		//float quality =0.0 to 1.0
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(fileExtension.replaceFirst(".", ""));

		ImageWriter writer = (ImageWriter) iter.next();

		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

		iwp.setCompressionQuality(quality);

		try {

			BufferedImage srcImg = ImageIO.read(srcPath);
			BufferedImage dstImg = Scalr.resize(srcImg, Scalr.Method.QUALITY, 400, 400);
			ImageIO.write(dstImg, "jpg", destPath);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static void changeQualityAndDimension(File srcPath, File destPath, String fileExtension,int width, int height) {

		// change  quality of thumbnail image and dimension  


		try {

			BufferedImage srcImg = ImageIO.read(srcPath);
			BufferedImage dstImg = Scalr.resize(srcImg, Scalr.Method.ULTRA_QUALITY, width, height);
			ImageIO.write(dstImg, fileExtension.replaceFirst(".", ""), destPath);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static void changeQualityOfImageOnly(File srcPath, File destPath, String fileExtension) {

		// change  quality of thumbnail image only, No change in dimension  

		float quality = 0.7f;
		//float quality =0.0 to 1.0
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(fileExtension.replaceFirst(".", ""));

		ImageWriter writer = (ImageWriter) iter.next();

		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

		iwp.setCompressionQuality(quality);

		try {

			FileImageOutputStream output = new FileImageOutputStream(destPath);
			writer.setOutput(output);

			FileInputStream inputStream = new FileInputStream(srcPath);
			BufferedImage originalImage = ImageIO.read(inputStream);

			IIOImage image = new IIOImage(originalImage, null, null);
			writer.write(null, image, iwp);

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (writer != null) {
				writer.dispose();
			}
		}

	}

//	public static javaxt.io.Image createHighQualityThumbnailImage(javaxt.io.Image image, int width, int height) {
//
//		// create high quality thumbnail image with given dimension 
//
//		Integer imgWidth = image.getWidth();
//		Integer imgHeight = image.getHeight();
//		Double imgRatio = (imgWidth.doubleValue() / imgHeight.doubleValue());
//
//		if (imgRatio >= 2) {
//			image.setWidth(width - 1);
//
//		} else if (imgRatio < 1) {
//			image.setHeight(300);
//
//		} else {
//			Double expectedHeight = (imgRatio * (height / ProjectConstant.THUMBNAIL_IMG_ASPECT_RATIO));
//			image.setHeight(expectedHeight.intValue());
//
//			if (image.getWidth() > width) {
//				image.setWidth(width - 20);
//			}
//		}
//
//		return image;
//	}

	public static void copyFile(File savedFile, File thumbNailFile) {

		try {
			InputStream in = new FileInputStream(savedFile);
			OutputStream out = new FileOutputStream(thumbNailFile);

			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
