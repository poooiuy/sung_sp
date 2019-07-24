package sung00_thread;

public class ThreadRunnable {
	public static void main(String[] args) {
		ThreadClass2 threadClass = new ThreadClass2();
		Thread thread = new Thread(threadClass);
		
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


class ThreadClass2 implements Runnable{
	
	public void run() {
		System.out.println("Running..");
	}

}