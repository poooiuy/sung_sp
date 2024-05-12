package sung05_exam2023_2_sub3;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;

public class Executor {
	
	Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public void run(String path) throws Exception {
		ScannerIn si = ScannerIn.getInstance();
		Map<String, Object> state = (Map<String, Object>) si.getMap().get(path);
		String url = (String) state.get("url");
		List<String> parameters = (List<String>) state.get("parameters");
		
		if(parameters != null && parameters.size()>0) {
			for(int i=0; i<parameters.size(); i++) {
				if(i==0) {
					url = url + "?"+ parameters.get(i) + "=" + si.getVMap().get(parameters.get(i));
				}else {
					url = url + "&" + parameters.get(i) + "=" + si.getVMap().get(parameters.get(i));
				}
			}
		}
		getRequest(url);
	}
	
	private void getRequest(String url) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentResponse contentRes = httpClient.newRequest(url).method(HttpMethod.GET).send();
		String res = contentRes.getContentAsString();
		System.out.println(res);
		Map<String, String> resMap = gson.fromJson(res, Map.class);
		for(String s : resMap.keySet()) {
			ScannerIn.getInstance().getVMap().put(s, resMap.get(s));
		}
	}

}
