package sung04_exam2021_sub4;

public class RunManager {
	

	public static void main(String[] args){
		
		try {
			HttpServer server = new HttpServer();
			server.start();
			
			CheckThread t = new CheckThread();
			t.start();
//			t.join();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}