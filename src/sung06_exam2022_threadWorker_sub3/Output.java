package sung06_exam2022_threadWorker_sub3;

public class Output {
	
	
	String url;
	static Output o = null;
	
	
	
	public static Output getOutput(String s) {
		if(o == null) {
			o = new Output(s);
		}
		return o;
	}
	
	public static Output getOutput() {
		return o;
	}

	public Output(String s) {
		this.url = s;
	}
	
	
	public void send(String val) throws Exception {
		MyClient mc = new MyClient();
		mc.postRequest(url, val);
	}
}
