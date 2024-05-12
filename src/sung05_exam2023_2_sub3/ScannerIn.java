package sung05_exam2023_2_sub3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.Gson;

import sung00_http.MyServlet;


public class ScannerIn {
	static ScannerIn scannerIn;
	
	public static ScannerIn getInstance() {
		if(scannerIn == null) {
			scannerIn = new ScannerIn();
		}
		return scannerIn;
	}
	
	public ScannerIn() {
		readFile();
		readVarialbe();
	}
	
	Map<String, Object> map = new HashMap<>();
	Map<String, Object> vMap = new HashMap<>();
	Gson gson = new Gson();
	
	public Map<String, Object> getMap(){
		return this.map;
	}
	
	public Map<String, Object> getVMap(){
		return this.vMap;
	}
	
	public void run() throws Exception {
//		System.out.println(System.getProperty("user.dir"));

		Scanner scanner = new Scanner(System.in);
		String line = "";
		while(true) {
			line = scanner.nextLine();
			if(map.containsKey(line)) {
				List<String> strList = (List) map.get(line);
				if(strList.size()>2) {
					String n = "?";
					String val = strList.get(2);
					String[] valArr = val.split(",");
					for(int i=0; i<valArr.length; i++) {
						if(i==0) {
							n = n + valArr[i] + "=" + vMap.get(valArr[i]);
						}else {
							n = n + "&" + valArr[i] + "=" + vMap.get(valArr[i]);
						}
					}
					System.out.println(strList.get(0) + " " + strList.get(1) + n);
				}else {
					System.out.println(strList.get(0) + " " + strList.get(1));
				}
			}
		}
	}
	
	public void readFile() {
		String text = "";
		String line = null;
		
		try {
			FileReader fileReader = new FileReader("./src/sung05_exam2023_2_sub3/STATE.JSON");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				text = text + line;
			}
			Map<String, Map<String, Object>> tMap = gson.fromJson(text, Map.class);
			map = tMap.get("state");
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readVarialbe() {
		String text = "";
		String line = null;
		
		try {
			FileReader fileReader = new FileReader("./src/sung05_exam2023_2_sub3/VARIABLE.JSON");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				text = text + line;
			}
			vMap = gson.fromJson(text, Map.class);
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
