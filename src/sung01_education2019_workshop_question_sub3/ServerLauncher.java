package sung01_education2019_workshop_question_sub3;

import java.io.IOException;

public class ServerLauncher {
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		Thread thread = new Thread(server);
		thread.start();
	}

}
