package sung04_exam2021_sub1;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
	
	Queue<String> queue;
	
	public MessageQueue(){
		queue = new LinkedList<>();
	}
	
	public Queue<String> getQueue() {
		return this.queue;
	}

}
