package sung07_exam2023_httpMicroService_sub1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ScannerIn {
	
	Map<String, Object> map = new HashMap<>();
	
	public void run() {
//		System.out.println(System.getProperty("user.dir"));
		readFile();
		Scanner scanner = new Scanner(System.in);
		String line = "";
		while(true) {
			line = scanner.nextLine();
			if(map.containsKey(line)) {
				List strList = (List) map.get(line);
				System.out.println(strList.get(0) + " " + strList.get(1));
			}
		}
	}
	
	public void readFile() {
		String line = null;
		
		try {
			FileReader fileReader = new FileReader("./src/sung05_exam2023_2_sub1/STATE.TXT");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				String[] arr = line.split("#");
				List<String> l = new ArrayList();
				for(int i=0; i<arr.length; i++) {
					if(i!=0)	l.add(arr[i]);
				}
				map.put(arr[0], l);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
