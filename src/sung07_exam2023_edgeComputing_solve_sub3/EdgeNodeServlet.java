package sung07_exam2023_edgeComputing_solve_sub3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import sung07_exam2023_edgeComputing_solve_sub3.DeviceInfoData.DeviceInfo;
import sung07_exam2023_edgeComputing_solve_sub3.ServerCommandInfoData.ServerCommandInfo;

@SuppressWarnings("serial")
public class EdgeNodeServlet extends HttpServlet {

	private Gson gson = new Gson();
	private Map<String, ServerCommandInfo> serverCommandInfoMap;
	private Map<String, DeviceInfo> deviceInfoMap;

	public EdgeNodeServlet(Map<String, ServerCommandInfo> serverCommandInfoMap, Map<String, DeviceInfo> deviceInfoMap) {
		this.serverCommandInfoMap = serverCommandInfoMap;
		this.deviceInfoMap = deviceInfoMap;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		CommandRequest requestData = this.gson.fromJson(requestString, CommandRequest.class);

		switch (request.getPathInfo()) {
		case "/fromServer":
			List<String> result = new ArrayList<>();

			for (String targetDevice : requestData.getTargetDevice()) {
				DeviceInfo deviceInfo = this.deviceInfoMap.get(targetDevice);
				String forwardCommand = this.serverCommandInfoMap.get(requestData.getCommand()).getForwardCommand();

				String commandResponse = null;
				try {
					commandResponse = sendPostRequest(
							String.format("http://%s:%d/fromEdge", deviceInfo.getHostname(), deviceInfo.getPort()),
							new CommandRequest(forwardCommand, null, requestData.getParam()).toJson(gson));
				} catch (Exception e) {
					e.printStackTrace();
				}
				CommandResponse responseFromDevice = gson.fromJson(commandResponse, CommandResponse.class);
				result.addAll(responseFromDevice.getResult());
			}
			response.setStatus(200);
			response.getWriter().write(new CommandResponse(result).toJson(gson));

			break;
		}
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
}
