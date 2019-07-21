package sung01.education2019.workshop.question.sub1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import sung01.solve.client.CardUtility;

public class ValidatorLauncher
{
	static Map<String, String> map = new HashMap<>();
	
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {  
		
		execute();
		
		
		
	}
	
	private static void execute() throws IOException, NoSuchAlgorithmException {
		
		getInsFile();
		
		login();
		
		
		
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
	
	//	LOGIN
	private static void login() throws NoSuchAlgorithmException {
		
		Scanner scanner = new Scanner(System.in);
		String line = "";
		
		while(true) {
			line = scanner.nextLine();
			String[] input = line.split(" ");
			String id = input[0];
			String pwd = CardUtility.passwordEncryption(input[1]);
			
			if(!map.containsKey(id)) {
				System.out.println("LOGIN FAIL");
				continue;
			}else {
				if(map.get(id).equals(pwd)) {
					System.out.println("LOGIN SUCCESS");
					break;
				}else {
					System.out.println("LOGIN FAIL");
					continue;
				}
			}
			
		}
	}
}