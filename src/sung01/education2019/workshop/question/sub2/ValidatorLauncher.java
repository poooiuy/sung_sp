package sung01.education2019.workshop.question.sub2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import sung01.solve.client.CardUtility;

public class ValidatorLauncher{
	
	static Map<String, String> map = new HashMap<>();
	static String inspectorId = "";
	static String startTime = "";
	static String busId = "";
	
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ParseException {  
		
		execute();
		
	}
	
	private static void execute() throws IOException, NoSuchAlgorithmException, ParseException {
		
		getInsFile();
		
		cmd();
		
	}
	
	//	INSPECTOR FILE READ
	private static void getInsFile() throws IOException{
		
		String line = null;
		Map<String, String> result = new HashMap<>();
		FileReader fileReader = new FileReader("./src/sung01.education2019.workshop.file/CLIENT/INSPECTOR.TXT");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		while((line = bufferedReader.readLine()) != null) {
			String[] l = line.split(" ");
			result.put(l[0], l[1]);
			
		}
		bufferedReader.close();
		
		map = result;
		
	}
	
	//	CMD
	private static void cmd() throws NoSuchAlgorithmException, IOException, ParseException {
		Scanner scanner = new Scanner(System.in);
		String line = "";
		boolean run = true;
		
		while(run) {
			if( (line = scanner.nextLine()) != null) {
				if( inspectorId.equals("") ) {
					login(line);
				}else {
					if (busId.equals("")) {
						busId = line;
						startTime = CardUtility.getCurrentDateTimeString();
					}else {
						switch(line) {
						case "DONE" : 
							busId = "";
							startTime = "";
							break;
						case "LOGOUT" : 
							run = false;
							break;
						default : 
							checkLogic(line);
							break;
						}
					}

				}
			}
			
		}
		scanner.close();
	}
	
	//	LOGIN
	private static void login(String line) throws NoSuchAlgorithmException {

		String[] input = line.split(" ");
		String id = input[0];
		String pwd = CardUtility.passwordEncryption(input[1]);
		
		if(!map.containsKey(id)) {
			System.out.println("LOGIN FAIL");
		}else {
			if(map.get(id).equals(pwd)) {
				System.out.println("LOGIN SUCCESS");
				inspectorId = id;
			}else {
				System.out.println("LOGIN FAIL");
			}
		}
			
	}
	
	
	private static void checkLogic(String line) throws IOException, ParseException {
		
		// Create Folder
		File destFolder = new File("./src/sung01.education2019.workshop.file/" + inspectorId);
		if(!destFolder.exists()) {
			destFolder.mkdirs(); 
		}
		
		//	Logic
		String a = line.substring(0,8);
		String b = line.substring(8,15);
		String c = line.substring(15,16);
		String d = line.substring(16,30);
		
		String inspectTime = CardUtility.getCurrentDateTimeString();
		
		String validationCode = "R1";
		
		if(!b.equals(busId)) {
			validationCode = "R2";
		}else if(!c.equals("N")) {
			validationCode = "R3";
		}else if(CardUtility.hourDiff(inspectTime, d) > 3) {
			validationCode = "R4";
		}
		
		// File Writing
		String strFilename = destFolder + "/" + inspectorId + "_" + startTime + ".TXT";
		File file = new File(strFilename);
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(strFilename, true);
			fw.write(inspectorId + "#" + busId + "#" + line + "#" + validationCode + "#" + inspectTime + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) { fw.close(); }
		}
		
	}
	
	
}