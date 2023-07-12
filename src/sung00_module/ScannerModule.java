package sung00_module;

import java.util.Scanner;

public class ScannerModule {
	
	public void scanner() {
		Scanner scanner = new Scanner(System.in);
		String line;
		while( (line = scanner.nextLine()) != null ) {
			System.out.println(line);
		}
	}

}