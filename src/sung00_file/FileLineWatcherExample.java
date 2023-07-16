package sung00_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileLineWatcherExample {
	
	public static void main(String[] args) {
		File file = new File("���� ���");
	    FileLineWatcher watcher = new FileLineWatcher(file);
	    Thread thread = new Thread(watcher);
	    thread.setDaemon(true);
	    thread.start();

	    //���� �����ϰ� �ʹٸ�
	    watcher.stop();
	}

}

class FileLineWatcher implements Runnable {
	  private static final int DELAY_MILLIS = 1000;

	  private boolean isRun;
	  //��� ����
	  private final File file;

	  public FileLineWatcher(File file) {
	    this.file = file;
	  }

	  @Override
	  public void run() {
	    System.out.println("Start to tail a file - " + file.getName());

	    isRun = true;
	    if (!file.exists()) {
	      System.out.println("Failed to find a file - " + file.getPath());
	    }

	    //try ������ Stream�� ���� ���� ������ �� close�� ����
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

	      while (isRun) {
	        //readLine(): ������ �� line�� �о���� �޼ҵ�
	        final String line = br.readLine();
	        if (line != null) {
	          System.out.println("New line added - " + line);
	        } else {
	          Thread.sleep(DELAY_MILLIS);
	        }
	      }
	    } catch (Exception e) {
	      System.out.println("Failed to tail a file - " + file.getPath());
	    }
	    System.out.println("Stop to tail a file - " + file.getName());
	  }

	  /**
	   * ������ ���� ���.
	   */
	  public void stop() {
	    isRun = false;
	  }
	}