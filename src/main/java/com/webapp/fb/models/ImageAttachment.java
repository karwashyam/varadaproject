package com.webapp.fb.models;

public class ImageAttachment {

	private String type;// image or template
	private ImagePayload payload;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ImagePayload getPayload() {
		return payload;
	}
	public void setPayload(ImagePayload payload) {
		this.payload = payload;
	}

	
}
