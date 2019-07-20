package sung00.socket.file;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient {
	
    public static void main(String[] args) throws IOException {
    	SendToServer("test.exe");
    }
    
	public static void SendToServer(String strFilename) throws UnknownHostException, IOException
	{
		Socket s = new Socket("127.0.0.1", 27015);
		java.io.OutputStream out = s.getOutputStream();
		byte[] buffer = new byte[4096];
		int readLen;
		
        //get all the files from a directory
        FileInputStream inputStream = new FileInputStream(".//ClientFiles//"+strFilename);
        while((readLen = inputStream.read(buffer)) != -1) {
        	out.write(buffer,0,readLen);
        }   
        inputStream.close();    
		
        s.close();
        
        System.out.println(strFilename+" is sent.");
	}
}
