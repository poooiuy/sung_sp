package sung04_exam2021_messageQueue_sub4_byYun;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Date;
import java.util.Timer;

public class RunManager {

	public static void main(String[] args) throws Exception {
		Timer timer = new Timer();
		long cuTime = System.currentTimeMillis();
		long startTime = (cuTime - (cuTime%1000)) + 1000;
//		System.out.println("timer :" +new Date(startTime));
		timer.scheduleAtFixedRate(ProcessHandlerNoTimer.getInstance(), new Date(startTime), 100);
		new RunManager().start();
//		ProcessHandler.getInstance().run();
	}

	public void start() throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(8080);
		server.addConnector(http);
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(MyServlet.class, "/");
		server.setHandler(servletHandler);
		server.start();
		server.join();
	}

}
