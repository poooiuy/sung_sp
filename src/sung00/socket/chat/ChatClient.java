package sung00.socket.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
 
public class ChatClient {
    public static void main(String[] args) {
        Socket sock = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        boolean endflag = false;
        try {
            sock = new Socket("127.0.0.1", 9090);	//������,��Ʈ
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            
            InputThread it = new InputThread(sock,br);
            it.start();
            String line = null;
            while((line = keyboard.readLine()) != null) {
                pw.println(line);
                pw.flush();
                if(line.equals("QUIT")) {
                    endflag = true;
                    break;
                }
            }
            System.out.println("Ŭ���̾�Ʈ ���� ����");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(pw != null) {
                    pw.close();
                }
                if(br != null) {
                    br.close();
                }
                if(sock != null) {
                    sock.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
 
class InputThread extends Thread{
    private Socket sock = null;
    private BufferedReader br = null;
    public InputThread(Socket sock,BufferedReader br) {
        this.sock = sock;
        this.br = br;
    }
    public void run() {
        try {
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
                if(sock != null) {
                    sock.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

