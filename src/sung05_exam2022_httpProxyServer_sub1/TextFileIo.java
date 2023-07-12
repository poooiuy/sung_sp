package sung05_exam2022_httpProxyServer_sub1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
				
				//	줄넘김 적용
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
	
	public static void main(String[] args) {
		printFile("./src/sung/file/BIGFILE/ABCDFILE.TXT");
	}

}
