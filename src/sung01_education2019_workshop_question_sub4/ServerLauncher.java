package sung01_education2019_workshop_question_sub4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class ServerLauncher {
	
	static TreeMap<String, InsVo> todayMap = new TreeMap<String, InsVo>();
	
	public static void main(String[] args) throws IOException {
		try {
			Server server = new Server();
			Thread thread = new Thread(server);
			thread.start();
			
			Scanner scanner = new Scanner(System.in);
			String line = "";
			boolean run = true;
			
			while(run) {
				if( (line = scanner.nextLine()) != null) {
					
					switch(line) {
					case "REPORT" : 
						agg();
						System.out.println("REPORT FINISH");
						break;
					case "QUIT" : 
						server.close();
						run = false;
						break;
					default : 
						print(line);
						break;
					}
					
				}
				
			}
			scanner.close();
		}catch(Exception e) {
			
		}
	}
	
	private static void agg() throws IOException {
		
		File directory = new File("./src/sung01.education2019.workshop.file/SERVER");
		File[] fList = directory.listFiles();
		String today = CardUtility.getCurrentDateString();
		for (File file : fList) {
			String fileDate = file.getName().substring(9,17);
			if( today.equals(fileDate) ) {
				
				String line = null;
				Map<String, String> result = new HashMap<>();
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				while((line = bufferedReader.readLine()) != null) {
					
					String[] data = line.split("#");
					String id = data[0];
					String valid = data[3];
					int num = 0;

					
					if(!todayMap.containsKey(id)) {
						InsVo vo = new InsVo();
						vo.setInspectorId(id);
						if(valid.equals("R1")) {
							vo.setValid(1);
							vo.setInValid(0);
						}else {
							vo.setValid(0);
							vo.setInValid(1);
						}
						todayMap.put(id, vo);
					}else {
						InsVo vo = todayMap.get(id);
						if(valid.equals("R1")) {
							vo.setValid(vo.getValid() + 1);
						}else {
							vo.setInValid(vo.getInValid() + 1);
						}
					}
				}
				bufferedReader.close();
			}
		}
		
		String strFilename = "./src/sung01.education2019.workshop.file/SERVER/REPORT_" + today + ".TXT";
		File file = new File(strFilename);
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(strFilename, true);
			Set<String> set = todayMap.keySet();
			
			for(String key : set) {
				fw.write(todayMap.get(key).getInspectorId() + " " + todayMap.get(key).getValid() + " " + todayMap.get(key).getInValid() + "\r\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) { fw.close(); }
		}
		
	}
	
	private static void print(String date) throws IOException {
		
		boolean order = false;
		
		if(date.contains("C")) {
			date = date.substring(0, (date.length()-1) );
			order = true;
		}
		
		File file = new File("./src/sung01.education2019.workshop.file/SERVER/REPORT_" + date + ".TXT");
		String line = "";
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		List<InsVo> list = new ArrayList<>();
		
		while((line = bufferedReader.readLine()) != null) {
			
			String[] data = line.split(" ");
			
			InsVo vo = new InsVo();
			vo.setInspectorId(data[0]);
			vo.setValid(Integer.parseInt(data[1]));
			vo.setInValid(Integer.parseInt(data[2]));
			
			list.add(vo);

		}
		
		bufferedReader.close();
		
		if (!order) {
			Collections.sort(list, (g1, g2) -> g1.getInspectorId().compareTo(g2.getInspectorId()));
		} else {
			Collections.sort(list, (g1, g2) -> (g2.getValid() - g1.getValid()));
		}
		
		Iterator<InsVo> itr = list.iterator(); 
		while (itr.hasNext()) {
			InsVo val = itr.next();
			System.out.println(String.format("%s %d %d",val.getInspectorId(), val.getValid(), val.getInValid()));
		} 
			
	}
	

}
