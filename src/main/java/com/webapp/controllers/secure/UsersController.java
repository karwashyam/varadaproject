package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.UsersJsonDto;
import com.webapp.services.UsersService;

@Controller
@RequestMapping("/users")
public class UsersController extends BusinessController{


	@Autowired
	private UsersService usersService;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		
		return "users";
	}

	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<UsersJsonDto> showUsers(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<UsersJsonDto> dt = new DataTablesTO<UsersJsonDto>();
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<UsersJsonDto> accts = usersService.fetchUsers(tableUtils, userId);
		int srNo=1;
		for (UsersJsonDto userJson : accts) {
			userJson.setSrNo(srNo++);
			userJson.setAction("<a href='"+req.getContextPath()+"/users/edit.do?user_id="+userJson.getUser_id()+"'>edit</a>");
			if (userJson.getCreated_at() == 0) {

			} else {
				userJson.setCreated_date(DateUtils.dbTimeStampToSesionDate(userJson.getCreated_at(), "IST","dd/MM/yyyy hh:mm a"));
			}
			
		}
		long count =  usersService.fetchTotalUsers(tableUtils, userId);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);

		return dt;
	}
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/users.js" };
	}
}
