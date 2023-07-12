package sung05_exam2022_httpProxyServer_sub4;


import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

public class MyClient {

	public static void main(String[] args) throws Exception {
//		getRequest("http://127.0.0.1:8080/mypath");
	}
	
	
	public ContentResponse getRequest(String path, Map<String, String[]> param) throws Exception {
		System.out.println("getRequest : " + path);
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		Request request = httpClient.newRequest(path);
		for(String s : param.keySet()) {
			request.param(s, param.get(s)[0].toString());
		}
		ContentResponse contentRes = request.method(HttpMethod.GET).send();
		return contentRes;
	}
	
	public ContentResponse postRequest(String path, String body) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		Request request = httpClient.POST(path);
		request.content(new StringContentProvider(body,"utf-8"));
		ContentResponse contentRes = request.send();
		return contentRes;
	}
}
