package com.webapp.controllers.secure;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.ProjectDto;
import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPlotsModel;
import com.webapp.services.ProjectSerivce;
import com.webapp.validator.ProjectValidator;

@Controller
@RequestMapping(value="/project")
public class ProjectController extends BusinessController{

	@Autowired
	private ProjectValidator projectValidator;
//	
	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	ProjectSerivce projectSerivce;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(projectValidator);
	}
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
//		ProjectModel projectModel=new ProjectModel();
		ProjectDto projectModel=new ProjectDto();
		model.addAttribute("projectModel", projectModel);
		return "add-projects";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(Model model,@Validated ProjectDto projectModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		ProjectModel projectModel1=new ProjectModel();
		long currentTime = new Date().getTime();

		long completeDate = DateUtils.getMilesecFromDateStr(projectModel.getCompletionDate(), DateUtils.SiMPLE_DATE_FORMAT, DateUtils.GMT);

			if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
				String url ="/login.do";
				return "redirect:" + url;
			}
			if (result.hasErrors()) {
				model.addAttribute("projectModel", projectModel);
				return "add-projects";
			}
			DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
			String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

			model.addAttribute("projectModel",projectModel);
			String projectId = UUIDGenerator.generateUUID();

			MultipartFile f = projectModel.getExcelFile();
			String tempDirectory = req.getSession().getServletContext().getRealPath(tempDirName);

			File excelFile = new File(tempDirectory, projectModel.getExcelFile().getOriginalFilename());
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(excelFile));
			stream.write(f.getBytes());
			stream.close();
		
			FileInputStream mreview = new FileInputStream(excelFile);
			XSSFWorkbook metricWorkbook = new XSSFWorkbook(mreview);
			XSSFSheet metricSheet = metricWorkbook.getSheetAt(0);
			Iterator<Row> rowIterator = metricSheet.rowIterator();
			
			ProjectPlotsModel projectPlotsModel=null;
			List<ProjectPlotsModel> projectPlotsModelsList=new ArrayList<ProjectPlotsModel>();
			
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				projectPlotsModel=new ProjectPlotsModel();
				Cell firstCol=row.getCell(0);
				firstCol.setCellType(Cell.CELL_TYPE_STRING);
				Cell secCol=row.getCell(1);

				if(row.getRowNum()>0){
//					Double plot = Double.parseDouble(firstCol.toString());
					projectPlotsModel.setPlotName(String.valueOf(firstCol.getStringCellValue()));
					Double d = Double.parseDouble(secCol.toString());
					Long pSizze= d.longValue();
//					long pSizze=Long.parseLong(secCol.toString());
					projectPlotsModel.setPlotSize(pSizze);
					projectPlotsModel.setProjectPlotId(UUIDGenerator.generateUUID());
//					projectPlotsModel.setPlotName(String.valueOf(i));
					projectPlotsModel.setProjectId(projectId);
//					projectPlotsModel.setPlotSize(projectModel.getPlotSize());
					projectPlotsModel.setCreatedAt(currentTime);
					projectPlotsModel.setCreatedBy(userId);
					projectPlotsModel.setUpdatedAt(currentTime);
					projectPlotsModel.setUpdatedBy(userId);
					projectPlotsModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);

					projectPlotsModelsList.add(projectPlotsModel);

				}
				
			}
			projectModel1.setProjectId(projectId);
			projectModel1.setCompletionDate(completeDate);
			projectModel1.setBookingPrefix(projectModel.getBookingPrefix());
			projectModel1.setTitle(projectModel.getTitle());
//			projectModel1.setPlotSize(projectModel.getPlotSize());
			projectModel1.setProjectOverview(projectModel.getProjectOverview());
			projectModel1.setTotalPlots(projectModel.getTotalPlots());
			projectModel1.setSuperBuildupPercentage(projectModel.getSuperBuildupPercentage());
			
	
	

		projectSerivce.addProject(projectModel1,userId,projectPlotsModelsList);

		return pageRedirect("/projects.do");
	}
	
	@RequestMapping(value = "/edit/{projectId}", method = RequestMethod.GET)
	public String editForm(Model model, @PathVariable("projectId") String projectId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		System.out.println("\n\t projectId=="+projectId);


		ProjectModel projectModel = projectSerivce.getProjectDetailsById(projectId);

		List<ProjectPlotsModel> projPlotsList=projectSerivce.fetchProjectPlots(projectId);
		System.out.println("\n\t projPlotsList=="+projPlotsList.size());
		ProjectDto projectModelDto=new ProjectDto();
		projectModelDto.setProjectId(projectId);
		projectModelDto.setTitle(projectModel.getTitle());
		projectModelDto.setProjectOverview(projectModel.getProjectOverview());
		projectModelDto.setBookingPrefix(projectModel.getBookingPrefix());
		projectModelDto.setTotalPlots(projectModel.getTotalPlots());
		projectModelDto.setCompletionDate(DateUtils.fetchDateStrFromMilisec(projectModel.getCompletionDate(), DateUtils.GMT, DateUtils.SiMPLE_DATE_FORMAT));
		projectModelDto.setSuperBuildupPercentage(projectModel.getSuperBuildupPercentage());
		
		projectModelDto.setProjPlotsList(projPlotsList);
		model.addAttribute("editProjectFrm", projectModelDto);
		
		model.addAttribute("title", projectModel.getTitle());
		model.addAttribute("projectId", projectModel.getProjectId());
		model.addAttribute("bookingPrefix", projectModel.getBookingPrefix());
		model.addAttribute("projectOverview", projectModel.getProjectOverview());
		model.addAttribute("totalPlots", projectModel.getTotalPlots());
		model.addAttribute("completionDate", projectModel.getCompletionDate());

		model.addAttribute("superBuildupPercentage", projectModel.getSuperBuildupPercentage());
		model.addAttribute("projPlotsList", projPlotsList);

		
		return "edit-project";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editFormPost(Model model, ProjectModel projectModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		/*if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_ADD)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}*/

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		projectSerivce.editProject(projectModel,userId);

		return pageRedirect("/projects.do");
	}
	
	@RequestMapping(value = "/editplot/{projectId}", method = RequestMethod.GET)
	public String editPlotForm(Model model, @PathVariable("projectId") String projectId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
		System.out.println("\n\t projectId=="+projectId);


		ProjectModel projectModel = projectSerivce.getProjectDetailsById(projectId);

		List<ProjectPlotsModel> projPlotsList=projectSerivce.fetchProjectPlots(projectId);
		System.out.println("\n\t projPlotsList=="+projPlotsList.size());
		ProjectDto projectModelDto=new ProjectDto();
		projectModelDto.setProjectId(projectId);
		
		projectModelDto.setProjPlotsList(projPlotsList);
		
		model.addAttribute("projPlotsList", projPlotsList);

		
		return "project-plots";
	}
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js","js/bootstrap/bootstrap-dialog.js", "js/viewjs/add-project.js","js/vendor/bootstrap-filestyle.min.js",
				"js/vendor/dataTables.editor.min.js","dataTables.select.min.js"};
	}


	
	
}
