package sung05_exam2022_httpProxyServer_sub4;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class MyServer {

	public static void main(String[] args) throws Exception {
//		new MyServer().start();
	}

	public void start(int port, List<Map<String, String>> list) throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(port);
		server.addConnector(http);
		
		ServletContextHandler servletContextHandler  = new ServletContextHandler();
		servletContextHandler.setContextPath("/");
		
		for(Map<String, String> m : list) {
			MyServlet myServlet = new MyServlet(m.get("url"));
			ServletHolder servletHolder = new ServletHolder(myServlet);
			servletContextHandler.addServlet(servletHolder, m.get("pathPrefix").concat("/*"));
		}		
		server.setHandler(servletContextHandler);
		
		server.start();
		server.join();
	}
	
}
