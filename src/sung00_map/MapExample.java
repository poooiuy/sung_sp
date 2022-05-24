package sung00_map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapExample {
	
	//	저장되는 순서 유지
	Map<String, Object> m = new LinkedHashMap<>();
	
	//	key로 정렬하여 저장
	Map<String, Object> m2 = new TreeMap<>();
	
	//	Thread-safe 하게 사용
	Map<String, Object> m3 = new ConcurrentHashMap<>(); 
}
