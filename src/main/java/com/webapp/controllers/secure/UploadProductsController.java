package com.webapp.controllers.secure;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaxt.io.Image;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

import com.fnf.utils.DateUtils;
import com.fnf.utils.FileUtils;
import com.fnf.utils.UUIDGenerator;
import com.google.gson.Gson;
import com.utils.constant.ImageUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.AddProductModel;
import com.webapp.models.ExcelDto;
import com.webapp.models.PhotoSize;
import com.webapp.models.PostUpdateModel;
import com.webapp.models.ProductTagModel;
import com.webapp.models.TagModel;
import com.webapp.services.FnFTelegramService;
import com.webapp.services.UploadService;
import com.webapp.validator.UploadValidator;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/upload")
public class UploadProductsController extends BusinessController{

	private static final String URL = ProjectConstant.FNFSAREESBOT;

	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private FnFTelegramService fnFTelegramService;
	
	@Autowired
	private UploadService uploadService;

	@Autowired
	private UploadValidator uploadValidator;
	
	@Autowired
	private FileUtils fileUtils;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(uploadValidator);
	}
	
	protected static final String IMAGE_UPLOAD_TYPE_AVATAR = "avatar";
	public static final int ERROR_IMAGE_RESOLUTION_GREATER_EQUAL_200_200 = 2009;
	public static final String FILE_TYPE_IMAGE = "IMAGE";
	public static final String ORIGINAL_IMAGE_PREFIX = "ORIGINAL_";
	public static final String THUMB_IMAGE_PREFIX = "THUMB_";
	public static final String ERROR_MSG = "Error";
	
	private static final String INTRICATE = "113680761";
	private static final String ASHISH = "103686419";
	private static final String SACHIN = "62421877";
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		ExcelDto excelDto = new ExcelDto();
		model.addAttribute("excelDto", excelDto);

		return "home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String uploads(Model model,@Validated ExcelDto excelDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		model.addAttribute("excelDto", excelDto);
		if (result.hasErrors()) {
			return "home";
		}
		MultipartFile f = excelDto.getExcelName();
		String tempDirectory = req.getSession().getServletContext().getRealPath(tempDirName);
		File excelFile = new File(tempDirectory, excelDto.getExcelName().getOriginalFilename());
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(excelFile));
		stream.write(f.getBytes());
		stream.close();
		Map<String,File> productImages=new HashMap<String,File>();
		File[] productImage = new File[excelDto.getImages().size()];
		int i = 0;

		for (MultipartFile productMultipart : excelDto.getImages()) {
			if (productMultipart != null && !productMultipart.isEmpty()) {
				productImage[i] = new File(tempDirectory, productMultipart.getOriginalFilename());
				BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(productImage[i]));
				stream1.write(productMultipart.getBytes());
				stream1.close();
				String productName= productMultipart.getOriginalFilename().toLowerCase().replace(".jpg","");
				productImages.put(productName.trim(), productImage[i]);
				i++;
			}
			
		}
		
		FileInputStream mreview = new FileInputStream(excelFile);
		XSSFWorkbook metricWorkbook = new XSSFWorkbook(mreview);
		XSSFSheet metricSheet = metricWorkbook.getSheetAt(0);
		Iterator<Row> rowIterator = metricSheet.rowIterator();
		int excel = 0;
		int recordUpdated = 0;
		List<AddProductModel> listAddProducts = new ArrayList<AddProductModel>();
		List<AddProductModel> listUpdateProducts = new ArrayList<AddProductModel>();
		List<ProductTagModel> listProductTag = new ArrayList<ProductTagModel>();
		List<String> listProduct = new ArrayList<String>();
		
		List<String> listProductIndb = uploadService.fetchProductsName();
		List<TagModel> tagModelIndb = uploadService.fetchTags();
		Map<String,String> tagNamesId= new HashMap<String,String>();
		for (TagModel tagModel : tagModelIndb) {
			tagNamesId.put(tagModel.getTagName(), tagModel.getTagId());
		}
		String imagesError = "";
		String message="Product uploading started";
		if(true){
			sendAdminsUpdate( message, INTRICATE);
		}
		if(true){
			sendAdminsUpdate( message, ASHISH);
		}
		if(true){
			sendAdminsUpdate( message, SACHIN);
		}
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (excel > 0) {
				Cell productNoCell = row.getCell(0);
				if(productNoCell==null){
					model.addAttribute("msg", "Product number is empty for row no."+(excel+1));
					return "home";
				}
				Cell productTypeCell = row.getCell(4);
				if(productTypeCell==null){
					model.addAttribute("msg", "Product type is empty for row no."+(excel+1));
					return "home";
				}
				Cell tagsCell = row.getCell(5);
				if(tagsCell==null){
					model.addAttribute("msg", "Tags is empty for row no."+(excel+1));
					return "home";
				}
				Cell weightCell = row.getCell(14);
				if(weightCell==null){
					model.addAttribute("msg", "Weight is empty for row no."+(excel+1));
					return "home";
				}
				Cell quantityCell = row.getCell(16);
				if(quantityCell==null){
					model.addAttribute("msg", "Quantity is empty for row no."+(excel+1));
					return "home";
				}
				Cell priceCell = row.getCell(20);
				if(priceCell==null){
					model.addAttribute("msg", "Price is empty for row no."+(excel+1));
					return "home";
				}
				productNoCell.setCellType(Cell.CELL_TYPE_STRING);
				productTypeCell.setCellType(Cell.CELL_TYPE_STRING);
				quantityCell.setCellType(Cell.CELL_TYPE_STRING);
				priceCell.setCellType(Cell.CELL_TYPE_STRING);
				weightCell.setCellType(Cell.CELL_TYPE_STRING);
				tagsCell.setCellType(Cell.CELL_TYPE_STRING);
				Cell captionCell = null;
				String captionValue=null;
				if(row.getCell(9)!=null){
					captionCell=row.getCell(9);
					captionCell.setCellType(Cell.CELL_TYPE_STRING);
					captionValue=captionCell.getStringCellValue();
				}
				
				MultipartEntity builder = new MultipartEntity();
				//if (productImages[excel - 1] != null && productImages[excel - 1].length() > 1) {
				if (productImages.containsKey(productNoCell.getStringCellValue().toLowerCase())) {
					//boolean valid = uploadService.checkProduct(productNoCell.getStringCellValue().trim());
					if (!listProductIndb.contains(productNoCell.getStringCellValue().trim())) {
						@SuppressWarnings("resource")
						HttpClient httpClient = new DefaultHttpClient();
						HttpPost httpPost = new HttpPost(URL + "sendPhoto");
						builder.addPart("chat_id", new StringBody("91734453", "text/plain", Charset.forName("UTF-8")));
						builder.addPart("photo", new FileBody(productImages.get(productNoCell.getStringCellValue().toLowerCase())));
						httpPost.setEntity(builder);
						HttpResponse response = httpClient.execute(httpPost);
						String json = EntityUtils.toString(response.getEntity());
						PostUpdateModel posts = new Gson().fromJson(json, PostUpdateModel.class);
						//				int i = posts.getResult().getPhoto().length;
						//				int count = 0;
						PhotoSize photoSize = posts.getResult().getPhoto()[posts.getResult().getPhoto().length - 1];
						
						//upload to s3 bucket
						String randomId = UUIDGenerator.generateUUID();
						String fileExtension = "";
						String actualFileName = randomId + fileExtension;
						String thumbFileName = "";
						String designFileName = "";
						File actualFile=productImages.get(productNoCell.getStringCellValue().toLowerCase());
						int thumbImageHeight = 0;
						int thumbImageWidth = 0;
						int designImageUIHeight = 0;
						int designImageUIWidth = 0;
						boolean createThumbnail = true;
						
						File thumbFile = null;
						File designFile = null;
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

						}
						
						
						AddProductModel addProductModel = new AddProductModel();
						addProductModel.setProductUrlOriginal(designFileName);
						addProductModel.setProductUrlThumb(thumbFileName);
						if (productTypeCell.getStringCellValue().equalsIgnoreCase("Saree")) {
							addProductModel.setProductType("1");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Dress Material")) {
							addProductModel.setProductType("2");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Kurti")) {
							addProductModel.setProductType("3");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Lehenga")) {
							addProductModel.setProductType("4");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Gown")) {
							addProductModel.setProductType("5");
						}
						addProductModel.setCaption(captionValue);
						addProductModel.setPrice(Double.parseDouble(priceCell.getStringCellValue().trim()));
						addProductModel.setProductNo(productNoCell.getStringCellValue().trim());
						addProductModel.setQuantity(Integer.parseInt(quantityCell.getStringCellValue().trim()));
						addProductModel.setProductPic(photoSize.getFile_id());
						addProductModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
						addProductModel.setWeight(Integer.parseInt(weightCell.getStringCellValue().trim()));
						listAddProducts.add(addProductModel);
						String tags=tagsCell.getStringCellValue().trim();
						if(tags!=null && !"".equalsIgnoreCase(tags)){
							String[] tagArray = tags.split(",");
							for (String tag : tagArray) {
								//String tagId=uploadService.getTagId(tag.trim().toLowerCase());
								if(!tagNamesId.containsKey(tag.trim().toLowerCase())){
									TagModel tagModel = new TagModel();
									tagModel.setTagId(UUIDGenerator.generateUUID());
									tagModel.setTagName(tag.trim().toLowerCase());
									uploadService.addTag(tagModel);
									tagNamesId.put(tagModel.getTagName(), tagModel.getTagId());
									ProductTagModel productTagModel = new ProductTagModel();
									productTagModel.setProductTagId(UUIDGenerator.generateUUID());
									productTagModel.setTagId(tagModel.getTagId());
									productTagModel.setProductId(productNoCell.getStringCellValue().trim());
									listProductTag.add(productTagModel);
								}else{
									ProductTagModel productTagModel = new ProductTagModel();
									productTagModel.setProductTagId(UUIDGenerator.generateUUID());
									productTagModel.setTagId(tagNamesId.get(tag.trim().toLowerCase()));
									productTagModel.setProductId(productNoCell.getStringCellValue().trim());
									listProductTag.add(productTagModel);
								}
							}
						}
					} else {
						@SuppressWarnings("resource")
						HttpClient httpClient = new DefaultHttpClient();
						HttpPost httpPost = new HttpPost(URL + "sendPhoto");
						builder.addPart("chat_id", new StringBody("91734453", "text/plain", Charset.forName("UTF-8")));
						builder.addPart("photo", new FileBody(productImages.get(productNoCell.getStringCellValue().toLowerCase())));
						httpPost.setEntity(builder);
						HttpResponse response = httpClient.execute(httpPost);
						String json = EntityUtils.toString(response.getEntity());
						PostUpdateModel posts = new Gson().fromJson(json, PostUpdateModel.class);
						//				int i = posts.getResult().getPhoto().length;
						//				int count = 0;
						PhotoSize photoSize = posts.getResult().getPhoto()[posts.getResult().getPhoto().length - 1];
						
						//upload to s3 bucket
						String randomId = UUIDGenerator.generateUUID();
						String fileExtension = "";
						String actualFileName = randomId + fileExtension;
						String thumbFileName = "";
						String designFileName = "";
						File actualFile=productImages.get(productNoCell.getStringCellValue().toLowerCase());
						int thumbImageHeight = 0;
						int thumbImageWidth = 0;
						int designImageUIHeight = 0;
						int designImageUIWidth = 0;
						boolean createThumbnail = true;
						
						File thumbFile = null;
						File designFile = null;
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

						}
						
						
						AddProductModel addProductModel = new AddProductModel();
						addProductModel.setProductUrlOriginal(designFileName);
						addProductModel.setProductUrlThumb(thumbFileName);
						if (productTypeCell.getStringCellValue().equalsIgnoreCase("Saree")) {
							addProductModel.setProductType("1");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Dress Material")) {
							addProductModel.setProductType("2");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Kurti")) {
							addProductModel.setProductType("3");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Lehenga")) {
							addProductModel.setProductType("4");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Gown")) {
							addProductModel.setProductType("5");
						}
						addProductModel.setCaption(captionValue);
						addProductModel.setPrice(Double.parseDouble(priceCell.getStringCellValue().trim()));
						addProductModel.setProductNo(productNoCell.getStringCellValue().trim());
						addProductModel.setQuantity(Integer.parseInt(quantityCell.getStringCellValue().trim()));
						addProductModel.setProductPic(photoSize.getFile_id());
						addProductModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
						addProductModel.setWeight(Integer.parseInt(weightCell.getStringCellValue().trim()));
						listUpdateProducts.add(addProductModel);
						String tags=tagsCell.getStringCellValue().trim();
						if(tags!=null && !"".equalsIgnoreCase(tags)){
							String[] tagArray = tags.split(",");
							for (String tag : tagArray) {
								if(!tagNamesId.containsKey(tag.trim().toLowerCase())){
									TagModel tagModel = new TagModel();
									tagModel.setTagId(UUIDGenerator.generateUUID());
									tagModel.setTagName(tag.trim().toLowerCase());
									uploadService.addTag(tagModel);
									tagNamesId.put(tagModel.getTagName(), tagModel.getTagId());
									ProductTagModel productTagModel = new ProductTagModel();
									productTagModel.setProductTagId(UUIDGenerator.generateUUID());
									productTagModel.setTagId(tagModel.getTagId());
									productTagModel.setProductId(productNoCell.getStringCellValue().trim());
									listProductTag.add(productTagModel);
								}else{
									ProductTagModel productTagModel = new ProductTagModel();
									productTagModel.setProductTagId(UUIDGenerator.generateUUID());
									productTagModel.setTagId(tagNamesId.get(tag.trim().toLowerCase()));
									productTagModel.setProductId(productNoCell.getStringCellValue().trim());
									listProductTag.add(productTagModel);
								}
							}
						}
						listProduct.add(productNoCell.getStringCellValue().trim());
					}
					recordUpdated++;
				} else {
					if (listProductIndb.contains(productNoCell.getStringCellValue().trim())) {
						AddProductModel addProductModel = new AddProductModel();
						if (productTypeCell.getStringCellValue().equalsIgnoreCase("Saree")) {
							addProductModel.setProductType("1");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Dress Material")) {
							addProductModel.setProductType("2");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Kurti")) {
							addProductModel.setProductType("3");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Lehenga")) {
							addProductModel.setProductType("4");
						} else if (productTypeCell.getStringCellValue().equalsIgnoreCase("Gown")) {
							addProductModel.setProductType("5");
						}
						addProductModel.setCaption(captionValue);
						addProductModel.setPrice(Double.parseDouble(priceCell.getStringCellValue().trim()));
						addProductModel.setProductNo(productNoCell.getStringCellValue().trim());
						addProductModel.setQuantity(Integer.parseInt(quantityCell.getStringCellValue().trim()));
						addProductModel.setCreatedAt(DateUtils.nowAsGmtMillisec());
						addProductModel.setWeight(Integer.parseInt(weightCell.getStringCellValue().trim()));
						listUpdateProducts.add(addProductModel);
						String tags=tagsCell.getStringCellValue().trim();
						if(tags!=null && !"".equalsIgnoreCase(tags)){
							String[] tagArray = tags.split(",");
							for (String tag : tagArray) {
								if(!tagNamesId.containsKey(tag.trim().toLowerCase())){
									TagModel tagModel = new TagModel();
									tagModel.setTagId(UUIDGenerator.generateUUID());
									tagModel.setTagName(tag.trim().toLowerCase());
									uploadService.addTag(tagModel);
									tagNamesId.put(tagModel.getTagName(), tagModel.getTagId());
									ProductTagModel productTagModel = new ProductTagModel();
									productTagModel.setProductTagId(UUIDGenerator.generateUUID());
									productTagModel.setTagId(tagModel.getTagId());
									productTagModel.setProductId(productNoCell.getStringCellValue().trim());
									listProductTag.add(productTagModel);
								}else{
									ProductTagModel productTagModel = new ProductTagModel();
									productTagModel.setProductTagId(UUIDGenerator.generateUUID());
									productTagModel.setTagId(tagNamesId.get(tag.trim().toLowerCase()));
									productTagModel.setProductId(productNoCell.getStringCellValue().trim());
									listProductTag.add(productTagModel);
								}
							}
						}
						listProduct.add(productNoCell.getStringCellValue().trim());
						recordUpdated++;
					} else {
						imagesError +=  productNoCell.getStringCellValue().trim() + " \n";
					}
				}
			}
			excel++;

		}
		excelFile.delete();
		for (File products : productImage) {
			if (products != null) {
				products.delete();
			}
		}
		String outputString="";
		int status = 0;
		if (listAddProducts.size() > 0) {
			status = uploadService.addProducts(listAddProducts);
			outputString += listAddProducts.size()+" new products added. <br>";
		}
		if (listUpdateProducts.size() > 0) {
			status = uploadService.updateProducts(listUpdateProducts);
			outputString += listUpdateProducts.size()+" products updated. <br>";
		}
		if(listProduct.size()>0){
			status = uploadService.deleteProductTag(listProduct);
		}
		if (listProductTag.size() > 0) {
			status = uploadService.addProductTag(listProductTag);
		}

		excel--;
		if (status > 0) {
			
			model.addAttribute("msg", outputString/*+recordUpdated + " products uploaded successfully"*/);
			model.addAttribute("msg1","No images for products : " +imagesError);
		} else {
			model.addAttribute("msg1","No images for products : " +imagesError);
			model.addAttribute("msg", "Failed to upload products");
		}
			if(true){
				sendAdminsUpdate( outputString, INTRICATE);
			}
			if(true){
				sendAdminsUpdate( outputString, ASHISH);
			}
			if(true){
				sendAdminsUpdate( outputString, SACHIN);
			}
		return "home";
	}
	
	private void sendAdminsUpdate(String message, String chatId) throws MalformedURLException, IOException,
			ProtocolException, JsonGenerationException, JsonMappingException,
			JsonParseException {
		URL url = new URL(URL + "sendMessage");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		ObjectMapper mapper = new ObjectMapper();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		data1.put("chat_id", chatId);
		data1.put("text", message);
		mapper.writeValue(wr, data1);
		int responseCode = conn.getResponseCode();
		InputStream in = null;
		if(responseCode==200){
			in = conn.getInputStream();
		}
		mapper.readValue(in,
				PostUpdateModel.class);
		in.close();
	}
}
