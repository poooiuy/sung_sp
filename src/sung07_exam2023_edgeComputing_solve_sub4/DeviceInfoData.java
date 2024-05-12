package sung07_exam2023_edgeComputing_solve_sub4;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import sung07_exam2023_edgeComputing_solve_sub4.DeviceInfoData.DeviceInfo.Device;

public class DeviceInfoData {
	@SerializedName("deviceInfo")
	private List<DeviceInfo> deviceInfoList;

	public List<DeviceInfo> getDeviceInfoList() {
		return deviceInfoList;
	}

	public class DeviceInfo {
		private String type;
		private List<Device> deviceList;
		private long parallelProcessingCount;

		public String getType() {
			return type;
		}

		public List<Device> getDeviceList() {
			return deviceList;
		}

		public long getParallelProcessingCount() {
			return parallelProcessingCount;
		}

		public class Device {
			private String device;
			private String hostname;
			private int port;

			public String getDevice() {
				return device;
			}

			public String getHostname() {
				return hostname;
			}

			public int getPort() {
				return port;
			}
		}
	}
	
	public String getType(String deviceName) {
		for (DeviceInfo deviceInfo : deviceInfoList) {
			for (Device device : deviceInfo.getDeviceList()) {
				if (device.getDevice().contentEquals(deviceName)) {
					return deviceInfo.getType();
				}
			}
		}
		return null;
	}
	
	public Device getDevice(String deviceName) {
		for (DeviceInfo deviceInfo : deviceInfoList) {
			for (Device device : deviceInfo.getDeviceList()) {
				if (device.getDevice().contentEquals(deviceName)) {
					return device;
				}
			}
		}
		return null;
	}
}
