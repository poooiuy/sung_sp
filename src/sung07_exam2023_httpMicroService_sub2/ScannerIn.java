package sung07_exam2023_httpMicroService_sub2;

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
	Map<String, Object> vMap = new HashMap<>();
	
	public void run() {
//		System.out.println(System.getProperty("user.dir"));
		readFile();
		readVarialbe();
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
		String line = null;
		
		try {
			FileReader fileReader = new FileReader("./src/sung05_exam2023_2_sub2/STATE.TXT");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				String[] arr = line.split("#");
				List<String> l = new ArrayList();
				for(int i=0; i<arr.length; i++) {
					if(i!=0)	l.add(arr[i]);
				}
//				System.out.println(l.size());
				map.put(arr[0], l);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readVarialbe() {
		String line = null;
		
		try {
			FileReader fileReader = new FileReader("./src/sung05_exam2023_2_sub2/VARIABLE.TXT");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				String[] arr = line.split("#");				
				vMap.put(arr[0], arr[1]);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
