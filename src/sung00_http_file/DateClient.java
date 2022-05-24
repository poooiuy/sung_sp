package sung00_http_file;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

public class DateClient {

    public static void main(String[] args) throws Exception {

        HttpClient httpClient = new HttpClient();
        httpClient.start();
        ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/requestDate").method(HttpMethod.GET).send();
        System.out.println(contentRes.getContentAsString());
        httpClient.stop();

    }

}