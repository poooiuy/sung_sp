package sung00_http;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

public class MyClient {

	public static void main(String[] args) throws Exception {
		getRequest();
	}
	
	
	private static void getRequest() throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/mypath").method(HttpMethod.GET).send();
		System.out.println(contentRes.getContentAsString());
	}
	
	private static void postRequest() throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		Request request = httpClient.POST("https://a1.easemob.com/yinshenxia/yinshenxia/users");
		request.header(HttpHeader.CONTENT_TYPE, "application/json");
        request.content(new StringContentProvider("{\"username\":\"jliu\",\"password\":\"123456\"}","utf-8"));
//      request.param("username", "jliu");
//      request.param("password", "123456");
		ContentResponse contentRes = request.send();
		String res = contentRes.getContentAsString();
        System.out.println(res);
//        httpClient.stop();
	}
}
