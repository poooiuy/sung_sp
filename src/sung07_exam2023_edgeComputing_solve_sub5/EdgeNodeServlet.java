package sung07_exam2023_edgeComputing_solve_sub5;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;

import com.google.gson.Gson;

import sung07_exam2023_edgeComputing_solve_sub5.DeviceInfoData.DeviceInfo;
import sung07_exam2023_edgeComputing_solve_sub5.DeviceInfoData.DeviceInfo.Device;

@SuppressWarnings("serial")
public class EdgeNodeServlet extends HttpServlet {

	private Gson gson = new Gson();
	private ServerCommandInfoData serverCommandInfoData;
	private DeviceCommandInfoData deviceCommandInfoData;
	private DeviceInfoData deviceInfoData;

	private Map<String, Long> parallelCountMap = new ConcurrentHashMap<>(); // device_id, parallelCount
	private Map<String, Set<String>> processingMap = new ConcurrentHashMap<>(); // device_id, processingSet

	public EdgeNodeServlet(ServerCommandInfoData serverCommandInfoData, DeviceCommandInfoData deviceCommandInfoData,
			DeviceInfoData deviceInfoData) {
		this.serverCommandInfoData = serverCommandInfoData;
		this.deviceCommandInfoData = deviceCommandInfoData;
		this.deviceInfoData = deviceInfoData;

		for (DeviceInfo deviceInfo : deviceInfoData.getDeviceInfoList()) {
			for (Device device : deviceInfo.getDeviceList()) {
//				System.out.println(device.getDevice());
				this.parallelCountMap.put(device.getDevice(), deviceInfo.getParallelProcessingCount());
				this.processingMap.put(device.getDevice(), new HashSet<String>());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		CommandRequest commandRequest = gson.fromJson(requestData, CommandRequest.class);

		switch (request.getPathInfo()) {
		case "/fromServer":

			List<CompletableFuture<List<String>>> deviceCommandFutureList = new ArrayList<>();
			List<String> result = new ArrayList<>();

			for (String targetDevice : commandRequest.getTargetDevice()) {
				CompletableFuture<List<String>> deviceCommandFuture = CompletableFuture.supplyAsync(() -> {
					return chainedDeviceCommand(targetDevice, commandRequest.getCommand(), commandRequest.getParam());
				});
				deviceCommandFutureList.add(deviceCommandFuture);
			}

			for (CompletableFuture<List<String>> deviceCommandFuture : deviceCommandFutureList) {
				try {
					result.addAll(deviceCommandFuture.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

			response.setStatus(200);
			response.getWriter().write(new CommandResponse(result).toJson(gson));
			break;

		case "/fromDevice":

			String computingLib = deviceCommandInfoData.getComputingLib(commandRequest.getCommand());
			String forwardCommand = deviceCommandInfoData.getForwardCommand(commandRequest.getCommand());
			String computingResult = runComputingLib(computingLib, commandRequest.getParam());
			if (computingResult != null) {
				try {
					sendPostRequest("http://127.0.0.1:7010/fromEdge",
							new CommandRequest(forwardCommand, null, computingResult).toJson(new Gson()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			response.setStatus(200);
			break;
		}
	}

	private List<String> chainedDeviceCommand(String targetDevice, String command, String requestedParam) {
		String deviceType = deviceInfoData.getType(targetDevice);
		Device device = deviceInfoData.getDevice(targetDevice);
		List<String> chainedCommand = serverCommandInfoData.getForwardCommand(command, deviceType);

		String param = requestedParam;
		List<String> resultList = null;

		for (String chainedCommandString : chainedCommand) {

			while (this.processingMap.get(targetDevice).size() >= this.parallelCountMap.get(targetDevice)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			while (this.processingMap.get(targetDevice).contains(chainedCommandString)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			this.processingMap.get(targetDevice).add(chainedCommandString);

			String commandResponse = null;
			try {
				commandResponse = sendPostRequest(
						String.format("http://%s:%d/fromEdge", device.getHostname(), device.getPort()),
						new CommandRequest(chainedCommandString, null, param).toJson(gson));
			} catch (Exception e) {
				e.printStackTrace();
			}

			CommandResponse responseFromDevice = gson.fromJson(commandResponse, CommandResponse.class);
			resultList = responseFromDevice.getResult();
			param = resultList.get(0);

			this.processingMap.get(targetDevice).remove(chainedCommandString);
		}

		return resultList;
	}

	private String sendPostRequest(String url, String content) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		try {
			org.eclipse.jetty.client.api.Request request = httpClient.POST(url);
			request.header(HttpHeader.CONTENT_TYPE, "application/json");
			request.content(new StringContentProvider(content, "utf-8"));
			ContentResponse response = request.send();
			return new String(response.getContent());
		} catch (ExecutionException executionException) {
			executionException.printStackTrace();
		} finally {
			httpClient.stop();
		}
		return null;
	}

	private String runComputingLib(String computingLibrary, String parameter) {
		String result = null;
		try {
			URL classURL = new URL(
					"jar:" + new File(String.format("./LIB/%s.jar", computingLibrary)).toURI().toURL() + "!/");
			try (URLClassLoader classLoader = new URLClassLoader(new URL[] { classURL })) {
				Class<?> c = classLoader.loadClass("com.lgcns.computing.ComputingLib");
				Constructor<?> constructor = c.getConstructor(new Class[] {});
				Object object = constructor.newInstance(new Object[] {});

				Method method = c.getMethod("compute", new Class[] { String.class });
				result = (String) method.invoke(object, parameter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
