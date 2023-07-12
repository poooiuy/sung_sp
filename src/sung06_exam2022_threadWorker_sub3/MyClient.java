package sung06_exam2022_threadWorker_sub3;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

public class MyClient {

	
	public String  getRequest(String url) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
//		httpClient.setConnectTimeout(10000);
		ContentResponse contentRes = httpClient.newRequest(url).method(HttpMethod.GET).send();
		return contentRes.getContentAsString();
	}
	
	public void postRequest(String url, String val) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		Request request = httpClient.POST(url);
		request.header(HttpHeader.CONTENT_TYPE, "application/json");
        request.content(new StringContentProvider("{\"result\":\"" +  val + "\"}"));
//      request.param("username", "jliu");
//      request.param("password", "123456");
		ContentResponse contentRes = request.send();
		String res = contentRes.getContentAsString();
        System.out.println(res);
//        httpClient.stop();
	}
}
