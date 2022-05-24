package sung04_exam2021_sub1;

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
			String message = inputString.substring(inputString.indexOf(" ")+1, inputString.length());
//			System.out.println(message);
			switch(cmd) {
			case "SEND" :
				mq.getQueue().add(message);
				break;
			case "RECEIVE" :
				String out = mq.getQueue().poll();
				System.out.println(out);
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
