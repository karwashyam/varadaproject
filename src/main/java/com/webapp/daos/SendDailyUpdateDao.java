package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SendDailyUpdateDao {


	List<String> getProductNames(@Param("catalogueId") String catalogueId);

	List<String> getUserIdListForCategory(@Param("productType") String productType);

	List<Map<String,Object>> getProductImagesForCatalogue(@Param("catalogueId") String catalogueId,@Param("productType") String productType);

}
