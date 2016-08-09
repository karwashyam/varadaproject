package com.webapp.controllers.secure.ajax.state;

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

		List<Map<String, Object> > aDData=stateSerivce.fetchStatesList(iDisplayLength, iDisplayStart, serialNo, sSortDir, columnName, sSearch);
		DatatableModel<Map<String, Object> > dtData = new DatatableModel<Map<String, Object> >();
		dtData.setAaData(aDData);
		

		dtData.setiTotalDisplayRecords(aDData.size());
		dtData.setiTotalRecords(Integer.valueOf(stateSerivce.fetchTotalStatesListCount().toString()));
		dtData.setsEcho(sEcho);
		return dtData;
	}
	

	@RequestMapping(value = "/ajax/state/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView deleteLesson(@RequestParam("stateId") String stateId, HttpServletRequest req, HttpServletResponse res){

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		Map<String, Object> outputFinalMap= new HashMap<String,Object>();

		int status = stateSerivce.deleteStateById(stateId, userId);

		if (status ==0) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );

	}
}