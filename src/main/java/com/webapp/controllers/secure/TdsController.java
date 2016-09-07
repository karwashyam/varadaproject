package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.models.TdsModel;
import com.webapp.services.TdsService;

@Controller
@RequestMapping("/tds")
public class TdsController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private TdsService tdsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		return "tds";
	}
	
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<TdsModel> showOrders(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<TdsModel> dt = new DataTablesTO<TdsModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<TdsModel> accts = tdsService.fetchTdsList(tableUtils);
		for (TdsModel tdsModel : accts) {
			tdsModel.setCreatedAtString(DateUtils.fetchDateStrFromMilisec(tdsModel.getCreatedAt(), "IST", "dd/MM/yyyy"));
			if(tdsModel.getChequeDate()>0){
				tdsModel.setChequeDateString(DateUtils.fetchDateStrFromMilisec(tdsModel.getChequeDate(), "IST", "dd/MM/yyyy"));
			}
			tdsModel.setStatus(tdsModel.getTdsAmount()+" "+tdsModel.getStatus());
		}
		long count =  tdsService.fetchTotalTdsList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@RequestMapping(value = "/tds-due",method = RequestMethod.GET)
	public @ResponseBody ModelAndView tdsDue(Model model, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		Long tdsDue = tdsService.fetchTdsDue();
		Map<String, Object> outputFinalMap= new HashMap<String,Object>();
		if (tdsDue==null) {
			tdsDue = tdsService.fetchTdsCreditDue();
			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", tdsDue);
		}

		return getOutputResponse(outputFinalMap );
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/viewjs/tds.js" };
		//return new String[] { "js/viewjs/city.js" };
	}

	
}
