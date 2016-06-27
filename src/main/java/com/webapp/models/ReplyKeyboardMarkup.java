package com.webapp.models;

import java.util.List;

/**
 * stas 8/4/15.
 */
public class ReplyKeyboardMarkup extends Keyboard {

	public List<List<String>> keyboard;
	public boolean resize_keyboard;
	public boolean one_time_keyboard;
	public boolean selective;

	public List<List<String>> getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(List<List<String>> keyboard) {
		this.keyboard = keyboard;
	}

	public boolean isResize_keyboard() {
		return resize_keyboard;
	}

	public void setResize_keyboard(boolean resize_keyboard) {
		this.resize_keyboard = resize_keyboard;
	}

	public boolean isOne_time_keyboard() {
		return one_time_keyboard;
	}

	public void setOne_time_keyboard(boolean one_time_keyboard) {
		this.one_time_keyboard = one_time_keyboard;
	}

	public boolean isSelective() {
		return selective;
	}

	public void setSelective(boolean selective) {
		this.selective = selective;
	}

	/* public ReplyKeyboardMarkup(String[]... keyboard) {
	     this(keyboard, false, false, false);
	 }

	 public ReplyKeyboardMarkup(String[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard, boolean selective) {
	     this.keyboard = keyboard;
	     this.resize_keyboard = resize_keyboard;
	     this.one_time_keyboard = one_time_keyboard;
	     this.selective = selective;
	 }*/
}
