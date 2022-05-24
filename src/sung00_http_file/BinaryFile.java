package sung00_http_file;

import java.io.FileOutputStream;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

public class BinaryFile {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.start();
        ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/requestFile").method(HttpMethod.GET).send();
        byte [] myByteArray = contentRes.getContent();

        try (FileOutputStream fos = new FileOutputStream("C:\\temp\\test.jpg")) {
            fos.write(myByteArray);
        }
        httpClient.stop();
    }
}