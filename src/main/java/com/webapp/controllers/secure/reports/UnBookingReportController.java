package com.webapp.controllers.secure.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.DateUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.services.BookingService;
import com.webapp.services.ProjectSerivce;

@Controller
@RequestMapping(value="/report")
public class UnBookingReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@Autowired
	BookingService bookingService;
	

	@Value("${tempDirName}")
	private String tempDirName;
	
	private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

	@RequestMapping(value="/unbooking", method = RequestMethod.GET)
	public String unbookingReport(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		System.out.println("\n\t\t bookingdata------------");
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		return "unbooking-report";
		
	}
	
	@RequestMapping(value="/unbooking/export", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody ModelAndView manageusersListExport(Model model, HttpServletRequest req, HttpServletResponse res,
			@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate
			,	@RequestParam("start") String start,@RequestParam("end") String end/*,@RequestParam("searchString") String searchString*/
			) throws ServletException, IOException {

		
		Map<String,Object> inputmap=new HashMap<String,Object>();
		if(!toDate.equals("")){
		long todatelong=DateUtils.getMilesecFromDateStr(toDate, DateUtils.SiMPLE_DATE_FORMAT, DateUtils.GMT);
		long fdate=DateUtils.getMilesecFromDateStr(fromDate, DateUtils.SiMPLE_DATE_FORMAT, DateUtils.GMT);
		
		inputmap.put("fromDate", fdate);
		inputmap.put("toDate", todatelong+MILLIS_PER_DAY-1);
		
		}
	
		int serialNo =Integer.valueOf(start) + 1;

List<Map<String, Object>> aDData= projectSerivce.fetchUnBookedPlotsBYdate(Integer.valueOf(end), Integer.valueOf(start), serialNo, "", "", "%%",inputmap);

		String filePath = req.getSession().getServletContext().getRealPath(tempDirName);
		String fileName ="Unbooked_Plots_"+System.currentTimeMillis()+".xls";
		
		String fileLocation = filePath + "/" + fileName;
		String contentType = "application/vnd.ms-excel";
		inputmap.put("fromDate", fromDate);
		inputmap.put("toDate", toDate);
		generateExcelSheetForUsers(aDData, fileLocation,inputmap);
		WriteFileToStream(res, contentType, fileName, fileLocation);
		return getOutputResponse(res);
	}
	
	private static void generateExcelSheetForUsers(List<Map<String, Object>> newList, String fileLocation, Map<String, Object> inputmap) {

		File file = new File(fileLocation);

		if (file.exists()) {
			file.delete();
		}

		try {

			file.createNewFile();

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Unbooked Plots details");

			HSSFRow headerRow0 = sheet.createRow(0);
			writeCellWithStyle(headerRow0, 0, "From Date", workbook);
			writeCellWithStyle(headerRow0, 1,inputmap.get("fromDate").toString(),workbook);
			writeCellWithStyle(headerRow0, 2, "To Date", workbook);
			writeCellWithStyle(headerRow0, 3, inputmap.get("toDate").toString(), workbook);
			
			HSSFRow headerRow = sheet.createRow(1);
			writeCellWithStyle(headerRow, 0, "S. No", workbook);
			writeCellWithStyle(headerRow, 1, "Project Name", workbook);
			writeCellWithStyle(headerRow, 2, "Plot Name", workbook);
			writeCellWithStyle(headerRow, 3, "Plot Size", workbook);

			int rowIndex = 1;
			int rowIndex2 = 0;
			Iterator<Map<String, Object>> pitr = newList.iterator();
			
			while (pitr.hasNext()) {
				Map<String, Object> uModelMap = pitr.next();

				HSSFRow rows = sheet.createRow(++rowIndex);
				++rowIndex2;
				writeCell(rows, 0,rowIndex2+"");
				writeCell(rows, 1, uModelMap.get("title").toString());
				writeCell(rows, 2, uModelMap.get("plotName").toString());
				writeCell(rows, 3,String.valueOf( uModelMap.get("plotSize")));
				
			}

			for (int i = 0; i < 5; i++) {
				sheet.autoSizeColumn((short) i);
			}

			// lets write the excel data to file now
			FileOutputStream fos;
			fos = new FileOutputStream(fileLocation);
//			fos = new FileOutputStream(new File("D:\\Work\\useracnc212.xls"));

			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println(fileLocation + " written successfully");

	}
	public static void writeCell(HSSFRow row, int cellIndex, String value) {

//		HSSFCell cell = row.createCell((short) cellIndex);
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		HSSFRichTextString richString = new HSSFRichTextString(value);
		cell.setCellValue(richString);
	}

	
	public static void writeCellWithStyle(HSSFRow row, int cellIndex, String value, HSSFWorkbook workbook) {

//		HSSFCell cell = row.createCell((short) cellIndex);
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		HSSFRichTextString richString = new HSSFRichTextString(value);
		cell.setCellValue(richString);

		HSSFCellStyle my_style = workbook.createCellStyle();

		my_style.setBorderTop((short) 6); // double lines border
		my_style.setBorderBottom((short) 1); // single line border
		my_style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

		HSSFFont my_font = workbook.createFont();
		//my_font.setFontHeight((short)20);
		my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		my_style.setFont(my_font);

		cell.setCellStyle(my_style);

	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {
				"js/viewjs/unbooking-report.js",
				"js/vendor/bootstrap-filestyle.min.js"};
	}

	
	
}
