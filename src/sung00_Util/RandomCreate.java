package sung00_Util;

import java.util.Random;

public class RandomCreate {
	
	public static void main(String[] args) {
		
		Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
        random.setSeed(System.currentTimeMillis()); //시드값 설정을 따로 할수도 있음
		
        System.out.println(random.nextLong());
	}

}
