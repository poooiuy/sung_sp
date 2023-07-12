package sung00_http;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import sung05_exam2022_httpProxyServer_sub3.MyServlet;

public class MyServer {

	public static void main(String[] args) throws Exception {
		new MyServer().start();
	}

	//	Single Context
	public void start() throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(8080);
		server.addConnector(http);

		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(MyServlet.class, "/mypath");
		server.setHandler(servletHandler);

		server.start();
		server.join();
	}
	
	// Multi Context
	public void start(int port, List<Map<String, String>> list) throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(port);
		server.addConnector(http);
		
		ServletContextHandler servletContextHandler  = new ServletContextHandler();
		servletContextHandler.setContextPath("/");
		
		for(Map<String, String> m : list) {
			System.out.println(m.get("pathPrefix"));
			MyServlet myServlet = new MyServlet(m.get("url"));
			ServletHolder servletHolder = new ServletHolder(myServlet);
			servletContextHandler.addServlet(servletHolder, m.get("pathPrefix"));
		}		
		server.setHandler(servletContextHandler);
		
		System.out.println(server.getHandlers().length);

		server.start();
		server.join();
	}
	
}
