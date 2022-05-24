package sung00_http_file;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletHandler;

public class MyServer {

	public static void main(String[] args) throws Exception {
		new MyServer().start();
	}

	public void start() throws Exception {
        Server server = new Server();
        ServerConnector http = new ServerConnector(server);
        http.setHost("127.0.0.1");
        http.setPort(8080);
        server.addConnector(http);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(MyServlet.class, "/*");
        server.setHandler(servletHandler);
        server.start();
        server.join();

    }

}