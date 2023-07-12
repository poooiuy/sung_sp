package sung04_exam2021_messageQueue_sub4;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CheckThread extends Thread{
	
	MessageQueue mq = MessageQueue.getInstance();
	Map<String, QueueObject> m;

	public void run() {
		
		while(true) {
//			System.out.println("CheckThread");
			m = mq.getQueueManager();
			for(String s : m.keySet()) {
				QueueObject qo = m.get(s);
				Queue<MessageObject> queueNew = new LinkedList<>();
				
				int size = qo.getSize();
				Queue<MessageObject> queue = qo.getQueue();
				int processTimeout = qo.getProcessTimeout();
				int maxFailCount = qo.getMaxFailCount();
				int waitTime = qo.getWaitTime();
				
				if( processTimeout > 0 ) {
					
					while( queue.size()>0 ) {
						MessageObject mo = queue.poll();
						if(mo.getSecondTimestamp() != 0L) {
//							System.out.println(mo.getMessage() + ":" + (System.currentTimeMillis() - mo.getSecondTimestamp()));
							if( (System.currentTimeMillis() - mo.getSecondTimestamp()) > (processTimeout*1000) ) {
								
								mo.setSendYn("N");
								mo.setFailCount(mo.getFailCount()+1);
								mo.setSecondTimestamp(0);
								if(mo.getFailCount() > maxFailCount) {
									mq.addDeadQueue(s, mo);
									continue;
								}
							}
							queueNew.add(mo);
						}else {
							queueNew.add(mo);
						}
					}
				}
				QueueObject qoNew = new QueueObject(size, queueNew, processTimeout, maxFailCount, waitTime);
				m.put(s, qoNew);
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
