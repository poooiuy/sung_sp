package sung00_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileLineWatcherExample {
	
	public static void main(String[] args) {
		File file = new File("파일 경로");
	    FileLineWatcher watcher = new FileLineWatcher(file);
	    Thread thread = new Thread(watcher);
	    thread.setDaemon(true);
	    thread.start();

	    //만약 종료하고 싶다면
	    watcher.stop();
	}

}

class FileLineWatcher implements Runnable {
	  private static final int DELAY_MILLIS = 1000;

	  private boolean isRun;
	  //대상 파일
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

	    //try 문에서 Stream을 열면 블럭이 끝났을 때 close를 해줌
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

	      while (isRun) {
	        //readLine(): 파일의 한 line을 읽어오는 메소드
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
	   * 스레드 중지 기능.
	   */
	  public void stop() {
	    isRun = false;
	  }
	}