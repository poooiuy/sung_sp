package com.lgcns.test;

import java.util.Queue;

public class QueueObject {
	
	int size;
	Queue<MessageObject> queue;
	int processTimeout;
	int maxFailCount;
	int waitTime;
	
	public QueueObject(int size
					 , Queue<MessageObject> queue
					 , int processTimeout
					 , int maxFailCount
					 , int waitTime){
		this.size = size;
		this.queue = queue;
		this.processTimeout = processTimeout;
		this.maxFailCount = maxFailCount;
		this.waitTime = waitTime;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public synchronized Queue<MessageObject> getQueue() {
		return queue;
	}

	public synchronized void setQueue(Queue<MessageObject> queue) {
		this.queue = queue;
	}

	public int getProcessTimeout() {
		return processTimeout;
	}

	public void setProcessTimeout(int processTimeout) {
		this.processTimeout = processTimeout;
	}

	public int getMaxFailCount() {
		return maxFailCount;
	}

	public void setMaxFailCount(int maxFailCount) {
		this.maxFailCount = maxFailCount;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}


	
	
}
