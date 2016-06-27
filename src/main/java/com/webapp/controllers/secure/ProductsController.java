package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.models.ProductTagModel;
import com.webapp.models.TagModel;
import com.webapp.models1.Products;
import com.webapp.services.ProductsService;
import com.webapp.services.UploadService;

@Controller
@RequestMapping("/products")
public class ProductsController extends BusinessController {

	protected static Logger logger = Logger.getLogger(CouponsController.class);
	
	@Autowired
	ProductsService productsService;

	@Autowired
	UploadService uploadService;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		return "products-list";
	}

	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<Products> getProductsList(
			@RequestParam int sEcho, @RequestParam String sSearch,
			HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<Products> dt = new DataTablesTO<Products>();

		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");

		// DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		// String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		List<Products> accts = productsService.fetchProductsList(tableUtils);
		for (Products product : accts) {
			product.setAction("<a href='" + req.getContextPath()
					+ "/products/edit.do?productId=" + product.getProductId()
					+ "'>Edit</a>");
		}

		long count = productsService.fetchTotalProducts(tableUtils);

		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);

		return dt;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String getEditProductForm(@RequestParam("productId") String productId,
			Model model, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		Products product = productsService.getProductDetails(productId);
		model.addAttribute("productModel", product);

		List<Map<String, Object>> productTypes = productsService.getAllProductTypes();
		model.addAttribute("productTypes", productTypes);

		return "product-details";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String editProduct(Products productModel, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		int updateStatus = productsService.updateProduct(productModel);

		logger.debug("updateStatus = " + updateStatus);

		List<ProductTagModel> listProductTag = new ArrayList<ProductTagModel>();

		List<String> tagNames = productModel.getTagNames();

		if (tagNames != null) {
			for (String tagName : tagNames) {
				String tagId = uploadService.getTagId(tagName.trim().toLowerCase());
				if (tagId == null) {
					TagModel tagModel = new TagModel();
					tagModel.setTagId(UUIDGenerator.generateUUID());
					tagModel.setTagName(tagName.trim().toLowerCase());
					uploadService.addTag(tagModel);
					ProductTagModel productTagModel = new ProductTagModel();
					productTagModel.setProductTagId(UUIDGenerator.generateUUID());
					productTagModel.setTagId(tagModel.getTagId());
					productTagModel.setProductId(productModel.getProductId());
					listProductTag.add(productTagModel);
				} else {
					ProductTagModel productTagModel = new ProductTagModel();
					productTagModel.setProductTagId(UUIDGenerator.generateUUID());
					productTagModel.setTagId(tagId);
					productTagModel.setProductId(productModel.getProductId());
					listProductTag.add(productTagModel);
				}
			}
		}
		if (listProductTag.size() > 0) {
			List<String> productIdsList = new ArrayList<String>();
			productIdsList.add(productModel.getProductId());
			updateStatus = uploadService.deleteProductTag(productIdsList);
			updateStatus = uploadService.addProductTag(listProductTag);
		}
		
		return "redirect:/products.do";
	}

	@Override
	protected String[] requiredJs() {
		return new String[] { "js/products.js" };
	}
}