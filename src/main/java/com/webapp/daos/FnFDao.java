package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FnFDao {

	Map<String, Object> fetchUserDetails(@Param("userId") String id);

	int addUserDetails(@Param("userId") String valueOf,
			@Param("firstName") String first_name, @Param("time") long time);

	int addFullName(@Param("userId") String id, @Param("fullName") String text);

	int addCity(@Param("userId") String id, @Param("city") String text);

	int addPhone(@Param("userId") String id, @Param("phone") String text);

	int addEmail(@Param("userId") String id, @Param("email") String text);

	String checkUpdate(@Param("update_id") String update_id);

	int addUpdate(@Param("update_id") String valueOf,
			@Param("userId") String valueOf2, @Param("message") String text,
			@Param("time") long time);

	List<Map<String, Object>> getProductTypes();

	int addUserQueries(@Param("id") String generateUUID,
			@Param("userId") String valueOf, @Param("message") String text,
			@Param("time") long time);

	Map<String, Object> getProductCode(@Param("userId") String valueOf);

	List<Map<String, Object>> getProductImage(
			@Param("productCode") String productCode,
			@Param("priceRange") int priceRange, @Param("offset") int offset);

	List<Map<String, Object>> getProductImagesHIgherPrice(
			@Param("productCode") String productCode,
			@Param("priceRange") int priceRange, @Param("offset") int offset);

	int updateSaree(@Param("userId") String valueOf, @Param("b") boolean b);

	int updateGown(@Param("userId") String valueOf, @Param("b") boolean b);

	int updateKurti(@Param("userId") String valueOf, @Param("b") boolean b);

	int updateLehenga(@Param("userId") String valueOf, @Param("b") boolean b);

	int updateDressMaterial(@Param("userId") String valueOf,
			@Param("b") boolean b);

	int updatePriceRange(@Param("userId") String valueOf, @Param("i") int i);

	int updateCatalogueDelivery(@Param("userId") String valueOf,
			@Param("delivery") String delivery);

	int updateUpdateFrequency(@Param("userId") String valueOf,
			@Param("delivery") String delivery);

	int addUserQueriesPrice(@Param("userId") String valueOf, @Param("i") int i,
			@Param("count") int count);

	Map<String, Object> getUserQueries(@Param("userId") String valueOf);

	int addNewOrder(@Param("userId") String valueOf, @Param("time") long time);

	boolean getActiveOrderStatus(@Param("userId") String valueOf);

	Map<String, Object> getProductDetails(@Param("product") String product);

	boolean checkProduct(@Param("product") String product);

	String getOrderId(@Param("userId") String valueOf);

	int addOrderProducts(@Param("orderId") int orderId,
			@Param("productId") String productId, @Param("price") int price,
			@Param("discountedPrice") int discountedPrice,
			@Param("weight") int weight);

	int cancelNewOrder(@Param("userId") String valueOf, @Param("time") long time);

	int addAddress(@Param("userId") String valueOf, @Param("time") long time);

	int addCouponRecordStatus(@Param("userId") String valueOf,
			@Param("time") long time);

	String getRecordStatus(@Param("userId") String valueOf);

	int addOrderAddressToMobile(@Param("userId") String valueOf,
			@Param("time") long time, @Param("address") String text);

	int addOrderMobile(@Param("userId") String valueOf,
			@Param("time") long time, @Param("phone") String text);

	Map<String, Object> getOrderPrice(@Param("userId") String valueOf);

	int completeOrder(@Param("order_id") int order_id,
			@Param("total_price") int total_price,
			@Param("total_discounted_price") int total_discounted_price,
			@Param("total_courier_charge") int total_courier_charge,
			@Param("total") int total, @Param("time") long time,
			@Param("referral_discount") Integer referral_discount,
			@Param("coupon_discount") Integer coupon_discount,
			@Param("internet_handling_charge") int internet_handling_charge,
			@Param("online_payment") int online_payment,
			@Param("cod_payment") int cod_payment,
			@Param("cod_charge") int cod_charge,
			@Param("courier_method") String courier_method,
			@Param("payment_method") String payment_method);

	List<Map<String, Object>> getOrderProducts(@Param("order_id") int order_id);

	int confirmProducts(@Param("userId") String valueOf);

	int getCourierCharge(@Param("total_weight") int total_weight);

	List<Map<String, Object>> getOrderHistory(@Param("userId") String valueOf);

	List<String> getAllUserId();

	int addReferralCodeAndWallet(@Param("userId") String valueOf,
			@Param("refercode") String refercode, @Param("wallet") int wallet);

	boolean checkReferralCode(@Param("refercode") String text);

	int addReferralHistory(@Param("id") String generateUUID,
			@Param("userId") String valueOf,
			@Param("user_used") String user_used,
			@Param("order_id") Integer order_id, @Param("amount") int amount,
			@Param("history_note") String history_note,
			@Param("time") long nowAsGmtMillisec);

	List<Map<String, Object>> getActiveCouponWithReferral(
			@Param("time") long nowAsGmtMillisec);

	List<Map<String, Object>> getAllActiveCoupon(
			@Param("time") long nowAsGmtMillisec);

	Map<String, Object> getCouponDetailsFromCouponCode(
			@Param("coupon") String text, @Param("time") long nowAsGmtMillisec,@Param("user_id") String string);

	int addCouponCode(@Param("coupon_id") String coupon_id,
			@Param("userId") String valueOf);

	Map<String, Object> getCouponDetailsFromCouponId(
			@Param("coupon_id") String coupon_id);

	int updateReferralBalance(@Param("referral_balance") int i,
			@Param("userId") String valueOf);

	List<Map<String, Object>> getWalletHistory(@Param("user_id") String valueOf);

	int addCourierMethod(@Param("courier_method") String courierMethod,@Param("user_id") String valueOf);

	int addPaymentMethod(@Param("payment_method") String payment_method,@Param("user_id") String valueOf);

	int backToMobile(@Param("user_id") String valueOf);

	List<String> getAllPhone();

	int noOfTimesUsed(@Param("user_id") String valueOf,@Param("coupon_id")  String coupon_id);

	boolean isCouponBlocked(@Param("user_id") String valueOf,@Param("coupon_id")  String coupon_id);

	void unsubscribeDailyUpdate(@Param("userId") String valueOf,@Param("saree") boolean saree,@Param("gown") boolean gown,
			@Param("kurti") boolean kurti, @Param("lehenga") boolean lehenga,@Param("dressMaterial") boolean dressMaterial);

	boolean isDeactivated(@Param("userId") String userId);

}
