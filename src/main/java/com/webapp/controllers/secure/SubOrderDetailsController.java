package com.webapp.controllers.secure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fnf.utils.DateUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.OrderModel;
import com.webapp.models.SubOrderModel;
import com.webapp.services.OrderService;

@Controller
@RequestMapping("/suborder/edit")
public class SubOrderDetailsController extends BusinessController{


	@Autowired
	private OrderService orderService;

		
	@RequestMapping(method = RequestMethod.GET)
	public String editOrder(@QueryParam("sub_order_id")int sub_order_id,Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		SubOrderModel suborderModel = orderService.fetchSubOrderDetails(sub_order_id);
		model.addAttribute("suborderModel", suborderModel);
		return "suborder-details";
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String postEditOrder(Model model,@Validated SubOrderModel subOrderModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		int status = orderService.updateSubOrders(subOrderModel);
		OrderModel orderModel = orderService.fetchOrderDetails(subOrderModel.getOrder_id());
		if (orderModel.getCreated_at() == 0) {

		} else {
			orderModel.setCreated_date(DateUtils.dbTimeStampToSesionDate(orderModel.getCreated_at(), "IST","dd/MM/yyyy hh:mm a"));
		}
		model.addAttribute("orderModel", orderModel);
		
		if(status>0){
			model.addAttribute("msg", "Sub Order Details Updated Successfully");
		}else{
			model.addAttribute("msg", "Failed to update");
		}
		return "order-details";
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/suborder.js" };
	}
}
