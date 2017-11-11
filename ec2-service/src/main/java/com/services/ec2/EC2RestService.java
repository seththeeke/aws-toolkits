package com.services.ec2;

import org.aeonbits.owner.ConfigFactory;

import com.amazonaws.regions.Regions;
import com.services.config.AWSConfig;

public class EC2RestService {

	public static void main(String[] args) {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		new EC2Service(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey()).createEC2Instance();
	}

	private EC2Service ec2Service;

	private EC2Service getEmailService() {
		if (this.ec2Service == null) {
			AWSConfig config = ConfigFactory.create(AWSConfig.class);
			this.ec2Service = new EC2Service(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		}
		return this.ec2Service;
	}

}
