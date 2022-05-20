package com.lgcns.test;

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