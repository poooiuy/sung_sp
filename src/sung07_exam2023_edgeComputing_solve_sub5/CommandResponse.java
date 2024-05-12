package sung07_exam2023_edgeComputing_solve_sub5;

import java.util.List;

public class CommandResponse extends Command {
	
	List<String> result;
	
	public CommandResponse(List<String> result) {
		this.result = result;
	}

	public List<String> getResult() {
		return result;
	}
}
