package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.daos.UploadDao;
import com.webapp.models.AddProductModel;
import com.webapp.models.ProductTagModel;
import com.webapp.models.TagModel;

@Service("uploadService")
public class UploadService {

	protected static Logger logger = Logger.getLogger(UploadService.class);

	@Autowired
	private UploadDao uploadDao;
	
	public int updateProducts(List<AddProductModel> listUpdateProducts) {
		return uploadDao.updateProducts(listUpdateProducts);
	}
	
	public int addProducts(List<AddProductModel> listAddProducts) {
		return uploadDao.addProducts(listAddProducts);
		
	}
	
	public boolean checkProduct(String product) {
		return uploadDao.checkProduct(product);
	}

	public String getTagId(String tag) {
		return uploadDao.getTagId(tag);
	}

	public int addTag(TagModel tagModel) {
		return uploadDao.addTag(tagModel);
		
	}

	public int addProductTag(List<ProductTagModel> listProductTag) {
		return uploadDao.addProductTag(listProductTag);
	}

	public int deleteProductTag(List<String> listProduct) {
		return uploadDao.deleteProductTag(listProduct);
	}

	public List<String> fetchProductsName() {
		return uploadDao.fetchProductsName();
	}

	public List<TagModel> fetchTags() {
		return uploadDao.fetchTags();
	}
}
