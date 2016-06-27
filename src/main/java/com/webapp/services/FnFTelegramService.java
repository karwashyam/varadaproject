package com.webapp.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.daos.FnFDao;

@Service("fnFTelegramService")
public class FnFTelegramService {

	protected static Logger logger = Logger.getLogger(FnFTelegramService.class);

	@Autowired
	private FnFDao fnFDao;
	
	public Map<String, Object> fetchUserDetails(int id) {
		
		return  fnFDao.fetchUserDetails(String.valueOf(id));
	}

	public Map<String, Object> fetchUserDetailsByUserId(String userId) {
		
		return  fnFDao.fetchUserDetails(userId);
	}

	public int addUserDetails(Integer id, String first_name) {
		
		return  fnFDao.addUserDetails(String.valueOf(id),first_name,DateUtils.nowAsGmtMillisec());
	}

	public int addFullName(Integer id, String text) {
		return  fnFDao.addFullName(String.valueOf(id),text);
	}

	public int addCity(Integer id, String text) {
		return  fnFDao.addCity(String.valueOf(id),text);
	}

	public int addEmail(Integer id, String text) {
		return  fnFDao.addEmail(String.valueOf(id),text);
	}

	public String checkUpdate(int update_id) {
		return fnFDao.checkUpdate(String.valueOf(update_id)); 
	}
	
	@Transactional
	public int addUpdate(Integer id, int update_id, String text) {
		String status= fnFDao.checkUpdate(String.valueOf(update_id)); 
		if(status==null || "".equalsIgnoreCase(status)){
			return fnFDao.addUpdate(String.valueOf(update_id),String.valueOf(id),text,DateUtils.nowAsGmtMillisec());
		}
		return 0;
		
	}

	public List<Map<String, Object>> getProductTypes() {
		return fnFDao.getProductTypes(); 
	}


	public int addUserQueries(Integer id, String text) {
		return fnFDao.addUserQueries(UUIDGenerator.generateUUID(),String.valueOf(id),text,DateUtils.nowAsGmtMillisec());
	}

	public Map<String, Object> getProductCode(Integer id) {
		return fnFDao.getProductCode(String.valueOf(id));
	}

	public List<Map<String, Object>> getProductImages(String productCode, int priceRange, int offset) {
		return fnFDao.getProductImage(productCode,priceRange,offset);
	}

	public List<Map<String, Object>> getProductImagesHIgherPrice(String productCode, int priceRange, int offset) {
		return fnFDao.getProductImagesHIgherPrice(productCode,priceRange,offset);
	}

	public int updateSaree(Integer id, boolean b) {
		return fnFDao.updateSaree(String.valueOf(id),b);
	}

	public int updateGown(Integer id, boolean b) {
		return fnFDao.updateGown(String.valueOf(id),b);
	}

	public int updateKurti(Integer id, boolean b) {
		return fnFDao.updateKurti(String.valueOf(id),b);
	}

	public int updateLehenga(Integer id, boolean b) {
		return fnFDao.updateLehenga(String.valueOf(id),b);
	}

	public int updateDressMaterial(Integer id, boolean b) {
		return fnFDao.updateDressMaterial(String.valueOf(id),b);
	}

	public int updatePriceRange(Integer id, int i) {
		return fnFDao.updatePriceRange(String.valueOf(id),i);
	}

	public int updateCatalogueDelivery(Integer id, String string) {
		return fnFDao.updateCatalogueDelivery(String.valueOf(id),string);
	}

	public int updateUpdateFrequency(Integer id, String string) {
		return fnFDao.updateUpdateFrequency(String.valueOf(id),string);
	}

	public int addUserQueriesPrice(Integer id, int priceRange, int i) {
		return fnFDao.addUserQueriesPrice(String.valueOf(id),priceRange,i);
	}

	public Map<String, Object> getUserQueries(Integer id) {
		return fnFDao.getUserQueries(String.valueOf(id));
	}

	public int addNewOrder(Integer id) {
		return fnFDao.addNewOrder(String.valueOf(id),DateUtils.nowAsGmtMillisec());
	}

	public boolean getActiveOrderStatus(Integer id) {
		return fnFDao.getActiveOrderStatus(String.valueOf(id));
	}

	public Map<String,Object> getProductDetails(String product) {
		return fnFDao.getProductDetails(product);
	}

	public boolean checkProduct(String product) {
		return fnFDao.checkProduct(product);
	}

	public String getOrderId(Integer id) {
		return fnFDao.getOrderId(String.valueOf(id));
		
	}

	public int addOrderProducts(int orderId, String productId, int price, int weight) {
		return fnFDao.addOrderProducts(orderId,productId,price,price/2,weight);
	}

	public int cancelNewOrder(Integer id) {
		return fnFDao.cancelNewOrder(String.valueOf(id),DateUtils.nowAsGmtMillisec());
	}

	public int addAddress(Integer id) {
		return fnFDao.addAddress(String.valueOf(id),DateUtils.nowAsGmtMillisec());
	}
	public String getRecordStatus(Integer id) {
		return fnFDao.getRecordStatus(String.valueOf(id));
	}

