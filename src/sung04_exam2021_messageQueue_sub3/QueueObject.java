package sung04_exam2021_messageQueue_sub3;

import java.util.Queue;

public class QueueObject {
	
	int size;
	Queue<MessageObject> queue;
	
	public QueueObject(int size, Queue<MessageObject> queue){
		this.size = size;
		this.queue = queue;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Queue<MessageObject> getQueue() {
		return queue;
	}

	public void setQueue(Queue<MessageObject> queue) {
		this.queue = queue;
	}
	
	
	

}
