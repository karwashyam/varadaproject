package com.webapp.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ExcelDto {
	private MultipartFile excelName;
	private List<MultipartFile> images;
	
	public List<MultipartFile> getImages() {
		return images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

	public MultipartFile getExcelName() {
		return excelName;
	}

	public void setExcelName(MultipartFile excelName) {
		this.excelName = excelName;
	}

	
	
}
