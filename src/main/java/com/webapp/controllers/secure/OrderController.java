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
import com.webapp.dto.OrderJsonDto;
import com.webapp.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends BusinessController{


	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(@RequestParam(required = false) String userId, Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		
		return "order";
	}

	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<OrderJsonDto> showOrders(@RequestParam(required = false) String userId, @RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<OrderJsonDto> dt = new DataTablesTO<OrderJsonDto>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		if("".equalsIgnoreCase(userId)){
			userId=null;
		}
		List<OrderJsonDto> accts = orderService.fetchOrders(tableUtils, userId);
		for (OrderJsonDto orderJson : accts) {
			orderJson.setAction("<a href='"+req.getContextPath()+"/order/edit.do?order_id="+orderJson.getOrder_id()+"'>edit</a>");
			if (orderJson.getCreated_at() == 0) {

			} else {
				orderJson.setCreated_date(DateUtils.dbTimeStampToSesionDate(orderJson.getCreated_at(), "IST","dd/MM/yyyy hh:mm a"));
			}
			
		}
		
		long count =  orderService.fetchTotalOrders(tableUtils, userId);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);

		return dt;
	}
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/order.js" };
	}
}
