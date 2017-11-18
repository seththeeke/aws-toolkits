package com.server.ec2;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.server.services.Services;

public class EC2WebServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8082);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		setUpServices(context);

		server.start();
		server.join();
	}

	private static void setUpServices(ServletContextHandler context) {
		ServletHolder smsServices = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		smsServices.setInitOrder(0);
		smsServices.setInitParameter("jersey.config.server.provider.classnames",
				String.join(",", Services.getServices()));
	}

}
