package sung08_exam2023_httpMicroService_solve_sub5.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EngineServer {

	public void start() throws Exception {
        Server server = new Server(8080);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new EngineServlet()), "/*");
        
        server.setHandler(context);
        server.start();
        server.join();
    }
}
