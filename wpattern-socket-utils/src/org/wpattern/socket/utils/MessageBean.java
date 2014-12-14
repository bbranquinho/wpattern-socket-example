package org.wpattern.socket.utils;

import java.io.Serializable;

public class MessageBean implements Serializable {

	private static final long serialVersionUID = -5396384525444822505L;

	private String name;

	private String message;

	public MessageBean(String name, String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.name + ":" + this.message;
	}

}
