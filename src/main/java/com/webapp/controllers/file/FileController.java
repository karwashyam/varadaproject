package com.webapp.controllers.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javaxt.io.Image;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fnf.utils.FileUtils;
import com.fnf.utils.StringUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ImageUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.dto.FileDto;
import com.webapp.validator.FileValidator;

@Controller
@RequestMapping("/file")
public class FileController extends BusinessController {


	protected static final String IMAGE_UPLOAD_TYPE_AVATAR = "avatar";
	public static final int ERROR_IMAGE_RESOLUTION_GREATER_EQUAL_200_200 = 2009;
	public static final String FILE_TYPE_IMAGE = "IMAGE";
	public static final String ORIGINAL_IMAGE_PREFIX = "ORIGINAL_";
	public static final String THUMB_IMAGE_PREFIX = "THUMB_";
	public static final String ERROR_MSG = "Error";

	@Value("${tempDirName}")
	private String tempDirName;

	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	FileValidator validator;

	private static Logger logger = Logger.getLogger(FileController.class);
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getForm(Model model) {
		FileDto fileModel = new FileDto();
		model.addAttribute("file", fileModel);
		return "file";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fileUploaded(Model model, @Validated FileDto file, BindingResult result, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		
		String fileExtension = "";
		String actualFileName = "";
		String thumbFileName = "";
		String designFileName = "";

		int thumbImageHeight = 0;
		int thumbImageWidth = 0;
		int designImageUIHeight = 0;
		int designImageUIWidth = 0;
		boolean createThumbnail = true;

		File thumbFile = null;
		File designFile = null;

		if (result.hasErrors()) {
			outputMap.put("type", "Error");
			return outputMap;

		} else {

			String tempDirectory = req.getSession().getServletContext().getRealPath(tempDirName);
			MultipartFile multipartFile = file.getFile();

			if (!multipartFile.isEmpty()) {
				try {

					byte[] bytes = multipartFile.getBytes();

					String randomId = UUIDGenerator.generateUUID();
					fileExtension = StringUtils.getExtension(multipartFile.getOriginalFilename());
					actualFileName = randomId + fileExtension;

					File actualFile = new File(tempDirectory, actualFileName);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(actualFile));
					stream.write(bytes);
					stream.close();
					
					thumbImageHeight = ProjectConstant.THUMBNAIL_DESIGN_IMG_HEIGHT;
					thumbImageWidth = ProjectConstant.THUMBNAIL_DESIGN_IMG_WIDTH;

					designImageUIHeight = ProjectConstant.DESIGN_IMG_HEIGHT;
					designImageUIWidth = ProjectConstant.DESIGN_IMG_WIDTH;
					
					if (createThumbnail) {

						thumbFileName = THUMB_IMAGE_PREFIX + randomId + fileExtension;
						thumbFile = new File(tempDirectory, thumbFileName);
						Image thumbImage = new javaxt.io.Image(actualFile);
						if (thumbImage.getHeight() > thumbImageHeight || thumbImage.getWidth() > thumbImageWidth) {

							ImageUtils.changeQualityAndDimension(actualFile, thumbFile, fileExtension,thumbImageWidth, thumbImageHeight );

							fileUtils.uploadToBucket(s3BucketName, thumbFile);

						} else {
							fileUtils.uploadToBucket(s3BucketName, actualFile);
							thumbFileName = actualFileName;
						}

						designFileName = ORIGINAL_IMAGE_PREFIX + randomId + fileExtension;
						designFile = new File(tempDirectory, designFileName);

						if (thumbImage.getHeight() > designImageUIHeight || thumbImage.getWidth() > designImageUIWidth) {

							ImageUtils.changeQualityAndDimension(actualFile, designFile, fileExtension,designImageUIWidth, designImageUIHeight);

							fileUtils.uploadToBucket(s3BucketName, designFile);

						} else {
							designFileName = actualFileName;
						}

					} else {
						thumbFileName = actualFileName;
						designFileName = actualFileName;
					}

//					fileUtils.uploadToBucket(s3BucketName, actualFile);
//
//					if (actualFile != null && actualFile.exists()) {
//						actualFile.delete();
//					}
					if (thumbFile != null && thumbFile.exists()) {
						thumbFile.delete();
					}
					if (designFile != null && designFile.exists()) {
						designFile.delete();
					}

				} catch (Exception e) {
					outputMap.put("type", "Error");
					return outputMap;
				}
			} else {

				outputMap.put("type", "Error");
				return outputMap;
			}
		}

		outputMap.put("type", "success");

		outputMap.put("originalFileName", designFileName + "&bucketName=" + s3BucketName);
		outputMap.put("fileName", actualFileName + "&bucketName=" + s3BucketName);

		outputMap.put("thumbFileName", thumbFileName + "&bucketName=" + s3BucketName);

		return outputMap;

	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFile(@RequestParam("id") String id, @RequestParam("bucketName") String bucketName, HttpServletRequest req, HttpServletResponse res) {

		String filePath = req.getSession().getServletContext().getRealPath(tempDirName);

		File fileToWrite = new File(filePath, id);
		
		if (!fileToWrite.exists()) {
			boolean result = fileUtils.downloadFromBucket(bucketName, id, fileToWrite);
			if (!result) {
				fileToWrite.delete();
			}
		}

		try {
			FileInputStream fileIn = new FileInputStream(fileToWrite);
			ServletOutputStream out = res.getOutputStream();
			
			byte[] outputByte = new byte[4096];
			// copy binary contect to output stream
			while (fileIn.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			fileIn.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		fileToWrite.delete();
	}

}