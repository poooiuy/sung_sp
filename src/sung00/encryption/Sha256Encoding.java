package sung00.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Encoding {

	public static void main(String[] args) {
		String input = "1234";
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA-256");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xFF) + 0x100, 16).substring(1));
			}
			System.out.println(sb.toString());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
