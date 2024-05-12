package sung08_exam2023_httpMicroService_solve_sub4;

import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

public class Workflow {

	private String startFrom;
	private Set<State> states;
	private List<String> responseFields;

	public Workflow(String startFrom, Set<State> states, List<String> responseFields) {
		super();
		this.startFrom = startFrom;
		this.states = states;
		this.responseFields = responseFields;
	}

	public JsonObject run() throws Exception {
		String next = startFrom;
		while (!"end".equals(next)) {
			State startState = getStartState(next);
			System.out.println("next:" + next);
			next = startState.run();
		}
		return getResponseValues();
	}

	private JsonObject getResponseValues() {
		JsonObject responseValues = new JsonObject();
		for (String responseField : responseFields) {
			responseValues.addProperty(responseField, VariableManager.get(responseField));
		}
		return responseValues;
	}

	private State getStartState(String stateName) {
		for (State state : states) {
			if (stateName.equals(state.getName()))
				return state;
		}
		return null;
	}
}
