package sung04_exam2021_messageQueue_sub2;

import java.util.Queue;

public class QueueObject {
	
	int size;
	Queue<String> queue;
	
	public QueueObject(int size, Queue<String> queue){
		this.size = size;
		this.queue = queue;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Queue<String> getQueue() {
		return queue;
	}

	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}
	

}
