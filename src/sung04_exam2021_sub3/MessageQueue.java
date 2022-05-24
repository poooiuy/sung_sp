package sung04_exam2021_sub3;

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

	public synchronized void createQueue(String s, String string2) {
		int size = Integer.parseInt(string2);
		Queue<MessageObject> queue = new LinkedList<>();
		QueueObject qo = new QueueObject(size, queue);
		queueManager.put(s, qo);
	}
	
	public synchronized MessageObject receive(String queueName) {
		
		MessageObject mo = null;
		
		QueueObject qo = queueManager.get(queueName);
		
		Queue<MessageObject> queue = qo.getQueue();
		int size = qo.getSize();
		
		Queue<MessageObject> queueNew = new LinkedList<>();
		
		while(queue.size()>0) {
			MessageObject origin = queue.poll();
			if( (mo == null) && (origin.getSendYn().equals("N")) ) {
				mo = origin;
				origin.setSendYn("Y");
//				System.out.println(origin.getMessage());
			}
			queueNew.add(origin);
		}
//		System.out.println(queueNew.size());
		QueueObject qoNew = new QueueObject(size, queueNew);
		queueManager.put(queueName, qoNew);
		
		return mo;
	}
	
	public synchronized void ack(String queueName, String messageId) {
		
		QueueObject qo = queueManager.get(queueName);
		
		Queue<MessageObject> queue = qo.getQueue();
		int size = qo.getSize();
		
		Queue<MessageObject> queueNew = new LinkedList<>();
		
		while(queue.size()>0) {
			MessageObject origin = queue.poll();
			if( !origin.getMessageId().equals(messageId) ) {
				queueNew.add(origin);
			}
		}
//		System.out.println(queueNew.size());
		QueueObject qoNew = new QueueObject(size, queueNew);
		queueManager.put(queueName, qoNew);
	}
	
	public synchronized void fail(String queueName, String messageId) {
		
		QueueObject qo = queueManager.get(queueName);
		
		Queue<MessageObject> queue = qo.getQueue();
		int size = qo.getSize();
		
		Queue<MessageObject> queueNew = new LinkedList<>();
		
		while(queue.size()>0) {
			MessageObject origin = queue.poll();
			if( origin.getMessageId().equals(messageId) ) {
				origin.setSendYn("N");
			}
			queueNew.add(origin);
		}
		QueueObject qoNew = new QueueObject(size, queueNew);
		queueManager.put(queueName, qoNew);
	}

}
