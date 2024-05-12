package sung08_exam2023_httpMicroService_solve_sub5.state;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import sung08_exam2023_httpMicroService_solve_sub5.State;
import sung08_exam2023_httpMicroService_solve_sub5.VariableManager;

public class ActionState extends State {

	private String url;
	private List<String> parameters;

	public ActionState(String name, String next, String url, List<String> parameters) {
		super(name, next);
		this.url = url;
		this.parameters = parameters;
	}

	@Override
	public String run() throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();

		try {
			String query = makeQuery();
			ContentResponse contentResponse = httpClient.newRequest(url + query).method(HttpMethod.GET).send();
			JsonObject responseJo = new Gson().fromJson(contentResponse.getContentAsString(), JsonObject.class);
			System.out.println("req:" + url + query + ", res:" + responseJo.toString());
			for (String k : responseJo.keySet()) {
				VariableManager.put(k, responseJo.get(k).getAsString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getNext();
	}

	private String makeQuery() throws UnsupportedEncodingException {
		String query = "";
		for (int i = 0; i < parameters.size(); i++) {
			if (i == 0) {
				query += "?";
			}
			query += URLEncoder.encode(parameters.get(i), "UTF-8") + "="
					+ URLEncoder.encode(VariableManager.get(parameters.get(i)), "UTF-8");
			if (i < parameters.size() - 1) {
				query += "&";
			}
		}
		return query;
	}
}
