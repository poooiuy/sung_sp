package sung05_exam2022_httpProxyServer_sub4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {
	
	String fileNameLocal = "./src/sung05_exam2022_httpProxyServer_sub4/log.txt";
	
	public void wirteLog(String fileName, String text) throws IOException {
		
		
		 // 1. 파일 객체 생성
        File file = new File(fileNameLocal);

        // 2. 파일 존재여부 체크 및 생성
        if (!file.exists()) {
            file.createNewFile();
        }

        // 3. Writer 생성
        //	이어쓰기 위해 FileWriter, PrintWriter 에 변수로 true 추가
        FileWriter fw = new FileWriter(file, true);
        PrintWriter writer = new PrintWriter(fw, true);

        // 4. 파일에 쓰기
        writer.println(text);
    	//	writer.print(text);

        // 5. PrintWriter close
        writer.close();
	}

}
