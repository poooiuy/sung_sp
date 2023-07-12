package sung00_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
 *	�ٳѱ��� �ʿ��� ��� "\r\n" �� ����Ѵ�. 
 */

public class TextFileIo {

	// ������ ������ System.out ���� Print �Ѵ�.
	public static void printFile(String fileName) {
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);

			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ������ ������ Return �Ѵ�.
	public String readFile(String fileName) {
		String result = "";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				if (result.equals("")) {
					result = result + line;
				} else {
					result = result + "\r\n" + line;
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// ������ ������ �ٴ����� Map�� ��� Return �Ѵ�.
	// �����ڴ� separator �� ����Ѵ�.
	public Map<String, Object> getMapFile(String fileName) {
		Map<String, Object> result = new HashMap<>();
		;
		String line = null;
		String separator = "#";
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// �ٴ��� ������ Map�� ���
			while ((line = bufferedReader.readLine()) != null) {
				String[] strArr = line.split(separator);
				result.put(strArr[0], strArr[1]);
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// ���� ����
	public void wirteLog(String fileName, String text) throws IOException {

		// 1. ���� ��ü ����
		File file = new File(fileName);

		// 2. ���� ���翩�� üũ �� ����
		if (!file.exists()) {
			file.createNewFile();
		}

		// 3. Writer ����
		// �̾�� ���� FileWriter, PrintWriter �� ������ true �߰�
		FileWriter fw = new FileWriter(file, true);
		PrintWriter writer = new PrintWriter(fw, true);

		// 4. ���Ͽ� ����
		writer.println(text);
		// writer.print(text);

		// 5. PrintWriter close
		writer.close();
	}

	public static void main(String[] args) {
		printFile("./src/sung/file/BIGFILE/ABCDFILE.TXT");
	}

}
