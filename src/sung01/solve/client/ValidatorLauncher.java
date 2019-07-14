package sung01.solve.client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Scanner;

public class ValidatorLauncher {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ParseException {
		Scanner scanner = new Scanner(System.in);
		String line, strId, strPassword;

		while (true) {
			Validator validator = new Validator();
			
			line = scanner.nextLine(); // id, password
			if (line.length() < 10) {
				System.out.println("Wrong ID Password");
				continue;
			}
			strId = line.substring(0, 8);
			strPassword = line.substring(9);

			if (validator.login(strId, strPassword)) {
				System.out.println("LOGIN SUCCESS");
				
				// Inspection
				while (true) {
					line = scanner.nextLine();	// busId
					
					if (line.equals("LOGOUT")) {
						validator.logout();
						break;
					}
					
					validator.getOnBus(line);

					// Card Validation
					while (true) {
						line = scanner.nextLine();	//cardInfo
						
						if (line.equals("DONE")) {
							validator.getOffBus();
							break;
						}
						
						validator.inspectCard(line);
					}
				}
			} else {
				System.out.println("LOGIN FAIL");
			}
		}
	}
}