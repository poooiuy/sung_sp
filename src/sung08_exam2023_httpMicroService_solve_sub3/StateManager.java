package sung08_exam2023_httpMicroService_solve_sub3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import sung08_exam2023_httpMicroService_solve_sub3.StateManager.StatesDto.StateDto;
import sung08_exam2023_httpMicroService_solve_sub3.state.ActionState;

public class StateManager {

	private static Map<String, State> states = new ConcurrentHashMap<>();

	class StatesDto {
		public Map<String, StateDto> state;

		class StateDto {
			public String type;
			public String url;
			public List<String> parameters;
		}

	}

	public static State get(String name) {
		return states.get(name);
	}

	public static void load() throws JsonSyntaxException, IOException {
//		Type type = new TypeToken<Map<String, WorkflowDto>>(){}.getType();
		StatesDto statesDto = new Gson().fromJson(new String(Files.readAllBytes(Paths.get("STATE.JSON"))),
				StatesDto.class);

		for (Entry<String, StateDto> e : statesDto.state.entrySet()) {
			states.put(e.getKey(), makeState(e.getKey(), e.getValue()));
		}
	}

	private static State makeState(String stateName, StateDto stateDto) {
		if ("action".equals(stateDto.type)) {
			return new ActionState(stateName, stateDto.url, stateDto.parameters);

		}
		return null;
	}
}
