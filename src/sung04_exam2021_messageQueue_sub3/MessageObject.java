package sung04_exam2021_messageQueue_sub3;

public class MessageObject {
	
	String message;
	String messageId;
	String sendYn;
	
	public MessageObject(String message) {
		this.message = message;
		this.messageId = MessageIdGenerator.getMessageId();
		this.sendYn = "N";
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

}