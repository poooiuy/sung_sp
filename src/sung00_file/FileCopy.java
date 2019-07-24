/*
 * INPUT ���� ���� ���� ��ϰ� ũ�⸦ ����Ͻÿ�.
 * ũ�Ⱑ 2Kbyte�� �Ѵ� ���ϵ��� ��� OUTPUT ������ �����Ͻÿ�. OUTPUT ������ �������� ���� ��� �����Ͻÿ�.
 * ��, ���� ���� �� ���̳ʸ� ������ ���ۿ� �а� ���� ������� �����ϰ�, ������ ũ��� 512Byte�� �����Ͻÿ�.
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
