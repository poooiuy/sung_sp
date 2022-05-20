package com.lgcns.test;

public class BodyObject {
	
	String Message;
	int QueueSize;
	int ProcessTimeout;
	int MaxFailCount;
	int WaitTime;
	
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public int getQueueSize() {
		return QueueSize;
	}
	public void setQueueSize(int queueSize) {
		QueueSize = queueSize;
	}
	public int getProcessTimeout() {
		return ProcessTimeout;
	}
	public void setProcessTimeout(int processTimeout) {
		ProcessTimeout = processTimeout;
	}
	public int getMaxFailCount() {
		return MaxFailCount;
	}
	public void setMaxFailCount(int maxFailCount) {
		MaxFailCount = maxFailCount;
	}
	public int getWaitTime() {
		return WaitTime;
	}
	public void setWaitTime(int waitTime) {
		WaitTime = waitTime;
	}
	

}
