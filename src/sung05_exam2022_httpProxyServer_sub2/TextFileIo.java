package sung05_exam2022_httpProxyServer_sub2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 *	줄넘김이 필요한 경우 "\r\n" 을 사용한다. 
 */

public class TextFileIo {
	
	//	파일의 내용을 System.out 으로 Print 한다.
	public static void printFile(String fileName) {
		String line = null;
			
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				while((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
					
				}
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//	파일의 내용을 Return 한다.
	public String readFile(String fileName) {
		String result = "";
		String line = null;
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				while((line = bufferedReader.readLine()) != null) {
					if(result.equals("")) {
						result = result + line;
					}else {
						result = result + "\r\n" + line;
					}
				}
				
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	//	파일의 내용을 줄단위로 Map에 담아 Return 한다.
	//	separator는 변경해서 사용한다.
	public Map<String, Object> getMapFile(String fileName) {
		Map<String, Object> result = new HashMap<>();
		String line = null;
		String separator = "#";
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				//	줄단위 내용을 Map에 담기
				while((line = bufferedReader.readLine()) != null) {
					String[] strArr = line.split(separator);
					result.put(strArr[0], strArr[1]);
				}
				
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	public static void main(String[] args) {
		printFile("./src/sung/file/BIGFILE/ABCDFILE.TXT");
	}

}
