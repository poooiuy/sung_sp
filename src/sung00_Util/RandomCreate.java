package sung00_Util;

import java.util.Random;

public class RandomCreate {
	
	public static void main(String[] args) {
		
		Random random = new Random(); //���� ��ü ����(����Ʈ �õ尪 : ����ð�)
        random.setSeed(System.currentTimeMillis()); //�õ尪 ������ ���� �Ҽ��� ����
		
        System.out.println(random.nextLong());
	}

}
