package com.services.ec2;

import org.aeonbits.owner.ConfigFactory;

import com.amazonaws.regions.Regions;
import com.services.config.AWSConfig;
import com.services.interfaces.IEC2Service;

public class EC2RestService {

	public static void main(String[] args) {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		IEC2Service ec2Service = new EC2Service(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		ec2Service.createEC2Instance();
	}

	private IEC2Service ec2Service;

	private IEC2Service getEC2Service() {
		if (this.ec2Service == null) {
			AWSConfig config = ConfigFactory.create(AWSConfig.class);
			this.ec2Service = new EC2Service(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		}
		return this.ec2Service;
	}

}
