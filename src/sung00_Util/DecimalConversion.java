package sung00_Util;

public class DecimalConversion {
	
	public static void main(String[] args) {
		
		//	10������ ��ȯ
		int num = 158;
		String binary = Integer.toBinaryString(num);
		String octa = Integer.toOctalString(num);
		String hexa = Integer.toHexString(num);
		
		System.out.println(binary);
		System.out.println(octa);
		System.out.println(hexa);
		
		//	10������ ��ȯ
		System.out.println(Integer.parseInt(binary, 2));
		System.out.println(Integer.parseInt(octa, 8));
		System.out.println(Integer.parseInt(hexa, 16));
	}

}
