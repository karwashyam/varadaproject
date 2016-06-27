package com.webapp.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private Integer message_id;
    private User from;
    private Integer date;
    private Integer edit_date;
    private UserOrGroupChat chat;
    private User forward_from;
    private Integer forward_date;
    private Message reply_to_message;
    private String text;
    private String caption;
    private Audio voice;
    private Document document;
    private PhotoSize[] photo;
    private Sticker sticker;
    private Video video;
    private Contact contact;
    private Location location;
    private User new_chat_participant;
    private User left_chat_participant;
    private String new_chat_title;
    private PhotoSize[] new_chat_photo;
    private Boolean delete_chat_photo;
    private Boolean group_chat_created;
    private Entities[] entities;
    
public String getCaption() {
	return caption;
}
public void setCaption(String caption) {
	this.caption = caption;
}
public Integer getMessage_id() {
	return message_id;
}
public void setMessage_id(Integer message_id) {
	this.message_id = message_id;
}
public User getFrom() {
	return from;
}
public void setFrom(User from) {
	this.from = from;
}
public Integer getDate() {
	return date;
}
public void setDate(Integer date) {
	this.date = date;
}
public UserOrGroupChat getChat() {
	return chat;
}
public void setChat(UserOrGroupChat chat) {
	this.chat = chat;
}
public User getForward_from() {
	return forward_from;
}
public void setForward_from(User forward_from) {
	this.forward_from = forward_from;
}
public Integer getForward_date() {
	return forward_date;
}
public void setForward_date(Integer forward_date) {
	this.forward_date = forward_date;
}
public Message getReply_to_message() {
	return reply_to_message;
}
public void setReply_to_message(Message reply_to_message) {
	this.reply_to_message = reply_to_message;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}

public Audio getVoice() {
	return voice;
}
public void setVoice(Audio voice) {
	this.voice = voice;
}
public Document getDocument() {
	return document;
}
public void setDocument(Document document) {
	this.document = document;
}
public PhotoSize[] getPhoto() {
	return photo;
}
public void setPhoto(PhotoSize[] photo) {
	this.photo = photo;
}
public Sticker getSticker() {
	return sticker;
}
public void setSticker(Sticker sticker) {
	this.sticker = sticker;
}
public Video getVideo() {
	return video;
}
public void setVideo(Video video) {
	this.video = video;
}
public Contact getContact() {
	return contact;
}
public void setContact(Contact contact) {
	this.contact = contact;
}
public Location getLocation() {
	return location;
}
public void setLocation(Location location) {
	this.location = location;
}
public User getNew_chat_participant() {
	return new_chat_participant;
}
public void setNew_chat_participant(User new_chat_participant) {
	this.new_chat_participant = new_chat_participant;
}
public User getLeft_chat_participant() {
	return left_chat_participant;
}
public void setLeft_chat_participant(User left_chat_participant) {
	this.left_chat_participant = left_chat_participant;
}
public String getNew_chat_title() {
	return new_chat_title;
}
public void setNew_chat_title(String new_chat_title) {
	this.new_chat_title = new_chat_title;
}
public PhotoSize[] getNew_chat_photo() {
	return new_chat_photo;
}
public void setNew_chat_photo(PhotoSize[] new_chat_photo) {
	this.new_chat_photo = new_chat_photo;
}
public Boolean getDelete_chat_photo() {
	return delete_chat_photo;
}
public void setDelete_chat_photo(Boolean delete_chat_photo) {
	this.delete_chat_photo = delete_chat_photo;
}
public Boolean getGroup_chat_created() {
	return group_chat_created;
}
public void setGroup_chat_created(Boolean group_chat_created) {
	this.group_chat_created = group_chat_created;
}
public Entities[] getEntities() {
	return entities;
}
public void setEntities(Entities[] entities) {
	this.entities = entities;
}
public Integer getEdit_date() {
	return edit_date;
}
public void setEdit_date(Integer edit_date) {
	this.edit_date = edit_date;
}
    
    
}
