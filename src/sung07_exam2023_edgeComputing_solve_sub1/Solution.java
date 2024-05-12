package sung07_exam2023_edgeComputing_solve_sub1;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {

	private Map<String, String> commandMap;

	public void run() throws Exception {
		loadCommandInfo();
		
		try (Scanner scanner = new Scanner(System.in)) {
			String[] requestArray = scanner.next().split("#");

			String command = requestArray[0];
			String[] targetDeviceArray = requestArray[1].split(",");
			String parameter = requestArray[2];

			for (String targetDevice : targetDeviceArray) {
				System.out.println(String.format("%s:%s#%s", targetDevice, this.commandMap.get(command), parameter));
			}
		}
	}

	private void loadCommandInfo() throws Exception {
		this.commandMap = new HashMap<>();

		try (Scanner scanner = new Scanner(new File("INFO/SERVER_COMMAND.TXT"))) {
			while (scanner.hasNext()) {
				String[] stringArray = scanner.next().split("#");
				this.commandMap.put(stringArray[0], stringArray[1]);
			}
		}
	}
}
