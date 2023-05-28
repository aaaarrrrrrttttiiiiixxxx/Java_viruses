package egorov;

import egorov.build.Autowired;
import egorov.controller.Controller;
import egorov.build.Context;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    @Autowired
    private static Controller controller;

    static {
        try {
            Context.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main (String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/api");
        servletContextHandler.addServlet(new ServletHolder(controller), "/*");
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
    }
}
