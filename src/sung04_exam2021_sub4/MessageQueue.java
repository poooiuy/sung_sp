package sung04_exam2021_sub4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MessageQueue {
	
	static MessageQueue inst = null;
	
	static Map<String, QueueObject> queueManager;
	Map<String, Queue<MessageObject>> deadQueueManager;
	
	
	public MessageQueue(){
		queueManager = new HashMap<>();
		deadQueueManager = new HashMap<>();
	}
	
	public static MessageQueue getInstance() {
		if(inst == null){ 
			inst = new MessageQueue();
		}
		return inst;
	}
	
	public synchronized Map<String, QueueObject> getQueueManager() {
		return this.queueManager;
	}
	
	public synchronized Map<String, Queue<MessageObject>> getDeadQueueManager() {
		return this.deadQueueManager;
	}
	
	public void addDeadQueue(String queueName, MessageObject mo) {
		if( deadQueueManager.containsKey(queueName) ){
			deadQueueManager.get(queueName).add(mo);
		}else {
			Queue<MessageObject> q = new LinkedList<>();
			q.add(mo);
			deadQueueManager.put(queueName, q);
		}
	}

	public synchronized void createQueue(String s, BodyObject bodyObject) {
		
		int size = bodyObject.getQueueSize();
		Queue<MessageObject> queue = new LinkedList<>();
		int processTimeout = bodyObject.getProcessTimeout();
		int maxFailCount = bodyObject.getMaxFailCount();
		int waitTime = bodyObject.getWaitTime();
		
		QueueObject qo = new QueueObject(size, queue, processTimeout, maxFailCount, waitTime);
		queueManager.put(s, qo);
	}
	
	public synchronized MessageObject receive(String queueName) {
		
		MessageObject mo = null;
		
		QueueObject qo = queueManager.get(queueName);
		
		int size = qo.getSize();
		Queue<MessageObject> queue = qo.getQueue();
		int processTimeout = qo.getProcessTimeout();
		int maxFailCount = qo.getMaxFailCount();
		int waitTime = qo.getWaitTime();
		
		Queue<MessageObject> queueNew = new LinkedList<>();
		
		while(queue.size()>0) {
			MessageObject origin = queue.poll();
			if( (mo == null) && (origin.getSendYn().equals("N")) ) {
				mo = origin;
				origin.setSendYn("Y");
				origin.setSecondTimestamp(System.currentTimeMillis());
//				System.out.println(qo.getQueue().size());
//				System.out.println(origin.getMessage());
			}
			queueNew.add(origin);
		}
		QueueObject qoNew = new QueueObject(size, queueNew, processTimeout, maxFailCount, waitTime);
		queueManager.put(queueName, qoNew);
		
		return mo;
	}
	
	public synchronized void ack(String queueName, String messageId) {
		
		QueueObject qo = queueManager.get(queueName);
		
		int size = qo.getSize();
		Queue<MessageObject> queue = qo.getQueue();
		int processTimeout = qo.getProcessTimeout();
		int maxFailCount = qo.getMaxFailCount();
		int waitTime = qo.getWaitTime();
		
		Queue<MessageObject> queueNew = new LinkedList<>();
		
		while(queue.size()>0) {
			MessageObject origin = queue.poll();
			if( !origin.getMessageId().equals(messageId) ) {
				queueNew.add(origin);
			}
		}
		QueueObject qoNew = new QueueObject(size, queueNew, processTimeout, maxFailCount, waitTime);
		queueManager.put(queueName, qoNew);
	}
	
	public synchronized void fail(String queueName, String messageId) {
		
		QueueObject qo = queueManager.get(queueName);
		
		int size = qo.getSize();
		Queue<MessageObject> queue = qo.getQueue();
		int processTimeout = qo.getProcessTimeout();
		int maxFailCount = qo.getMaxFailCount();
		int waitTime = qo.getWaitTime();
		
		Queue<MessageObject> queueNew = new LinkedList<>();
		
		while(queue.size()>0) {
			MessageObject origin = queue.poll();
			if( origin.getMessageId().equals(messageId) ) {
				origin.setSendYn("N");
				origin.setFailCount(origin.getFailCount()+1);
				origin.setSecondTimestamp(0);
				if(origin.getFailCount() > qo.getMaxFailCount()) {
					deadQueueManager.get(queueName).add(origin);
					continue;
				}
			}
			queueNew.add(origin);
		}
		QueueObject qoNew = new QueueObject(size, queueNew, processTimeout, maxFailCount, waitTime);
		queueManager.put(queueName, qoNew);
	}

}
