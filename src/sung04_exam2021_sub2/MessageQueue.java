package com.lgcns.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MessageQueue {
	
	Queue<String> queue;
	
	Map<String, QueueObject> queueManager;
	
	public MessageQueue(){
		queueManager = new HashMap<>();
	}
	
	public Map<String, QueueObject> getQueueManager() {
		return this.queueManager;
	}

	public void createQueue(String s, String string2) {
		int size = Integer.parseInt(string2);
		Queue<String> queue = new LinkedList<>();
		QueueObject qo = new QueueObject(size, queue);
		queueManager.put(s, qo);
	}

}
