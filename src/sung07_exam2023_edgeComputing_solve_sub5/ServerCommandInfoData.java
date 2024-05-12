package sung07_exam2023_edgeComputing_solve_sub5;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import sung07_exam2023_edgeComputing_solve_sub5.ServerCommandInfoData.ServerCommandInfo.ForwardCommandInfo;

public class ServerCommandInfoData {
    @SerializedName("serverCommandInfo")
    private List<ServerCommandInfo> serverCommandInfoList;

    public List<ServerCommandInfo> getServerCommandInfoList() {
        return serverCommandInfoList;
    }
    
    public class ServerCommandInfo {
    	private String command;
    	private List<ForwardCommandInfo> forwardCommandInfo;

    	public String getCommand() {
			return command;
		}
		public List<ForwardCommandInfo> getForwardCommandInfo() {
			return forwardCommandInfo;
		}
		
		public class ForwardCommandInfo {
			private String type;
			private List<String> forwardCommand;

			public String getType() {
				return type;
			}

			public List<String> getForwardCommand() {
				return forwardCommand;
			}
		}
    }
    
    public List<String> getForwardCommand(String command, String type) {
    	List<String> forwardCommand = null;
		for (ServerCommandInfo commandInfo : serverCommandInfoList) {
			if (command.equals(commandInfo.getCommand())) {
				for (ForwardCommandInfo chainedCommandInfo : commandInfo.getForwardCommandInfo()) {
					if (chainedCommandInfo.getType().contentEquals(type)) {
						forwardCommand = chainedCommandInfo.getForwardCommand();
					}
				}
			}
		}
		return forwardCommand;
    }
}
