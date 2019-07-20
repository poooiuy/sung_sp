/*
 * 네트워크 프로그래밍
 * 1. Client에서 Server에 접속하면 Server는 현재 날짜와 시각을 Client로 전송하고, Client는 전송 받은 값을 출력하시오.
 * 2. Client에서 Server에 접속하여 파일을 전송하는 프로그램을 작성하시오.
 *   - ClientFiles 폴더의 test.exe파일을 전송하여 ServerFiles폴더에 저장 
 *   - Client는 파일 전송 완료 후 종료 
 *   - Server는 파일을 수신 완료하고 다시 Client 접속 대기 
 *   - Server는 ‘QUIT’입력을 받으면 종료
 */

package sung00.socket.file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileServer {

    public static void main(String[] args) throws IOException, InterruptedException {
    	ThreadClass tc = new ThreadClass(); 
        Thread th = new Thread(tc); 
        th.start(); 
    	
	    InputStream in = System.in;
	    InputStreamReader reader = new InputStreamReader(in);
	    BufferedReader br = new BufferedReader(reader);
	    String str;
	    	    
	    while (true)
        {
	    	str = br.readLine();
	    	
			if (str.equals("QUIT"))
			{
				tc.listener.close();
				th.join();
				break;
			}
        }	    
    }

}

class ThreadClass implements Runnable { // Runnable Interface 구현 
	public ServerSocket listener;   
    public void run() {
    	final int BUF_SIZE = 4096;
    	int recvLen;   	
    	String filename = "test.exe";
    	byte[] buffer = new byte[BUF_SIZE];
    	
        listener = null;
 		try {
			listener = new ServerSocket(27015);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
        try {
            while (true) {
                Socket s = listener.accept();
                InputStream input = s.getInputStream();
                int cnt = 0;
                while ((recvLen = input.read(buffer, 0, BUF_SIZE)) !=  -1) 
                {            	
	               	FileOutputStream fw = new FileOutputStream(".//ServerFiles//"+filename, true);
               		fw.write(buffer, cnt, recvLen);
               		fw.close();
                }
                System.out.println(filename+" is received.");
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        finally {
            try {
				listener.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        } 		
    } 
} 