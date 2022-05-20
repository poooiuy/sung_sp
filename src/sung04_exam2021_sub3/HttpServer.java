package com.lgcns.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class HttpServer {
	
	public  void start() throws Exception{
		
    	// 1. Web Server, Server Connector 생성
		Server server = new Server();
		ServerConnector httpConector = new ServerConnector(server);
		httpConector.setHost("127.0.0.1");
		httpConector.setPort(8080);
		server.addConnector(httpConector);
		
        // 2. Servlet Handler 매핑
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(Http2Servlet.class, "/");
		server.setHandler(servletHandler);
		
        // 3. Web Server start
		server.start();
		server.join();
		
	}

}


