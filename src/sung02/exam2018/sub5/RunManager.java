package sung02.exam2018.sub5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RunManager {
	
	static Map<Integer, String> dataMap = new HashMap<>();
	
	
	public static void main(String[] args) throws IOException {
		
		socketServer();
		
	}
	
	private static void socketServer() throws IOException {
		
		 ServerSocket listener = null;
		 Socket sock;
		 BufferedReader br;
		 PrintWriter pw;
		 
		 String message;		 
		 int cnt = 0;
		 boolean init = true;
		 
        try {
        	listener = new ServerSocket(9876);
        	sock = listener.accept();
        	System.out.println("연결됨!!!");
        	
        	pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            while(true) {    
				if ((message = br.readLine()) != null) {
					System.out.println("Received Message : " + message);
	
					if (init) {
						String[] temp = message.split("#");
						message = temp[0];
						work(message, temp[1]);
						readData(message);
						pw.println(dataMap.get(1));
						pw.flush();
						init = false;
						cnt += 1;
					}else {
						switch (message) {
						case "ACK":
							pw.println(dataMap.get(cnt + 1));
							pw.flush();
							cnt += 1;
							break;
						case "ERR":
							pw.println(dataMap.get(cnt));
							pw.flush();
							break;
						default:
							pw.println(dataMap.get(Integer.parseInt(message)));
							pw.flush();
							cnt = Integer.parseInt(message);
							break;
						}
					}
	
					if (cnt == dataMap.size()) {
						sock.close();
						listener.close();
					}
				}
			}
                
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
        	
        }
	}
	
	private static void readData(String inputFileName) throws IOException {
		
		String fileName = new FileSearch().searchFilePath(inputFileName, ".//src//sung02.exam2018.file//BIGFILE//");
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line;
		int lineCnt = 1;
		while ((line = in.readLine()) != null) {
			dataMap.put(lineCnt, line);
			lineCnt++;
		}
		
	}
	
	private static void work(String inputFileName, String target) throws IOException {
		
		BufferedReader in = null;
		
		try {
			
			String fileName = new FileSearch().searchFilePath(inputFileName, ".//src//sung02.exam2018.file//BIGFILE//");
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
						write(temp, beforeline, false, target);
						beforeline = line;
					}else {
						beforeline = (count + 1)+"#"+beforeline;
						write(temp, beforeline, false, target);
						beforeline = line;
						count =  0;
					}
				}

			}
			
			//	Last line
			if(count == 0) {
				write(temp, beforeline, true, target);
			}else {
				beforeline = (count + 1)+"#"+beforeline;
				write(temp, beforeline, true, target);
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
	
	private static void write(String fileName, String line, boolean last, String target) throws IOException {
		
		String line2 = process(line);
//		line2 = caesar(line2, 5);
		line2 = encryt(line2, target);
		
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
	
	private static String encryt(String s, String x) {
		
		String result = x;
		String[] target = x.split("");
		
		for(int i=0; i<s.length(); i++){
			char str = s.charAt(i);
			
			if( !isHasValue(target, str+"" ) ) {
				result += str;
			}
		}
		
		return result;
    }
	
	public static boolean isHasValue(String[] targetArr, String value) {
		return Arrays.stream(targetArr).anyMatch(value::equals);
	}

}
