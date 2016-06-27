package com.webapp.models;

/**
 * stas
 * 8/5/15.
 */
public class UserProfilePhotos {

	private Integer total_count;
	private PhotoSize[][] photos;
	public Integer getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}
	public PhotoSize[][] getPhotos() {
		return photos;
	}
	public void setPhotos(PhotoSize[][] photos) {
		this.photos = photos;
	}
	
}
