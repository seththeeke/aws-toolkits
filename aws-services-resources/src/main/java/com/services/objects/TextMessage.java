package com.services.objects;

public class TextMessage {

	private String receiverPhoneNumber;
	private String message;

	public TextMessage(final String receiverPhoneNumber, final String message) {
		this.receiverPhoneNumber = receiverPhoneNumber;
		this.message = message;
	}

	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}

	public void setReceiverPhoneNumber(String receiverPhoneNumber) {
		this.receiverPhoneNumber = receiverPhoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
