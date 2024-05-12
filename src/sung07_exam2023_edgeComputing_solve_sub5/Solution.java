package sung07_exam2023_edgeComputing_solve_sub5;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.gson.Gson;

public class Solution {

	private Gson gson = new Gson();
	private ServerCommandInfoData serverCommandInfoData;
	private DeviceCommandInfoData deviceCommandInfoData;
	private DeviceInfoData deviceInfoData;

	public void run() throws Exception {

		loadServerCommandInfo();
		loadDeviceCommandInfo();
		loadDeviceInfo();
		
		Server fromServer = createServer();
		fromServer.start();
	}

	private void loadServerCommandInfo() throws Exception {
		serverCommandInfoData = gson.fromJson(String.join("", Files.readAllLines(Paths.get("INFO/SERVER_COMMAND.JSON"))),
				ServerCommandInfoData.class);
	}

	private void loadDeviceCommandInfo() throws Exception {
		deviceCommandInfoData = gson.fromJson(String.join("", Files.readAllLines(Paths.get("INFO/DEVICE_COMMAND.JSON"))),
				DeviceCommandInfoData.class);
	}

	private void loadDeviceInfo() throws Exception {
		deviceInfoData = gson.fromJson(String.join("", Files.readAllLines(Paths.get("INFO/DEVICE.JSON"))),
				DeviceInfoData.class);
	}

	private Server createServer() {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(8010);
		server.addConnector(http);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/*");
        context.addServlet(new ServletHolder(new EdgeNodeServlet(serverCommandInfoData, deviceCommandInfoData, deviceInfoData)),"/*");
        server.setHandler(context);
        
		return server;
	}
}
