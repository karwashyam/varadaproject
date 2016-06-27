/*package com.webapp.schedulers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.fnf.utils.CommonUtils;
import com.fnf.utils.DateUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.models.OrderModel;
import com.webapp.services.OrderService;

@Controller
@EnableScheduling
public class OrdersFollowUpScheduler {

	@Autowired
	OrderService orderService;

	@Scheduled(fixedDelay = 60000) 
	public void schedulerMethod() throws ServletException, IOException {

		doFollowUpOfOrders();

	}

	private void doFollowUpOfOrders() {

		System.out.println("inside follow up method");

		List<OrderModel> ordersList = orderService
				.fetchOrdersListByStatus(ProjectConstant.ORDER_STATUS_PAYMENT_CONFIRMATION_AWAITED);


		long currentTime = DateUtils.nowAsGmtMillisec();

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentTime);

		cal.add(Calendar.DATE, -3);
		long timeBeforeThreeDays = cal.getTimeInMillis();

		List<OrderModel> orderModelsList = new ArrayList<OrderModel>();

		for (OrderModel orderModel : ordersList) {

			if (orderModel.getUpdatedAt() >= timeBeforeThreeDays && orderModel.getUpdatedAt() <= currentTime) {

				System.out.println("inside send sms if ");
				String message = "Hello Franchise, ";
				new CommonUtils().sendCustomerNote(orderModel.getUser_id(), message);

				new CommonUtils().sendSms(orderModel.getPhone(), message);

			} else if (orderModel.getUpdatedAt() < timeBeforeThreeDays) {

				System.out.println("inside update order status if ");

				orderModel.setOrder_status(ProjectConstant.ORDER_STATUS_PENDING_PAYMENT);
				orderModel.setUpdatedAt(currentTime);
				orderModelsList.add(orderModel);
			}
		}

		//System.out.println("orderModelsList size = " + orderModelsList.size());
		//System.out.println("orderModelsList = " + orderModelsList);
		
		if (orderModelsList.size() > 0) {
			int updateStatus = orderService.updateOrderStatus(orderModelsList);
			System.out.println("order update status - " + updateStatus);
		}
	}
}*/