package sung01.education2019.workshop.question.sub3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
					if ( busId.equals("") && (!line.equals("LOGOUT")) ) {
						busId = line;
						startTime = CardUtility.getCurrentDateTimeString();
					}else {
						switch(line) {
						case "DONE" : 
							busId = "";
							startTime = "";
							break;
						case "LOGOUT" : 
							sendToServer();
							inspectorId = "";
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
	
	public static void sendToServer() throws UnknownHostException, IOException
	{
		Socket socket = null;
		DataOutputStream os = null;
		try {
			socket = new Socket("127.0.0.1", 27016);
			os = new DataOutputStream(socket.getOutputStream());
			
			byte[] buffer = new byte[4096];
			int length;
			
			// get all the files from a directory
			File directory = new File("./src/sung01.education2019.workshop.file/" + inspectorId);
			File[] fList = directory.listFiles();
			for (File file : fList) {
				if (file.isFile()) {
					os.writeUTF(file.getName());
					os.writeInt((int) file.length());
					
					FileInputStream is = null;
					try {
						is = new FileInputStream(file.getPath());
						while ((length = is.read(buffer)) != -1) {
							os.write(buffer, 0, length);
						}
					} finally {
						if (is != null) { is.close(); }
					}
	
					// move file to backup folder
					moveFileToBackup(file.getPath(), file.getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) { os.close(); }
			if (socket != null) { socket.close(); }
		}
        
	}
	
	private static void moveFileToBackup(String path, String fileName) {
		File fileFrom = new File(path); // source
		File fileTo = new File("./src/sung01.education2019.workshop.file/BACKUP/" + fileName); // destination
		fileTo.delete();
		fileFrom.renameTo(fileTo);
	}
	
	
}