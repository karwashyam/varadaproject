package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.AddProductModel;
import com.webapp.models.ProductTagModel;
import com.webapp.models.TagModel;

public interface UploadDao {

	boolean checkProduct(@Param("product") String product);
	
	int updateProducts(@Param("listUpdateProducts") List<AddProductModel> listUpdateProducts);
	
	int addProducts(@Param("listAddProducts") List<AddProductModel> listAddProducts);

	String getTagId(@Param("tag") String tag);

	int addTag(TagModel tagModel);

	int addProductTag(@Param("listProductTag") List<ProductTagModel> listProductTag);

	int deleteProductTag(@Param("listProduct") List<String> listProduct);

	List<String> fetchProductsName();

	List<TagModel> fetchTags();

}
