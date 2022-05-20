package com.lgcns.test;

import java.util.Scanner;

public class SystemInput {

	static MessageQueue mq = new MessageQueue();

	public void run() {
		
		String inputString = "";
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			inputString = scanner.nextLine();
//			System.out.println(inputString);
			String cmd = inputString.split(" ")[0];
//			String message = inputString.substring(inputString.indexOf(" ")+1, inputString.length());
//			System.out.println(message);
			switch(cmd) {
			case "CREATE" :
				String[] s = inputString.split(" ");
				if(mq.getQueueManager().containsKey(s[1])) {
					System.out.println("Queue Exist");
				} else {
					mq.createQueue(s[1], s[2]);
				}
				break;
			case "SEND" :
				String[] s2 = inputString.split(" ");
				QueueObject qo = mq.getQueueManager().get(s2[1]);
				if( qo.getQueue().size() >= qo.getSize()) {
					System.out.println("Queue Full");
				}else {
					qo.getQueue().add(s2[2]);
				}
				break;
			case "RECEIVE" :
				String[] s3 = inputString.split(" ");
				QueueObject qo2 = mq.getQueueManager().get(s3[1]);
				System.out.println(qo2.getQueue().poll());
				break;
			case "EXIT" :
				scanner.close();
				return;
			default :
				break;
			}
		}
	}
}
