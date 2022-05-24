package sung00_queue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
	
	public static void main(String[] args) {
		
		//	1. Queue 생성
		Queue<Integer> queue = new LinkedList<>();
		
		
		//	2. Queue 에 값 추가
		//		2.1 add
		//			해당 큐 맨 뒤에 값 삽입
		//			값 추가 성공 시 true 반환
		//			큐가 꽉 찬 경우 IllegalStateException 에러 발생
		queue.add(1);
		
		//		2.2 off
		//			해당 큐 맨 뒤에 값 삽입
		//			값 추가 성공 시 true 반환
		//			값 추가 실패 시 false 반환
		queue.offer(2);
		
		
		//	3. Queue에 값 제거
		//		3.1 remove
		//			큐 맨 앞에 있는 값 반환 후 삭제
		//			큐가 비어 있는 경우 NoSuchElementException 에러 발생
		queue.remove();
		
		//		3.2 poll
		//			큐 맨 앞에 있는 값 반환 후 삭제
		//			큐가 비어있을 경우 null 반환		
		queue.poll();
		
		//		3.3 clear
		//			큐 비우기
		queue.clear();
		
		
		//	4. Queue의 맨 앞 값 확인
		//		4.1 element
		//			큐의 맨 앞에 있는 값 반환
		//			큐가 비어 있는 경우 NoSuchElementException 에러 발생
		queue.element();
		
		//		4.2 peek
		//			큐의 맨 앞에 있는 값 반환
		//			비어있을 경우 null 반환
		queue.peek();
		
		
	}
	
	
}