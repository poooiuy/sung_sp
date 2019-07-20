package sung00.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DateClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 9090);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        System.out.println(answer);
    }
}