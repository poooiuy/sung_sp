package sung00_map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapExample {
	
	//	����Ǵ� ���� ����
	Map<String, Object> m = new LinkedHashMap<>();
	
	//	key�� �����Ͽ� ����
	Map<String, Object> m2 = new TreeMap<>();
	
	//	Thread-safe �ϰ� ���
	Map<String, Object> m3 = new ConcurrentHashMap<>(); 
}
