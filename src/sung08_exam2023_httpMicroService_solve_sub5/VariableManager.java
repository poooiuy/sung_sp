package sung08_exam2023_httpMicroService_solve_sub5;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class VariableManager {

	private static Map<String, String> variables;

	public synchronized static String get(String key) {
		return variables.get(key);
	}

	public synchronized static String put(String key, String value) {
		return variables.put(key, value);
	}

	public synchronized static void load() throws JsonSyntaxException, IOException {
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		variables = Collections
				.synchronizedMap(new Gson().fromJson(new String(Files.readAllBytes(Paths.get("VARIABLE.JSON"))), type));
	}
}
