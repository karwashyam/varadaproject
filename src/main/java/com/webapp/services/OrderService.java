package com.webapp.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.daos.OrderDao;
import com.webapp.dto.OrderJsonDto;
import com.webapp.dto.SubOrderJsonDto;
import com.webapp.models.OrderModel;
import com.webapp.models.SubOrderModel;
import com.webapp.models1.OrdersHistory;

@Service("orderService")
public class OrderService {

	protected static Logger logger = Logger.getLogger(FnFTelegramService.class);

	@Autowired
	private OrderDao orderDao;

	public List<OrderJsonDto> fetchOrders(JQTableUtils tableUtils, String userId) {

		return orderDao.fetchOrders(tableUtils, userId);
	}

	public long fetchTotalOrders(JQTableUtils tableUtils, String userId) {
		return orderDao.fetchTotalOrders(tableUtils, userId);
	}

	public List<SubOrderJsonDto> fetchSubOrder(JQTableUtils tableUtils, String userId,int order_id) {

		return orderDao.fetchSubOrder(tableUtils,order_id);
	}

	public long fetchSubTotalOrders(JQTableUtils tableUtils, String userId,int order_id) {
		return orderDao.fetchSubTotalOrders(tableUtils,order_id);
	}

	public OrderModel fetchOrderDetails(int order_id) {
		return orderDao.fetchOrderDetails(order_id);
	}

	public SubOrderModel fetchSubOrderDetails(int sub_order_id) {
		return orderDao.fetchSubOrderDetails(sub_order_id);
	}

	public int updateOrders(OrderModel orderModel) {
		return orderDao.updateOrders(orderModel,DateUtils.nowAsGmtMillisec());
	}

	public int updateSubOrders(SubOrderModel subOrderModel) {
		return orderDao.updateSubOrders(subOrderModel);
	}

	public String getFirstBuyDetails(String user_id) {
		return orderDao.getFirstBuyDetails(user_id);
	}

	public int addReferralBonus(String referrer_coupon, String user_id) {
		return orderDao.addReferralBonus(referrer_coupon,user_id);
	}

	public int updateFirstBuy(String user_id) {
		return orderDao.updateFirstBuy(user_id);
		
	}

	public int addOrder(OrderModel orderModel) {
	    return orderDao.addOrder(orderModel);
		
	}

	public int getOrderId(OrderModel orderModel) {
		return orderDao.getOrderId(orderModel);
	}

	public Map<String, Object> getOrderPrice(int order_id) {
		return orderDao.getOrderPrice(order_id);
	}

	public List<OrderJsonDto> fetchIncompleteOrders(JQTableUtils tableUtils,
			String userId) {
		return orderDao.fetchIncompleteOrders(tableUtils);
	}

	public long fetchIncompleteTotalOrders(JQTableUtils tableUtils,
			String userId) {
		return orderDao.fetchIncompleteTotalOrders(tableUtils);
	}
	
	public List<OrderModel> getOrderDetailsOfUserForTimePeriod(HashMap<String, Object> parametersMap)
	{
	return orderDao.getOrderDetailsOfUserForTimePeriod(parametersMap);
	}

	public int addCumulativeDiscountOrderHistory(OrderModel orderModel)
	{
	return orderDao.addCumulativeDiscountOrderHistory(orderModel);
	}

	public int updateCumulativeDiscountOrderHistory(OrderModel orderModel)
	{
	return orderDao.updateCumulativeDiscountOrderHistory(orderModel);
	}

	public boolean checkDiscountOrder(OrderModel orderModel) {
		return orderDao.checkDiscountOrder(orderModel);
	}

	public List<SubOrderModel> fetchSubOrderDetailsList(int order_id) {
		return orderDao.fetchSubOrderDetailsList(order_id);
	}
	
	public int insertOrderChangeHistory(OrdersHistory orderChangeHistory) {
		return orderDao.insertOrderChangeHistory(orderChangeHistory);
	}
	
	public List<OrdersHistory> getOrderActivityHistoryOfOrder(JQTableUtils tableUtils, int orderId) {
		return orderDao.getOrderActivityHistoryOfOrder(tableUtils, orderId);
	}
	
	public List<OrderModel> fetchOrdersListByStatus(String orderStatus)
	{
		return orderDao.fetchOrdersListByStatus(orderStatus);
	}
	
	public int updateOrderStatus(List<OrderModel> orderModelsList)
	{
		return orderDao.updateOrderStatus(orderModelsList);
	}

	public int disableCouponForUser(String coupon_id, String user_id,
			int order_id, String disabledCouponId) {
		return orderDao.disableCouponForUser(coupon_id,user_id,order_id,disabledCouponId);
	}
}