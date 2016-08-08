package com.webapp.controllers.secure.ajax.state;

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

import com.webapp.controllers.BusinessApiController;
import com.webapp.models.DatatableModel;
import com.webapp.services.SessionService;
import com.webapp.services.StateSerivce;


@Controller
public class StateManagementAjaxController extends BusinessApiController {


	@Autowired
	private SessionService sessionService;

	@Value("${SessionCookieName}")
	private String sessionCookieName;


	@Autowired
	private StateSerivce stateSerivce;

	@RequestMapping(value = "/ajax/states", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody DatatableModel<Map<String, Object> > fetchLessons(@RequestParam int iDisplayStart,
	                                                                       @RequestParam int iDisplayLength,
	                                                                       @RequestParam int sEcho,
	                                                                       @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res){


		int serialNo = iDisplayStart + 1;
		int colNo;
		String iSortCol, columnName = null, sSortDir = null;
		String cols[] = { "", "st.state_name"};

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

		DatatableModel<Map<String, Object> > dtData = new DatatableModel<Map<String, Object> >();
		dtData.setAaData(stateSerivce.fetchStatesList(iDisplayLength, iDisplayStart, serialNo, sSortDir, columnName, sSearch));
		

		dtData.setiTotalDisplayRecords(10);
		dtData.setiTotalRecords(10);
		dtData.setsEcho(sEcho);
		return dtData;
	}



}