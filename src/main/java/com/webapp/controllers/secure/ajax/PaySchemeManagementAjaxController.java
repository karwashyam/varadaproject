package com.webapp.controllers.secure.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.controllers.BusinessApiController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.DatatableModel;
import com.webapp.services.PaymentSchemeSerivce;
import com.webapp.services.ProjPaymentSchemeSerivce;
import com.webapp.services.SessionService;


@Controller
public class PaySchemeManagementAjaxController extends BusinessApiController {


	@Autowired
	private SessionService sessionService;


	@Value("${SessionCookieName}")
	private String sessionCookieName;


	@Autowired
	private PaymentSchemeSerivce paymentSchemeSerivce;

	@Autowired
	private ProjPaymentSchemeSerivce projPaymentSchemeSerivce;

	
	@RequestMapping(value = "/ajax/paymentScheme", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody DatatableModel<Map<String, Object> > fetchLessons(@RequestParam int iDisplayStart,
	                                                                       @RequestParam int iDisplayLength,
	                                                                       @RequestParam int sEcho,
	                                                                       @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res){


		int serialNo = iDisplayStart + 1;
		int colNo;
		String iSortCol, columnName = null, sSortDir = null;
		String cols[] = { "", "p.title"};

		sSearch = "%" + sSearch + "%";

		if (req.getParameter("sSortDir_0") != null) {
			sSortDir = req.getParameter("sSortDir_0");
		}

		if (req.getParameter("iSortCol_0") != null) {
			iSortCol = req.getParameter("iSortCol_0");
			if (!iSortCol.trim().equalsIgnoreCase("")) {
				colNo = Integer.parseInt(iSortCol);
				if (cols.length > 0) {
					columnName = cols[colNo];
				}
			}
		}

		List<Map<String, Object> > aDData=paymentSchemeSerivce.fetchPaymentSchemeList(iDisplayLength, iDisplayStart, serialNo, sSortDir, columnName, sSearch);
		DatatableModel<Map<String, Object> > dtData = new DatatableModel<Map<String, Object> >();
		dtData.setAaData(aDData);
		
		int totalRecords= Integer.valueOf(paymentSchemeSerivce.fetchTotalPaymentSchemeListCount().toString());
		dtData.setiTotalDisplayRecords(totalRecords);
		dtData.setiTotalRecords(totalRecords);
		dtData.setsEcho(sEcho);
		return dtData;
	}
	

	@RequestMapping(value = "/ajax/paymentScheme/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView deleteLesson(@RequestParam("paymentScemeId") String paymentScemeId, HttpServletRequest req, HttpServletResponse res){

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		Map<String, Object> outputFinalMap= new HashMap<String,Object>();

		int status = paymentSchemeSerivce.deletePaymentScheme(paymentScemeId, userId);

		if (status ==0) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );

	}
	
	
	@RequestMapping(value = "/ajax/projpaymentScheme", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody DatatableModel<Map<String, Object> > fetchProjPaymentScheme(@RequestParam int iDisplayStart,
	                                                                       @RequestParam int iDisplayLength,
	                                                                       @RequestParam int sEcho,
	                                                                       @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res){


		int serialNo = iDisplayStart + 1;
		int colNo;
		String iSortCol, columnName = null, sSortDir = null;
		String cols[] = { "", "project_title"};

		sSearch = "%" + sSearch + "%";

		if (req.getParameter("sSortDir_0") != null) {
			sSortDir = req.getParameter("sSortDir_0");
		}

		if (req.getParameter("iSortCol_0") != null) {
			iSortCol = req.getParameter("iSortCol_0");
			if (!iSortCol.trim().equalsIgnoreCase("")) {
				colNo = Integer.parseInt(iSortCol);
				if (cols.length > 0) {
					columnName = cols[colNo];
				}
			}
		}

		List<Map<String, Object> > aDData=projPaymentSchemeSerivce.fetchProjPaymentSchemeList(iDisplayLength, iDisplayStart, serialNo, sSortDir, columnName, sSearch);
		DatatableModel<Map<String, Object> > dtData = new DatatableModel<Map<String, Object> >();
		dtData.setAaData(aDData);
		
		int totalRecords= Integer.valueOf(projPaymentSchemeSerivce.fetchTotalProjPaymentSchemeListCount().toString());
		dtData.setiTotalDisplayRecords(totalRecords);
		dtData.setiTotalRecords(totalRecords);
		dtData.setsEcho(sEcho);
		return dtData;
	}
	

	@RequestMapping(value = "/ajax/projpaymentScheme/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView deleteProjPayScheme(@RequestParam("projpaymentScemeId") String projpaymentScemeId, HttpServletRequest req, HttpServletResponse res){

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		Map<String, Object> outputFinalMap= new HashMap<String,Object>();

		int status = projPaymentSchemeSerivce.deleteProjectPaymentScheme(projpaymentScemeId, userId);

		if (status ==0) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );

	}
}