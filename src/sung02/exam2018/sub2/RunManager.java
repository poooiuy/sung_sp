package sung02.exam2018.sub2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RunManager {
	
	static String cmd = "";

	public static void main(String[] args) throws IOException {
		
		BufferedReader in = null;
		
		try {
			
			Scanner scanner = new Scanner(System.in);
			
			cmd = scanner.nextLine();
			
			in = new BufferedReader(new FileReader(".//src//sung02.exam2018.file//BIGFILE//"+ cmd));
			String beforeline = "";
			String line ;
			int count = 0;
			boolean first = true;
			while ((line = in.readLine()) != null) {
				
				if(first) {
					first = false;
					beforeline = line;
					continue;
				}
				
				if( beforeline.equals(line) ) {
					count += 1;
					
				} else {
					if(count == 0) {
						write(beforeline, false);
						beforeline = line;
					}else {
						beforeline = (count + 1)+"#"+beforeline;
						write(beforeline, false);
						beforeline = line;
						count =  0;
					}
				}

			}
			
			//	Last line
			if(count == 0) {
				write(beforeline, true);
			}else {
				beforeline = (count + 1)+"#"+beforeline;
				write(beforeline, true);
			}
			
			// ���� �� �۾�
			in.close();
			
			File file = new File(".//src//sung02.exam2018.file//BIGFILE//"+ cmd);
			if(file.exists()) {
				file.delete();
			}
			
			file = new File(".//src//sung02.exam2018.file//BIGFILE//temp"+ cmd);
			file.renameTo(new File(".//src//sung02.exam2018.file//BIGFILE//"+ cmd));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) { in.close(); }
		}
		
		

	}
	
	private static void write(String line, boolean last) throws IOException {
		
		String line2 = process(line);
		
		String outputFile = "./src/sung02.exam2018.file/BIGFILE/temp" + cmd;
		
		File destFolder = new File(outputFile);
		if (!destFolder.exists()) {
			destFolder.createNewFile();
		}
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(outputFile, true);
			if(!last) {
				fw.write(line2 + "\r\n");
			}else {
				fw.write(line2);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) { fw.close(); }
		}
	}
	
	private static String process(String line) {
		
		String result = "";
		List list = new ArrayList();
		
		for(int i=0; i < line.length(); i++) {
			list.add(line.charAt(i));
		}
		
		String before = "";
		int cnt = 0;
		for(int i=0; i<list.size(); i++) {
			
			if(i==0) {
				before = list.get(i).toString();
				continue;
			}
			
			if(before.equals(list.get(i).toString())) {
				cnt += 1;
			}else {
				if(cnt == 0) {
					result = result + before;
					before = list.get(i).toString();
				}else if(cnt == 1) {
					result = result + before + before;
					before = list.get(i).toString();
					cnt = 0;
				}else {
					result = result + (cnt + 1) + before;
					before = list.get(i).toString();
					cnt = 0;
				}
			}
			
			if( i == (list.size()-1) ){
				if(cnt == 0) {
					result = result + list.get(i);
				}else if(cnt == 1) {
					result = result + before + before;
				}else {
					result = result + (cnt + 1) + list.get(i);
				}
			}
			
		}
		
		return result;
	}

}
