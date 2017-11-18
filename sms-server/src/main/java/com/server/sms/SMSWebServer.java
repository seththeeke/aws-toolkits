package com.server.sms;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.server.services.Services;

public class SMSWebServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		setUpServices(context);

		server.start();
		server.join();
	}

	private static void setUpServices(ServletContextHandler context) {
		ServletHolder helloServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		helloServlet.setInitOrder(0);
		helloServlet.setInitParameter("jersey.config.server.provider.classnames",
				String.join(",", Services.getServices()));
	}

}
