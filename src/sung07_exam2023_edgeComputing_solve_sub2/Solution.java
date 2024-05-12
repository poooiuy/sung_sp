package sung07_exam2023_edgeComputing_solve_sub2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

			List<String> resultList = new ArrayList<>();

			for (String targetDevice : targetDeviceArray) {
				writeFile(String.format("DEVICE/REQ_TO_%S.TXT", targetDevice), String.format("%s#%s", this.commandMap.get(command), parameter));
				Thread.sleep(500);
				String result = readFile(String.format("DEVICE/RES_FROM_%S.TXT", targetDevice));
				resultList.add(result);
			}
			
			System.out.println(String.join(",", resultList));
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
	
	private String readFile(String fileName) throws Exception {
		return Files.readAllLines(Paths.get(fileName)).get(0);
	}

	private void writeFile(String fileName, String content) throws Exception {
		Files.write(Paths.get(fileName), (content + "\n").getBytes(), StandardOpenOption.CREATE);
	}
}
