package sung07_exam2023_edgeComputing_solve_sub3;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ServerCommandInfoData {
    @SerializedName("serverCommandInfo")
    private List<ServerCommandInfo> serverCommandInfoList;

    public List<ServerCommandInfo> getServerCommandInfoList() {
        return serverCommandInfoList;
    }
    
    public class ServerCommandInfo {
    	private String command;
    	private String forwardCommand;

    	public String getCommand() {
			return command;
		}
		public String getForwardCommand() {
			return forwardCommand;
		}
    }
}
