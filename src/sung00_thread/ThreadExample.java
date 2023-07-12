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
		while(true) {
			System.out.println("Running..");
		}
	}

}

//	join은 모든 Thread를 start 한 후 join 한다.
//	thread는 run 메소드 안에 while 같은 반복문을 실행하지 않으면 반복되지 않는다.