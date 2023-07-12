package sung05_exam2022_httpProxyServer_sub3;

import java.util.Scanner;

public class ScannerModule {
	
	public void scanner() throws Exception {
		Scanner scanner = new Scanner(System.in);
		String line;
		
		while( (line = scanner.nextLine()) != null ) {
			System.out.println("line:" + line);
			ExecProcessor execProcessor = new ExecProcessor(line);
			execProcessor.start();
			
			try {
				execProcessor.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}