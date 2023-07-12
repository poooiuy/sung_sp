package sung06_exam2022_threadWorker_sub1;

import java.util.HashMap;
import java.util.Map;

import com.lgcns.test.Worker;

public class WorkerManager {
	
	static WorkerManager wm = null;
	public Map<Integer, Worker> map = new HashMap<>();
	
	public static WorkerManager getWorkerManager() {
		if(wm == null) {
			wm = new WorkerManager();
		}
		return wm;
	}
	
	public WorkerManager() {
		Worker worker0 = new Worker(0);
		Worker worker1 = new Worker(1);
		map.put(0, worker0);
		map.put(1, worker1);
	}
	
	public Worker getWorker(int num) {
		return map.get(num);
	}
	
	
}
