/*
 * Mutex를 사용하여 Main과 2개의 Thread함수에서 다음과 같이 1~30까지 숫자를 연속으로 출력하게 하시오.
 */

package sung00.thread;

import java.util.concurrent.locks.ReentrantLock;

public class SyncronizeThreadSample {
	public static void main(String[] args) {

		SyncronizeThreadTestClass tc1 = new SyncronizeThreadTestClass("Thread1");
		SyncronizeThreadTestClass tc2 = new SyncronizeThreadTestClass("Thread2");
		SyncronizeThreadTestClass main = new SyncronizeThreadTestClass("Main");
		tc1.start();
		tc2.start();
		main.start();
		try {
			tc1.join();
			tc2.join();
			main.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

class SyncronizeThreadTestClass extends Thread {

	String thread_name;

	public SyncronizeThreadTestClass(String name) {
		this.thread_name = name;
	}

	static ReentrantLock lock = new ReentrantLock();

	public synchronized void run() {
		lock.lock();
		System.out.println("[" + thread_name + "] ");
		for (int i = 1; i < 31; i++) {
			try {
				if (i == 30) {
					System.out.println(" " + i);
				} else {
					System.out.print(" " + i);
				}
//						Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lock.unlock();
	}


}