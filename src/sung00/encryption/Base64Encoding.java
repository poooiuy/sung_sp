package sung00.encryption;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Base64Encoding {
	
	public static void main(String[] args) {
		String TestString = "This is a Base64 test.";
		Encoder encoder = Base64.getEncoder(); 
		String encodedString;
		try {
			encodedString = encoder.encodeToString(TestString.getBytes("UTF-8"));
			System.out.println(encodedString); 
			Decoder decoder = Base64.getDecoder(); 
			byte[] decodedBytes = decoder.decode(encodedString); 
			String decodedString = new String(decodedBytes, "UTF-8"); 
			System.out.println(decodedString); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

	}

}
