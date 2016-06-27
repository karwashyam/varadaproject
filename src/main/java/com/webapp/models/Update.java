package com.webapp.models;

/**
 * stas
 * 8/4/15.
 */
public class Update {

	private Integer update_id;
    private Message message;
    private Message edited_message;
public Integer getUpdate_id() {
	return update_id;
}
public void setUpdate_id(Integer update_id) {
	this.update_id = update_id;
}
public Message getMessage() {
	return message;
}
public void setMessage(Message message) {
	this.message = message;
}
public Message getEdited_message() {
	return edited_message;
}
public void setEdited_message(Message edited_message) {
	this.edited_message = edited_message;
}
    
}
