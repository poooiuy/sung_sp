/*
 * Thread 2���� ���� ��, 
 * Main�Լ��� Thread 2������ ���ÿ� 0���� 9���� ����Ͻÿ�. 
 * ��� ����Ͽ����� ������ �� �ְ� ���� �տ� [Main], [Thread1], [Thread2] ǥ�ø� �Ͻÿ�.
 */


package sung00_thread;

public class MultiThreadSample {
	
	public static void main(String[] args){
		MultiThreadClass tc1 = new MultiThreadClass("Thread1");
		MultiThreadClass tc2 = new MultiThreadClass("Thread2");
		tc1.start();
		tc2.start();
		
		try {
			for(int i=0; i < 10; i++){
				System.out.println("[Main] " + i);
				Thread.sleep(100);
			}
			tc1.join();
			tc2.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}

class MultiThreadClass extends Thread{
	
	String thread_name;
	public MultiThreadClass(String name){
		this.thread_name = name;
	}
	
	public void run(){
		for(int i=0; i < 10; i++){
			
			try {
				System.out.println("["+thread_name+"] "+i);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}