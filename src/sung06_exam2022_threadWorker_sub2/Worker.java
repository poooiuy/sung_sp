package sung06_exam2022_threadWorker_sub2;

import java.util.List;

import com.lgcns.test.AbstractWorker;

/* ----------------------------------------------------------------------------
 * 
 * Worker.java - removeExpiredStoreItems() ����, �� �� ���� ����
 * 
 * ----------------------------------------------------------------------------
 */
public class Worker extends AbstractWorker {
	
	/*
	 * �� Worker ����
	 * - <Queue ��ȣ>�� �Ķ���ͷ� �Ͽ� Worker �ν��Ͻ� ����
	 */
	public Worker(int queueNo) {
		super(queueNo);
	}

	/*
	 * �� ����� Store Item ����
	 * - �Էµ� Timestamp�� Store Item�� Timestamp���� ���̰� ����ð�(3000)�� �ʰ��ϸ� Store���� ����
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
