package sung00_thread;

import java.util.concurrent.locks.ReentrantLock;

public class SyncronizeThreadClass{
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new SuncronizeThreadClass());
		t1.start();
		
		try {
			t1.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class SuncronizeThreadClass implements Runnable{
	static ReentrantLock lock = new ReentrantLock();
	
	public void run() {
		lock.lock();
		try {
			//	Syncronized Target
		} finally {
			lock.unlock();
		}
		
	}
}
