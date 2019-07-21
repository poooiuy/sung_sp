package sung01.education2019.workshop.question.sub2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CardUtility {
	
	
	public static String passwordEncryption(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xFF) + 0x100, 16).substring(1).toUpperCase());
		}

		return sb.toString();
	}

	public static long hourDiff(String strTime2, String strTime1) throws ParseException {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		java.util.Date date1 = transFormat.parse(strTime1);
		java.util.Date date2 = transFormat.parse(strTime2);

		long gap = date2.getTime() - date1.getTime();

		return gap / 60 / 60 / 1000;
	}

	public static void intToByte(byte[] buffer, int offset, int num) {
		buffer[offset + 3] = (byte) (num >> 24);
		buffer[offset + 2] = (byte) (num >> 16);
		buffer[offset + 1] = (byte) (num >> 8);
		buffer[offset + 0] = (byte) (num);
	}

	public static int byteToInt(byte[] buffer, int offset) {
		int nRet = ((((int) buffer[offset + 3] & 0xff) << 24)
				| (((int) buffer[offset + 2] & 0xff) << 16)
				| (((int) buffer[offset + 1] & 0xff) << 8)
				| (((int) buffer[offset] & 0xff)));
		return nRet;
	}

	public static String getCurrentDateTimeString() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String strTime = dayTime.format(new Date(time));
		return strTime;
	}

	public static String getCurrentDateString() {
		long time = System.currentTimeMillis();
		SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
		String strToday = today.format(new Date(time));
		return strToday;
	}
}
