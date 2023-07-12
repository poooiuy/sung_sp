import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;

import com.google.gson.Gson;

public class RandomTest {

	
	public static void main(String[] args) {
		try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("SEARCH_SA_UID", "71648");
            requestBody.put("SEARCH_NAME", "");
            requestBody.put("TYPE", "01");
            Gson gson = new Gson();
            String requestBodyString = gson.toJson(requestBody);

            HttpClient httpClient = new HttpClient();

            httpClient.start();

            Request request = httpClient.POST("http://3.39.151.193:10080/legacy/legacy/userList");
            request.header(HttpHeader.CONTENT_TYPE, "application/json");
            request.content(new StringContentProvider(requestBodyString));

            ContentResponse contentRes = request.send();
            String res = contentRes.getContentAsString();
            System.out.println(res);
        } catch (Exception e) {
            
        }
	}
}
