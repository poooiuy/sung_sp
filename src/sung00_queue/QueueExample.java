package sung00_queue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
	
	public static void main(String[] args) {
		
		//	1. Queue ����
		Queue<Integer> queue = new LinkedList<>();
		
		
		//	2. Queue �� �� �߰�
		//		2.1 add
		//			�ش� ť �� �ڿ� �� ����
		//			�� �߰� ���� �� true ��ȯ
		//			ť�� �� �� ��� IllegalStateException ���� �߻�
		queue.add(1);
		
		//		2.2 off
		//			�ش� ť �� �ڿ� �� ����
		//			�� �߰� ���� �� true ��ȯ
		//			�� �߰� ���� �� false ��ȯ
		queue.offer(2);
		
		
		//	3. Queue�� �� ����
		//		3.1 remove
		//			ť �� �տ� �ִ� �� ��ȯ �� ����
		//			ť�� ��� �ִ� ��� NoSuchElementException ���� �߻�
		queue.remove();
		
		//		3.2 poll
		//			ť �� �տ� �ִ� �� ��ȯ �� ����
		//			ť�� ������� ��� null ��ȯ		
		queue.poll();
		
		//		3.3 clear
		//			ť ����
		queue.clear();
		
		
		//	4. Queue�� �� �� �� Ȯ��
		//		4.1 element
		//			ť�� �� �տ� �ִ� �� ��ȯ
		//			ť�� ��� �ִ� ��� NoSuchElementException ���� �߻�
		queue.element();
		
		//		4.2 peek
		//			ť�� �� �տ� �ִ� �� ��ȯ
		//			������� ��� null ��ȯ
		queue.peek();
		
		
	}
	
	
}