package com.services.objects;

public enum ImageId {
	AMAZON_LINUX("AmazonLinux", "ami-6057e21a"), UBUNTU_SERVER_16_04("UbuntuServer",
			"ami-da05a4a0"), MICROSOFT_WINDOWS_SERVER_2016_BASE("Windows_2016_BASE", "ami-e3bb7399");

	private final String key;
	private final String value;

	ImageId(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
