package sung06_exam2022_threadWorker_sub3;

import java.util.HashMap;
import java.util.Map;


public class WorkerManager {
	
	static WorkerManager wm = null;
	public Map<Integer, Worker> map = new HashMap<>();
	
	public static WorkerManager getWorkerManager(int i) {
		if(wm == null) {
			wm = new WorkerManager(i);
		}
		return wm;
	}
	
	public static WorkerManager getWorkerManager() {
		return wm;
	}
	
	public WorkerManager(int count) {
		for(int i=0; i<count; i++) {
			Worker worker = new Worker(i);
			map.put(i, worker);
		}
	}
	
	public Worker getWorker(int num) {
		return map.get(num);
	}
	
	
}
