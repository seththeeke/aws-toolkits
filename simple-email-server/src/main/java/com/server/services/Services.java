package com.server.services;

import com.services.email.EmailRestService;

public class Services {

	private static String[] services = { EmailRestService.class.getCanonicalName() };

	public static String[] getServices() {
		return services;
	}
}
