package sung02_exam2018_sub3;

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
			
			String fileName = new FileSearch().searchFilePath(cmd, ".//src//sung02.exam2018.file//BIGFILE//");
			String temp = fileName + "_temp";
			
			System.out.println("fileName : " + fileName);
			
			in = new BufferedReader(new FileReader(fileName));
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
						write(temp, beforeline, false);
						beforeline = line;
					}else {
						beforeline = (count + 1)+"#"+beforeline;
						write(temp, beforeline, false);
						beforeline = line;
						count =  0;
					}
				}

			}
			
			//	Last line
			if(count == 0) {
				write(temp, beforeline, true);
			}else {
				beforeline = (count + 1)+"#"+beforeline;
				write(temp, beforeline, true);
			}
			
			// 종료 후 작업
			in.close();
			
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			
			file = new File(temp);
			file.renameTo(new File(fileName));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) { in.close(); }
		}
		
		

	}
	
	private static void write(String fileName, String line, boolean last) throws IOException {
		
		String line2 = process(line);
		line2 = caesar(line2, 5);
		
		String outputFile = fileName;
		
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
	
	private static String caesar(String s, int n) {
		
		String result = "";
		String index = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for(int i=0; i<s.length(); i++){
			char str = s.charAt(i);
			
			if( (str >= 'a' && str <= 'z') || (str >= 'A' && str <= 'Z')) {
				int idx = index.lastIndexOf(str);
				result += index.charAt(idx-n)+"";
			}else {
				result += str;
			}
		}
		
		return result;
    }

}
