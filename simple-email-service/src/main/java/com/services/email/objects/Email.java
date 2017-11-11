package com.services.email.objects;

import java.util.List;

public class Email {

	private String fromAddress;
	private List<String> toAddresses;
	private String subject;
	private String body;

	public Email(String fromAddress, List<String> toAddress, String subject, String body) {
		this.fromAddress = fromAddress;
		this.toAddresses = toAddress;
		this.subject = subject;
		this.body = body;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public List<String> getToAddresses() {
		return toAddresses;
	}

	public void setToAddress(List<String> toAddress) {
		this.toAddresses = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((fromAddress == null) ? 0 : fromAddress.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((toAddresses == null) ? 0 : toAddresses.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (fromAddress == null) {
			if (other.fromAddress != null)
				return false;
		} else if (!fromAddress.equals(other.fromAddress))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (toAddresses == null) {
			if (other.toAddresses != null)
				return false;
		} else if (!toAddresses.equals(other.toAddresses))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Email [fromAddress=" + fromAddress + ", toAddress=" + toAddresses + ", subject=" + subject + ", body="
				+ body + "]";
	}

}
