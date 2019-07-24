/*
 * INPUT 폴더 내의 파일 목록과 크기를 출력하시오.
 * 크기가 2Kbyte가 넘는 파일들은 모두 OUTPUT 폴더로 복사하시오. OUTPUT 폴더가 존재하지 않을 경우 생성하시오.
 * 단, 파일 복사 시 바이너리 파일을 버퍼에 읽고 쓰는 방식으로 구현하고, 버퍼의 크기는 512Byte로 설정하시오.
 */

package sung00_file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {
	
	public static void fileAccess(String path) {
		
		File directory = new File(path);
		File[] fileArr = directory.listFiles();
		
		for(File file : fileArr) {
			print(file);
			
			if(file.length() > 2000) {
				copy(file);
			}
		}
	}
	
	private static void print(File file) {
		
		System.out.println( file.getName()
				+ " : " 
				+ file.length()
				+ " bytes");
	}
	
	private static void copy(File file) {
		
		int bufferSize = 512;
		int readLen;
		
		// Create Folder
		File destFolder = new File("./src/sung00.file/OUTPUT");
		if(!destFolder.exists()) {
			destFolder.mkdirs(); 
		}       
		
		try {
			InputStream inputStream = new FileInputStream(file);
			OutputStream outputStream = new FileOutputStream("./src/sung00.file/OUTPUT/"+file.getName());
			
			byte[] buffer = new byte[bufferSize];
			
			while((readLen = inputStream.read(buffer)) != -1){
				outputStream.write(buffer, 0, readLen);
			}
			
			inputStream.close();
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		fileAccess("./src/sung00.file/INPUT");
	}

}