	public int addOrderAddressToMobile(Integer id, String text) {
		return fnFDao.addOrderAddressToMobile(String.valueOf(id),DateUtils.nowAsGmtMillisec(),text);
	}

	public int addOrderMobile(Integer id, String text) {
		return fnFDao.addOrderMobile(String.valueOf(id),DateUtils.nowAsGmtMillisec(),text);
	}

	public Map<String, Object> getOrderPrice(Integer id) {
		return fnFDao.getOrderPrice(String.valueOf(id));
	}

	public int completeOrder(int order_id, int total_price, int total_discounted_price, int total_courier_charge, int total, Integer referral_discount, Integer coupon_discount,int internet_handling_charge,int online_payment,
			int cod_payment,int cod_charge,String courier_method,String payment_method) {
		return fnFDao.completeOrder(order_id,total_price,total_discounted_price,total_courier_charge,total,DateUtils.nowAsGmtMillisec(),referral_discount,coupon_discount,internet_handling_charge,online_payment,
				cod_payment,cod_charge,courier_method,payment_method); 
	}

	public List<Map<String, Object>> getOrderProducts(int order_id) {
		return fnFDao.getOrderProducts(order_id);
	}

	public int confirmProducts(Integer id) {
		return fnFDao.confirmProducts(String.valueOf(id));
	}

	public int getCourierCharge(int total_weight) {
		return fnFDao.getCourierCharge(total_weight);
	}

	public List<Map<String, Object>> getOrderHistory(Integer id) {
		return fnFDao.getOrderHistory(String.valueOf(id));
	}

	public int addPhone(Integer id, String text) {
		return  fnFDao.addPhone(String.valueOf(id),text);
	}


	public List<String> getAllUserId() {
		return fnFDao.getAllUserId();
	}
	
	public List<String> getAllPhone() {
		return fnFDao.getAllPhone();
	}

	public int addReferralCodeAndWallet(Integer id, String refercode, int wallet) {
		return fnFDao.addReferralCodeAndWallet(String.valueOf(id),refercode,wallet);
	}

	public boolean checkReferralCode(String text) {
		return fnFDao.checkReferralCode(text);
	}

	public int addReferralHistory(Integer id, String user_used,
			Integer order_id, int amount, String history_note) {
		return fnFDao.addReferralHistory(UUIDGenerator.generateUUID(),String.valueOf(id),user_used,order_id,amount,history_note,DateUtils.nowAsGmtMillisec());
		
	}

	public int addCouponRecordStatus(Integer id) {
		return fnFDao.addCouponRecordStatus(String.valueOf(id),DateUtils.nowAsGmtMillisec());
	}

	public List<Map<String, Object>> getActiveCouponWithReferral() {
		return fnFDao.getActiveCouponWithReferral(DateUtils.nowAsGmtMillisec());
	}

	public List<Map<String, Object>> getAllActiveCoupon() {
		return fnFDao.getAllActiveCoupon(DateUtils.nowAsGmtMillisec());
	}

	public Map<String, Object> getCouponDetailsFromCouponCode(Integer id, String text) {
		return fnFDao.getCouponDetailsFromCouponCode(text,DateUtils.nowAsGmtMillisec(),String.valueOf(id));
	}

	public int addCouponCode(String coupon_id, Integer id) {
		return fnFDao.addCouponCode(coupon_id,String.valueOf(id));
		
	}

	public Map<String, Object> getCouponDetailsFromCouponId(String coupon_id) {
		return fnFDao.getCouponDetailsFromCouponId(coupon_id);
	}

	public int updateReferralBalance(Integer id, int i) {
		return fnFDao.updateReferralBalance(i,String.valueOf(id));
		// TODO Auto-generated method stub
		
	}

	public List<Map<String, Object>> getWalletHistory(Integer id) {
		return fnFDao.getWalletHistory(String.valueOf(id));
	}

	public int addCourierMethod(Integer id, String text) {
		return fnFDao.addCourierMethod(text,String.valueOf(id));
	}

	public int addPaymentMethod(Integer id, String text) {
		return fnFDao.addPaymentMethod(text,String.valueOf(id));
	}

	public int backToMobile(Integer id) {
		return fnFDao.backToMobile(String.valueOf(id));
	}

	public int noOfTimesUsed(Integer id, String coupon_id) {
		return fnFDao.noOfTimesUsed(String.valueOf(id),coupon_id);
	}

	public boolean isCouponBlocked(Integer id, String coupon_id) {
		return fnFDao.isCouponBlocked(String.valueOf(id),coupon_id);
	}

	public void unsubscribeDailyUpdate(Integer id, boolean saree, boolean gown,
			boolean kurti, boolean lehenga, boolean dressMaterial) {
		 fnFDao.unsubscribeDailyUpdate(String.valueOf(id),saree,gown,kurti,lehenga,dressMaterial);
		
	}

	public boolean isDeactivated(Integer id) {
		return fnFDao.isDeactivated(String.valueOf(id));
	}
}
