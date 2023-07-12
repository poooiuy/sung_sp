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

//	join�� ��� Thread�� start �� �� join �Ѵ�.
//	thread�� run �޼ҵ� �ȿ� while ���� �ݺ����� �������� ������ �ݺ����� �ʴ´�.