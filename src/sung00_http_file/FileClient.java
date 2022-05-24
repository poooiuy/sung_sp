package sung00_http_file;

import java.io.File;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FileClient {

    public static void main(String[] args) throws Exception {

        String strFileList = getFileList();
        HttpClient httpClient = new HttpClient();
        httpClient.start();
        Request request = httpClient.newRequest("http://127.0.0.1:8080/fileList").method(HttpMethod.POST);
        request.header(HttpHeader.CONTENT_TYPE, "application/json");
        request.content(new StringContentProvider(strFileList,"utf-8"));
        ContentResponse contentRes = request.send();
        System.out.println(contentRes.getContentAsString());
        httpClient.stop();

    }

    private static String getFileList() {
        JsonObject jo = new JsonObject();
        File directory = new File("./Input");
        jo.addProperty("Folder", "Input");
        JsonArray jarr = new JsonArray();
        File[] fList = directory.listFiles();
        for (File file : fList) {
            jarr.add(file.getName());
        } 

        jo.add("FILES", jarr);
        String res = jo.toString();
        return res; 
    }

}