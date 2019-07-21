package sung00.file;

import java.io.File;
import java.io.IOException;

public class FileMove {
	
	public static void main(String[] args) throws IOException {
		moveFileToBackup("./src/sung01.education2019.workshop.file/INSP_001/INSP_001_20190721145834.TXT",
				"./src/sung01.education2019.workshop.file/BACKUP/INSP_001_20190721145834.TXT");
	}

	
	//	path + file 명까지 기재
	private static void moveFileToBackup(String from, String to) throws IOException {
		
		System.out.println(System.getProperty("user.dir"));
		
		File fileFrom = new File(from); // source
		File fileTo = new File(to); // destination
		fileTo.delete();
		boolean result = fileFrom.renameTo(fileTo);
		
		System.out.println(result);
	}
}
