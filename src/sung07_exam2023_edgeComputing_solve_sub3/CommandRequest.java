package sung07_exam2023_edgeComputing_solve_sub3;

import java.util.List;

public class CommandRequest extends Command {

	String command;
	List<String> targetDevice;
	String param;

	public CommandRequest(String command, List<String> targetDevice, String param) {
		this.command = command;
		this.targetDevice = targetDevice;
		this.param = param;
	}

	public String getCommand() {
		return command;
	}

	public List<String> getTargetDevice() {
		return targetDevice;
	}

	public String getParam() {
		return param;
	}
}
