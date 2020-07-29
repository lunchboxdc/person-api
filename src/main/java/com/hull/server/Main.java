package com.hull.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

	public static void main(String[] args) {

		Server server = new Server(8080);

		ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		ctx.setContextPath("/");
		server.setHandler(ctx);

		ServletHolder servletHolder = ctx.addServlet(ServletContainer.class, "/rest/*");
		servletHolder.setInitOrder(1);
		servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.hull.api");

		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			server.destroy();
		}
	}
}
