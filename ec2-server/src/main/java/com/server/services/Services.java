package com.server.services;

import com.services.ec2.EC2RestService;

public class Services {

	private static String[] services = { EC2RestService.class.getCanonicalName() };

	public static String[] getServices() {
		return services;
	}

}
