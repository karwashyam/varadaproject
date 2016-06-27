package com.webapp.models;

/**
 * stas
 * 8/4/15.
 */
public class PhotoSize {

    private String file_id;
    private Integer width;
    private Integer height;
    private Integer file_size;
    private String file_path;
    private String caption;
    
    
public String getCaption() {
	return caption;
}
public void setCaption(String caption) {
	this.caption = caption;
}
public String getFile_path() {
	return file_path;
}
public void setFile_path(String file_path) {
	this.file_path = file_path;
}
public String getFile_id() {
	return file_id;
}
public void setFile_id(String file_id) {
	this.file_id = file_id;
}
public Integer getWidth() {
	return width;
}
public void setWidth(Integer width) {
	this.width = width;
}
public Integer getHeight() {
	return height;
}
public void setHeight(Integer height) {
	this.height = height;
}
public Integer getFile_size() {
	return file_size;
}
public void setFile_size(Integer file_size) {
	this.file_size = file_size;
}
    
}
