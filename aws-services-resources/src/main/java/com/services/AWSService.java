package com.services;

import com.amazonaws.regions.Regions;

public abstract class AWSService {

	private Regions region;
	private String accessKey;
	private String secretKey;

	public AWSService(final Regions region, final String accessKey, final String secretKey) {
		this.region = region;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public AWSService(final Regions region) {
		this.region = region;
	}

	protected Regions getRegion() {
		return region;
	}

	protected String getAccessKey() {
		return accessKey;
	}

	protected String getSecretKey() {
		return secretKey;
	}

}
