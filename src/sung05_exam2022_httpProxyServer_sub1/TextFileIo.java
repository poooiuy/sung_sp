package sung05_exam2022_httpProxyServer_sub1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 *	�ٳѱ��� �ʿ��� ��� "\r\n" �� ����Ѵ�. 
 */

public class TextFileIo {
	
	//	������ ������ System.out ���� Print �Ѵ�.
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
	
	//	������ ������ Return �Ѵ�.
	public String readFile(String fileName) {
		String result = "";
		String line = null;
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				//	�ٳѱ� ����
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
