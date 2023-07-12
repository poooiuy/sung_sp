package sung06_exam2022_threadWorker_sub3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RunManager {
	
	static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		MyClient mc = new MyClient();
		String ruleUri = "http://127.0.0.1:8080/queueInfo";
		String rule = mc.getRequest(ruleUri);
		
		System.out.println(rule);
		
		List<Processor> l = new ArrayList<>();
		
	
		Map<String, Object> jo = gson.fromJson(rule, Map.class);
		int count = (int) Math.round((double) jo.get("inputQueueCount"));
		List<String> inputUri = (List<String>) jo.get("inputQueueURIs");
		String outUri = jo.get("outputQueueURI").toString();
		
		Output.getOutput(outUri);
		
		
		WorkerManager wm = WorkerManager.getWorkerManager(count);
		
		for(int i=0; i<inputUri.size(); i++) {
			Processor p = new Processor(i, inputUri.get(i));
			p.start();	
			l.add(p);
		}
				
		for(Thread p : l) {
			p.join();
		}
		
	}
	
	
}
