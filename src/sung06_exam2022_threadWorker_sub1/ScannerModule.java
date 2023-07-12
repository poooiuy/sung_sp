package sung06_exam2022_threadWorker_sub1;

import java.util.Scanner;

import com.lgcns.test.Worker;

public class ScannerModule {
	
	public void scanner() {
		Scanner scanner = new Scanner(System.in);
		WorkerManager wm = WorkerManager.getWorkerManager();
		String line;
		while( (line = scanner.nextLine()) != null ) {
			String[] param = line.split(" ");
			Worker worker = wm.getWorker(Integer.parseInt(param[0]));
			String out = worker.run(param[1]);
			if(out != null) {
				System.out.println(out);
			}
		}
	}

}

