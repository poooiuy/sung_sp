package sung08_exam2023_httpMicroService_solve_sub1;

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
			states.put(name, new State(name, type, url));
		}

		while (true) {
			String request = scanner.nextLine();
			State state = states.get(request);
			System.out.println(state.getType() + " " + state.getUrl());
		}
	}
}
