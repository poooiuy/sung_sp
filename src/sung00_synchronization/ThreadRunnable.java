package sung00_synchronization;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadRunnable {
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new ThreadClass());
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadClass implements Runnable {
	static ReentrantLock lock = new ReentrantLock();

	public void run() {
		lock.lock();
		try {
			SaveFile("data");
		} finally {
			lock.unlock();
		}
	}
	
	public synchronized void SaveFile(String data)
	{
		System.out.println(data);
	}

}