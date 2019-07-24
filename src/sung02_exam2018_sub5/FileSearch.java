package sung02_exam2018_sub5;

import java.io.File;

public class FileSearch {
	
	
	public String searchFilePath(String fileName, String parentPath) {
		String result = "";
//		String parentPath = ".//src//sung02.exam2018.file//BIGFILE//";
		
		File directory = new File(parentPath);
		File[] fileArr = directory.listFiles();
		
		for(File file : fileArr) {
			
			if( file.isDirectory() ) {
				//	case Directory
				System.out.println("[" + file.getName() + "]");
				result = searchFilePath(fileName, file.getPath());
				if (!result.equals("")){
					return result;
				};
				
			}else {
				//	case File
				System.out.println( file.getName() );
				if(file.getName().contentEquals(fileName)) {
					return file.getPath();
				}
				
			}
		}
		
		return result;
	}
	
}