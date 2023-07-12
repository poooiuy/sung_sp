package sung05_exam2022_httpProxyServer_sub3;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class ExecProcessor extends Thread {
	
	TextFileIo textFileIo = new TextFileIo();
	Gson gson = new Gson();
	String line;
	
	public ExecProcessor(String line) {
		this.line = line;
	}

	public void run(){
		
		String conf = textFileIo.readFile(".//src//sung05_exam2022_httpProxyServer_sub3//"+ line);
		Map<String, Object> map = gson.fromJson(conf, Map.class);
		
		double port = Double.parseDouble(map.get("port").toString());
		List<Map<String, String>> list = (List<Map<String, String>>) map.get("routes");
		
		try {
			new MyServer().start((int)port, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void proxyProcess(String cmd, String path) {
		Map<String, Object> map = textFileIo.getMapFile(".//src//sung05_exam2022_httpProxyServer_sub2//"+ cmd);
		String cmd2 = (String) map.get(path);
		if(cmd2.startsWith("Proxy-")) {
			proxyProcess(cmd2, path);
		} else if(cmd2.startsWith("Service-")){
			textFileIo.printFile(".//src//sung05_exam2022_httpProxyServer_sub2//"+ cmd2);
		}
	}
	
}
