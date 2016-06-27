package com.webapp.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.OrderJsonDto;
import com.webapp.dto.SubOrderJsonDto;
import com.webapp.models.OrderModel;
import com.webapp.models.SubOrderModel;
import com.webapp.models1.OrdersHistory;

public interface OrderDao {

	List<OrderJsonDto> fetchOrders(@Param("JQTableUtils") JQTableUtils tableUtils, @Param("userId") String userId);

	long fetchTotalOrders(@Param("JQTableUtils") JQTableUtils tableUtils, @Param("userId") String userId);

	OrderModel fetchOrderDetails(@Param("order_id") int order_id);

	SubOrderModel fetchSubOrderDetails(@Param("sub_order_id") int sub_order_id);

	List<SubOrderJsonDto> fetchSubOrder(@Param("JQTableUtils") JQTableUtils tableUtils,@Param("order_id") int order_id);

	long fetchSubTotalOrders(@Param("JQTableUtils") JQTableUtils tableUtils,@Param("order_id") int order_id);

	int updateOrders(@Param("orderModel") OrderModel orderModel,@Param("updated_at") long nowAsGmtMillisec);

	int updateSubOrders(@Param("subOrderModel") SubOrderModel subOrderModel);

	String getFirstBuyDetails(@Param("user_id") String user_id);

	int addReferralBonus(@Param("referrer_coupon") String referrer_coupon,@Param("user_id")  String user_id);

	int updateFirstBuy(@Param("user_id") String user_id);

	int addOrder(OrderModel orderModel);

	int getOrderId(OrderModel orderModel);

	Map<String, Object> getOrderPrice(@Param("orderId") int order_id);

	List<OrderJsonDto> fetchIncompleteOrders(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchIncompleteTotalOrders(@Param("JQTableUtils") JQTableUtils tableUtils);
	
	List<OrderModel> getOrderDetailsOfUserForTimePeriod(HashMap<String, Object> parametersMap);

	int addCumulativeDiscountOrderHistory(OrderModel orderModel);

	int updateCumulativeDiscountOrderHistory(OrderModel orderModel);

	boolean checkDiscountOrder(OrderModel orderModel);

	List<SubOrderModel> fetchSubOrderDetailsList(@Param("order_id") int order_id);
	
	int insertOrderChangeHistory(OrdersHistory orderChangeHistory);
	
	List<OrdersHistory> getOrderActivityHistoryOfOrder(@Param("JQTableUtils") JQTableUtils tableUtils, @Param("orderId") int orderId);
	
	List<OrderModel> fetchOrdersListByStatus(String orderStatus);
	
	int updateOrderStatus(@Param("orderModelsList") List<OrderModel> orderModelsList);

	int disableCouponForUser(@Param("coupon_id") String coupon_id,@Param("user_id") String user_id,@Param("order_id") int order_id,
			@Param("coupons_disabled_id") String coupons_disabled_id);

}
