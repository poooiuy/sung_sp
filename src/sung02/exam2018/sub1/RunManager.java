package sung02.exam2018.sub1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
						write(beforeline + "\r\n");
						beforeline = line;
					}else {
						beforeline = (count + 1)+"#"+beforeline;
						write(beforeline + "\r\n");
						beforeline = line;
						count =  0;
					}
				}

			}
			
			//	Last line
			if(count == 0) {
				write(beforeline);
			}else {
				beforeline = (count + 1)+"#"+beforeline;
				write(beforeline);
			}
			
			// 종료 후 작업
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
	
	private static void write(String line) throws IOException {
		String outputFile = "./src/sung02.exam2018.file/BIGFILE/temp" + cmd;
		
		File destFolder = new File(outputFile);
		if (!destFolder.exists()) {
			destFolder.createNewFile();
		}
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(outputFile, true);
			fw.write(line);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) { fw.close(); }
		}
	}

}
