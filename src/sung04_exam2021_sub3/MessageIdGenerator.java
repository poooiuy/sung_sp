package sung04_exam2021_sub3;

public class MessageIdGenerator {
	
	static int i = 0;
	
	public synchronized static String getMessageId() {
		i = i+1;
		return i+"";
	}

}
