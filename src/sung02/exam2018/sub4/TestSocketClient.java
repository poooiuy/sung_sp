package sung02.exam2018.sub4;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestSocketClient {
	
    public static void main(String[] args) throws IOException {
    	
    	String message = "ABCDFILE.TXT ";
    	
    	Scanner scanner = new Scanner(System.in);		
		String cmd = scanner.nextLine();
		
    	SendToServer(cmd);
    }
    
	public static void SendToServer(String cmd) throws UnknownHostException, IOException
	{
		Socket s = new Socket("127.0.0.1", 9876);
		java.io.OutputStream out = s.getOutputStream();
		int readLen;
		
		out.write(cmd.getBytes());
		
        s.close();
        
	}
}
