package sung08_exam2023_httpMicroService_solve_sub2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class VariableManager {

	private static Map<String, String> variables = new HashMap<>();

	public static String get(String key) {
		return variables.get(key);
	}

	public static String put(String key, String value) {
		return variables.put(key, value);
	}

	public static void load() throws Exception {
		for (String line : Files.readAllLines(Paths.get("VARIABLE.TXT"))) {
			String[] elements = line.split("#");
			String name = elements[0];
			String value = elements[1];
			variables.put(name, value);
		}
	}
}
