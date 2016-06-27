package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models1.Products;

public interface ProductsDao {

	List<Products> fetchProductsList(@Param("JQTableUtils") JQTableUtils tableUtils);
	
	long fetchTotalProducts(@Param("JQTableUtils") JQTableUtils tableUtils);
	
	Products getProductDetails(String productId);
	
	List<Map<String, Object>> getAllProductTypes();
	
	int updateProduct(Products product);
}