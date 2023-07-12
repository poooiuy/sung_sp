package sung05_exam2022_httpProxyServer_sub2;

import java.util.Scanner;

public class ScannerModule {
	
	public void scanner() {
		Scanner scanner = new Scanner(System.in);
		String line;
		
		while( (line = scanner.nextLine()) != null ) {
			ExecProcessor execProcessor = new ExecProcessor();
			execProcessor.run(line);
		}
		
	}

}