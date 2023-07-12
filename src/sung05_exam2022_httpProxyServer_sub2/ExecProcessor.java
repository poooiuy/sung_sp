package sung05_exam2022_httpProxyServer_sub2;

import java.util.Map;

public class ExecProcessor {
	
	TextFileIo textFileIo = new TextFileIo();

	public void run(String line) {
		
		String[] command = line.split(" ");
		proxyProcess(command[0].concat(".txt"), command[1]);
		
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
