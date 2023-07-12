package sung06_exam2022_threadWorker_sub3;

import java.util.Map;

import com.google.gson.Gson;

public class Processor extends Thread {
	
	int num;
	String url;
	Gson gson = new Gson();
	
	public Processor(int num, String url) {
		this.num = num;
		this.url = url;
	}
	
	public void run() {
		while(true) {
//			System.out.println("--------"+num+":"+url);
			try {
			
			MyClient mc = new MyClient();
			
			String res = "";
				res = mc.getRequest(url);
			System.out.println("res:"+res);
			if(res != null && !res.equals("")) {
				Map<String, Object> jo = gson.fromJson(res, Map.class);		
				String out = WorkerManager.getWorkerManager().getWorker(num).run(Math.round((double) jo.get("timestamp")), jo.get("value").toString());
				System.out.println("out:"+out);
				if(out != null){
					Output.getOutput().send(out);
				}
			}
			
			
			
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
