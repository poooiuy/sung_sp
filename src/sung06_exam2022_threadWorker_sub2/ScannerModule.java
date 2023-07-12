package sung06_exam2022_threadWorker_sub2;

import java.util.Scanner;



public class ScannerModule {
	
	public void scanner() {
		Scanner scanner = new Scanner(System.in);
		WorkerManager wm = WorkerManager.getWorkerManager();
		String line;
		while( (line = scanner.nextLine()) != null ) {
			String[] param = line.split(" ");
			Worker worker = wm.getWorker(Integer.parseInt(param[1]));
			String out = worker.run(Long.parseLong(param[0]), param[2]);
			if(out != null) {
				System.out.println(out);
			}
		}
	}

}

