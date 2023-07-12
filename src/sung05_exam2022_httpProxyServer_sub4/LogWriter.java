package sung05_exam2022_httpProxyServer_sub4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {
	
	String fileNameLocal = "./src/sung05_exam2022_httpProxyServer_sub4/log.txt";
	
	public void wirteLog(String fileName, String text) throws IOException {
		
		
		 // 1. ���� ��ü ����
        File file = new File(fileNameLocal);

        // 2. ���� ���翩�� üũ �� ����
        if (!file.exists()) {
            file.createNewFile();
        }

        // 3. Writer ����
        //	�̾�� ���� FileWriter, PrintWriter �� ������ true �߰�
        FileWriter fw = new FileWriter(file, true);
        PrintWriter writer = new PrintWriter(fw, true);

        // 4. ���Ͽ� ����
        writer.println(text);
    	//	writer.print(text);

        // 5. PrintWriter close
        writer.close();
	}

}
