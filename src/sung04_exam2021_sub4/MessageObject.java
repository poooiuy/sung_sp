package sung04_exam2021_sub4;

public class MessageObject {
	
	String message;
	String messageId;
	String sendYn;
	String result;
	long firstTimestamp;
	long secondTimestamp;
	int failCount;
	
	public MessageObject(String message) {
		this.message = message;
		this.messageId = MessageIdGenerator.getMessageId();
		this.sendYn = "N";
		this.result = "";
		this.firstTimestamp = System.currentTimeMillis();
		this.secondTimestamp = 0L;
		this.failCount = 0;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public long getFirstTimestamp() {
		return firstTimestamp;
	}
	public void setFirstTimestamp(long firstTimestamp) {
		this.firstTimestamp = firstTimestamp;
	}
	public long getSecondTimestamp() {
		return secondTimestamp;
	}
	public void setSecondTimestamp(long secondTimestamp) {
		this.secondTimestamp = secondTimestamp;
	}

}