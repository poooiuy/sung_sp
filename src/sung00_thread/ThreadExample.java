package sung00_thread;

public class ThreadExample{
	public static void main(String[] args) {
		ThreadClass threadClass = new ThreadClass();
		threadClass.start();
		
		try {
			threadClass.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadClass extends Thread{
	
	public void run() {
		System.out.println("Running..");
	}

}