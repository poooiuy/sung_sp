package sung06_exam2022_threadWorker_sub2;

import java.util.List;

import com.lgcns.test.AbstractWorker;

/* ----------------------------------------------------------------------------
 * 
 * Worker.java - removeExpiredStoreItems() 구현, 그 외 변경 금지
 * 
 * ----------------------------------------------------------------------------
 */
public class Worker extends AbstractWorker {
	
	/*
	 * ※ Worker 생성
	 * - <Queue 번호>를 파라미터로 하여 Worker 인스턴스 생성
	 */
	public Worker(int queueNo) {
		super(queueNo);
	}

	/*
	 * ※ 만료된 Store Item 제거
	 * - 입력된 Timestamp와 Store Item의 Timestamp간의 차이가 만료시간(3000)을 초과하면 Store에서 제거
	 */
	public void removeExpiredStoreItems(long timestamp, List<String> store) {
		for(String s : store) {
			String[] strArr = s.split("#");
			if(timestamp - Long.parseLong(strArr[0]) > 3000) {
				store.remove(s);
			}
		}
	}
}
