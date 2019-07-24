package sung00_file_directory;

import java.io.File;

public class FileDirectoryListPrint {
	
	public static void fileDirectoryList(String path) {
		File directory = new File(path);
		File[] fileArr = directory.listFiles();
		
		for(File file : fileArr) {
			//	case Directory
			if( file.isDirectory() ) {
				System.out.println("[" + file.getName() + "]");
			}else {
			//	case File
				System.out.println( file.getName() );
			}
		}
	}
	
	public static void fileDirectoryListAll(String path) {
		File directory = new File(path);
		File[] fileArr = directory.listFiles();
		
		for(File file : fileArr) {
			//	case Directory
			if( file.isDirectory() ) {
				System.out.println("[" + file.getName() + "]");
				fileDirectoryListAll(path+"/"+file.getName());
			}else {
			//	case File
				System.out.println( file.getName() );
			}
		}
	}
	
	public static void main(String[] args) {
		//	1st 
//		fileDirectoryList("./");
		
		//	all
		fileDirectoryListAll("./");
	}

}
