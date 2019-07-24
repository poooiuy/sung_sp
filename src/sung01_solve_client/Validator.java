package sung01_solve_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public class Validator {
	public static final int FILENAME_LEN = 27;
	
	private String insId;
	private String onBusId;
	private String onBusTime;
	
	public boolean login(String id, String psw) throws NoSuchAlgorithmException, IOException {
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader("./src/sung01.education2019.workshop.file/CLIENT/INSPECTOR.TXT"));
			String line;
			String encPsw = CardUtility.passwordEncryption(psw);
			while ((line = in.readLine()) != null) {
				String fileId = line.substring(0, 8);
				String filePsw = line.substring(9);
	
				if (id.equals(fileId) && filePsw.equals(encPsw)) {
					insId = id;
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) { in.close(); }
		}

		return false;
	}
	
	public void logout() throws IOException {
		sendToServer();
		insId = null;
	}
	
	public void getOnBus(String busId) {
		if (busId.length() < 7 || busId.substring(0, 4).equals("BUS_") != true) {
			System.out.println("Wrong Bus ID");
			return;
		}
		
		onBusId = busId;
		onBusTime = CardUtility.getCurrentDateTimeString();// Get Start Time
	}
	
	public void getOffBus() {
		onBusId = null;
		onBusTime = null;
	}

	// cardInfo : [카드ID(8)][버스번호(7)][승차/하차 코드(1)][최근 승차시각(14)]
	// sample : CARD_001BUS_001N20171019143610
	public void inspectCard(String cardInfo) throws IOException, ParseException {
		if (cardInfo.length() != 30) {
			System.out.println("Wrong Card Info");
			return;
		}
		
		if (onBusId != null) {
			String validateCode;
	
			// cardInfo parsing
			// String cardID = cardInfo.substring(0, 8);
			String cardBusID = cardInfo.substring(8, 15);
			String code = cardInfo.substring(15, 16);
			String rideTime = cardInfo.substring(16);
	
			// Get Inspect Time
			String inpectTime = CardUtility.getCurrentDateTimeString();
	
			// Validation
			// 1. Bus ID Match
			if (onBusId.equals(cardBusID)) {
				// 2. Check Aboard Code
				if (code.equals("N")) {
					// 3. Time Difference
					if (CardUtility.hourDiff(inpectTime, rideTime) < 3) {
						validateCode = "R1";
					} else {
						validateCode = "R4";
					}
				} else {
					validateCode = "R3";
				}
			} else {
				validateCode = "R2";
			}
	
			saveFile(cardInfo, validateCode, inpectTime);
		}
	}

	private void saveFile(String cardInfo, String validateCode, String inpectTime) throws IOException {
		// Create Folder
		File destFolder = new File("./src/sung01.education2019.workshop.file/" + insId);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
					
		// File Writing
		String strFilename = destFolder + "/" + insId + "_" + onBusTime + ".TXT";
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(strFilename, true);
			fw.write(insId + "#" + onBusId + "#" + cardInfo + "#" + validateCode + "#" + inpectTime + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) { fw.close(); }
		}
	}
	
	//파일명(string),파일크기(long),파일데이터(NByte)
	public void sendToServer() throws IOException {
		Socket socket = null;
		DataOutputStream os = null;
		try {
			socket = new Socket("127.0.0.1", 27017);
			os = new DataOutputStream(socket.getOutputStream());
			
			byte[] buffer = new byte[4096];
			int length;
			
			// get all the files from a directory
			File directory = new File("./src/sung01.education2019.workshop.file/" + insId);
			File[] fList = directory.listFiles();
			for (File file : fList) {
				if (file.isFile()) {
					os.writeUTF(file.getName());
					os.writeInt((int) file.length());
					
					FileInputStream is = null;
					try {
						is = new FileInputStream(file.getPath());
						while ((length = is.read(buffer)) != -1) {
							os.write(buffer, 0, length);
						}
					} finally {
						if (is != null) { is.close(); }
					}
	
					// move file to backup folder
					moveFileToBackup(file.getPath(), file.getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) { os.close(); }
			if (socket != null) { socket.close(); }
		}
	}

	private void moveFileToBackup(String path, String name) {
		File fileFrom = new File(path); // source
		File fileTo = new File("./src/sung01.education2019.workshop.file/BACKUP/" + name); // destination
		fileTo.delete();
		fileFrom.renameTo(fileTo);
	}
}