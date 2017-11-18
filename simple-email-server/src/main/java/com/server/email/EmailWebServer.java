package com.server.email;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.server.services.Services;

public class EmailWebServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		setUpServices(context);

		server.start();
		server.join();
	}

	private static void setUpServices(ServletContextHandler context) {
		ServletHolder emailRestServices = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		emailRestServices.setInitOrder(0);
		emailRestServices.setInitParameter("jersey.config.server.provider.classnames",
				String.join(",", Services.getServices()));
	}

}
