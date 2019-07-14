package sung01.solve.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CardServer implements Runnable { // Runnable Interface 구현
	public static final int BUF_SIZE = 4096;
	
	private ServerSocket serverSocket;
	
	private boolean isStop;

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	//파일명(String),파일크기(int),파일데이터(NByte)
	public void run() {
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(27015);
			
			byte[] buffer = new byte[BUF_SIZE];
			int length;
			
			while (!isStop) {
				Socket socket = null;
				DataInputStream is = null;
				try {
					socket = serverSocket.accept();
					
					is = new DataInputStream(socket.getInputStream());
					while (true) {
						String fileName = is.readUTF();
						int fileSize = is.readInt();
						
						while (fileSize > 0) {
							length = is.read(buffer, 0, Math.min(fileSize, buffer.length));
							fileSize -= length;
							writeFile(fileName, buffer, 0, length);
						}
					}
				} catch (EOFException e) {
					// Do Nothing
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (socket != null) { socket.close(); }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) { serverSocket.close(); }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeFile(String fileName, byte[] buffer, int offset, int length) throws IOException {
		FileOutputStream fw = null;
		try {
			fw = new FileOutputStream("./src/sung01/file/SERVER/" + fileName, true);
			fw.write(buffer, offset, length);
		} finally {
			if (fw != null) { fw.close(); }
		}
	}

	public synchronized void close() {
		isStop = true;
		
		try {
			if (serverSocket != null) { serverSocket.close(); }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
