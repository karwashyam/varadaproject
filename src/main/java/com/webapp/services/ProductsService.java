package com.webapp.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.ProductsDao;
import com.webapp.models1.Products;

@Service("productsService")
public class ProductsService {

	protected static Logger logger = Logger.getLogger(ProductsService.class);
	
	@Autowired
	ProductsDao productsDao;
	
	public List<Products> fetchProductsList(JQTableUtils tableUtils)
	{
		return productsDao.fetchProductsList(tableUtils);
	}
	
	public long fetchTotalProducts(JQTableUtils tableUtils)
	{
		return productsDao.fetchTotalProducts(tableUtils);
	}
	
	public Products getProductDetails(String productId)
	{
		return productsDao.getProductDetails(productId);
	}
	
	public List<Map<String, Object>> getAllProductTypes()
	{
		return productsDao.getAllProductTypes();
	}
	
	public int updateProduct(Products product)
	{
		return productsDao.updateProduct(product);
	}
}