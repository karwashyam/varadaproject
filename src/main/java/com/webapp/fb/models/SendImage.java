package com.webapp.fb.models;

public class SendImage {

	private String text;
	private ImageAttachment attachment;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ImageAttachment getAttachment() {
		return attachment;
	}
	public void setAttachment(ImageAttachment attachment) {
		this.attachment = attachment;
	}
	

}
