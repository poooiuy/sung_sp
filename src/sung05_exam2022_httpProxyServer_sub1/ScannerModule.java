package sung05_exam2022_httpProxyServer_sub1;

import java.util.Scanner;

public class ScannerModule {
	
	public void scanner() {
		Scanner scanner = new Scanner(System.in);
		String line;
		while( (line = scanner.nextLine()) != null ) {
			TextFileIo textFileIo = new TextFileIo();
			String fileName = textFileIo.readFile(".//src//sung05_exam2022_httpProxyServer_sub1//"+ line + ".txt");
			textFileIo.printFile(".//src//sung05_exam2022_httpProxyServer_sub1//"+ fileName);
		}
	}

}