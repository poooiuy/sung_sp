package sung07_exam2023_edgeComputing_solve_sub5;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DeviceCommandInfoData {
    @SerializedName("deviceCommandInfo")
    private List<DeviceCommandInfo> deviceCommandInfoList;

    public List<DeviceCommandInfo> getDeviceCommandInfoList() {
        return deviceCommandInfoList;
    }
    
    public class DeviceCommandInfo {
    	private String command;
    	private String forwardCommand;
    	private String computingLib;

    	public String getCommand() {
			return command;
		}

    	public String getForwardCommand() {
			return forwardCommand;
		}

    	public String getComputingLib() {
			return computingLib;
		}
    }
    
    public String getComputingLib(String command) {
		for (DeviceCommandInfo commandInfo : deviceCommandInfoList) {
			if (command.equals(commandInfo.getCommand())) {
				return commandInfo.getComputingLib();
			}
		}
		return null;
    }
    
    public String getForwardCommand(String command) {
		for (DeviceCommandInfo commandInfo : deviceCommandInfoList) {
			if (command.equals(commandInfo.getCommand())) {
				return commandInfo.getForwardCommand();
			}
		}
		return null;
    }
}
