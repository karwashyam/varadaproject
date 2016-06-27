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
import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.models1.OrdersHistory;
import com.webapp.services.OrderService;

@Controller
@RequestMapping("/order/activity")
public class OrderActivityController extends BusinessController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	public String displayOrderActivityHistory(@RequestParam("order_id") int orderId, Model model, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		model.addAttribute("orderId", orderId);

		return "order-activity";
	}

	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<OrdersHistory> fetchOrderActivityHistory(@RequestParam int sEcho,
			@RequestParam("orderId") String orderId, @RequestParam String sSearch, HttpServletRequest req,
			HttpServletResponse res) {
		
		DataTablesTO<OrdersHistory> dt = new DataTablesTO<OrdersHistory>();
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");

		//DbSession dbSession = DbSession.getSession(req, res, sessionService, se1ssionCookieName, false);
		//String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		int order_id = Integer.parseInt(orderId);
		List<OrdersHistory> orderActivityList = orderService.getOrderActivityHistoryOfOrder(tableUtils, order_id);
		long srNo=1;
		for (OrdersHistory ordersHistory : orderActivityList) {
			ordersHistory.setActivityDate(DateUtils.dbTimeStampToSesionDate(ordersHistory.getCreatedAt(),
					ProjectConstant.TIMEZONE_IST, ProjectConstant.DATE_FORMAT_DD_MONTH_YYYY_HH_MM_A));
			ordersHistory.setSrNo(srNo);
		}
		
		//long count = orderService.fetchSubTotalOrders(tableUtils, userId, order_id);
		long count = orderActivityList.size();
		
		dt.setAaData(orderActivityList); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);

		return dt;
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/order-activity.js" };
	}
}