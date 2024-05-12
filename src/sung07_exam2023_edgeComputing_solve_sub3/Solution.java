package sung07_exam2023_edgeComputing_solve_sub3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.gson.Gson;

import sung07_exam2023_edgeComputing_solve_sub3.DeviceInfoData.DeviceInfo;
import sung07_exam2023_edgeComputing_solve_sub3.ServerCommandInfoData.ServerCommandInfo;

public class Solution {

	private Gson gson = new Gson();
	private Map<String, ServerCommandInfo> serverCommandInfoMap;
	private Map<String, DeviceInfo> deviceInfoMap;

	public void run() throws Exception {

		loadServerCommandInfo();
		loadDeviceInfo();

		Server fromServer = createServer();
		fromServer.start();
	}

	private void loadServerCommandInfo() throws Exception {
		ServerCommandInfoData commandInfoData = gson.fromJson(
				String.join("", Files.readAllLines(Paths.get("INFO/SERVER_COMMAND.JSON"))),
				ServerCommandInfoData.class);
		this.serverCommandInfoMap = new HashMap<>();
		for (ServerCommandInfo serverCommandInfo : commandInfoData.getServerCommandInfoList()) {
			this.serverCommandInfoMap.put(serverCommandInfo.getCommand(), serverCommandInfo);
		}
	}

	private void loadDeviceInfo() throws Exception {
		DeviceInfoData deviceInfoData = gson
				.fromJson(String.join("", Files.readAllLines(Paths.get("INFO/DEVICE.JSON"))), DeviceInfoData.class);
		this.deviceInfoMap = new HashMap<>();
		for (DeviceInfo deviceInfo : deviceInfoData.getDeviceInfoList()) {
			this.deviceInfoMap.put(deviceInfo.getDevice(), deviceInfo);
		}
	}

	private Server createServer() {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(8010);
		server.addConnector(http);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/*");
        context.addServlet(new ServletHolder(new EdgeNodeServlet(serverCommandInfoMap, deviceInfoMap)), "/*");
        server.setHandler(context);

        return server;
	}
}
