package sung05_exam2022_httpProxyServer_sub3;

public class RunManager {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("user directory : " + System.getProperty("user.dir"));
		
		ScannerModule scannerModule = new ScannerModule();
		scannerModule.scanner();
	}

}