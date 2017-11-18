package com.server.services;

import com.services.sms.SMSRestService;

public class Services {

	private static String[] services = { SMSRestService.class.getCanonicalName() };

	public static String[] getServices() {
		return services;
	}

}
