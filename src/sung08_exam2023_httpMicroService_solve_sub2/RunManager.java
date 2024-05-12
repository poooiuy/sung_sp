package sung08_exam2023_httpMicroService_solve_sub2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RunManager {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		Map<String, State> states = new HashMap<>();
		for (String line : Files.readAllLines(Paths.get("STATE.TXT"))) {
			String[] elements = line.split("#");
			String name = elements[0];
			String type = elements[1];
			String url = elements[2];
			String[] keys = null;
			if (elements.length > 3) {
				keys = elements[3].split(",");
			}
			states.put(name, new State(name, type, url, keys));
		}

		VariableManager.load();

		while (true) {
			String request = scanner.nextLine();

			State state = states.get(request);

			String print = state.getType() + " " + state.getUrl();

			String[] keys = state.getKeys();
			if (keys != null) {
				for (int i = 0; i < keys.length; i++) {
					if (i == 0) {
						print += "?";
					} else if (i > 0) {
						print += "&";
					}
					print += keys[i] + "=" + VariableManager.get(keys[i]);
				}
			}

			System.out.println(print);
		}
	}
}
