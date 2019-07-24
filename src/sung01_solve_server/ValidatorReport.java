package sung01_solve_server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import sung01_solve_client.CardUtility;

public class ValidatorReport {
	
	private static String LINE_BREAK =  System.getProperty("line.separator");
	
	public boolean reportValidator() throws IOException {
		HashMap<String, ReportVo> map = new HashMap<>();
		final String strToday = CardUtility.getCurrentDateString();

		// File Find
		File directory = new File("..//SERVER");
		File[] list = directory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && file.getName().length() == 27 
						&& file.getName().substring(9, 17).equals(strToday);
			}
		});
		
		for (File file : list) {
			analysisData(map, file.getPath());
		}

		// Save Report File
		saveReportFile(map, strToday);

		return true;
	}

	private void analysisData(HashMap<String, ReportVo> map, String path) throws IOException {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(path));
			
			String line;
			while ((line = br.readLine()) != null) {
				ReportVo userReport = new ReportVo();
	
				String key = line.substring(0, 8);
	
				// insert key & value
				if (map.get(key) == null) {
					userReport.setInsId(line.substring(0, 8));
					userReport.setCheckCard(1);
					if (line.charAt(49) == '1') {
						userReport.setFailCard(0);
					} else {
						userReport.setFailCard(1);
					}
					map.put(key, userReport);
				} else {	// just change value
					map.get(key).increaseCheckCard();
					if (line.charAt(49) != '1') {
						map.get(key).increaseFailCard();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) { br.close(); }
		}
	}

	private void saveReportFile(HashMap<String, ReportVo> map, String today) throws IOException {
		String filename = "./src/sung01/file/REPORT_" + today + ".TXT";
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(filename);
			
			for (String key : map.keySet()) {
				fw.write(map.get(key).toString());
				fw.write(LINE_BREAK);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) { fw.close(); }
		}
	}

	public void printReport(String date, String option) throws IOException {
		ArrayList<ReportVo> reportList = new ArrayList<>();

		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader("./src/sung01/file/REPORT_" + date + ".TXT"));
			while ((line = br.readLine()) != null) {
				ReportVo userReport = new ReportVo(line);
				reportList.add(userReport);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) { br.close(); }
		}

		if ("C".equals(option)) {
			Comparator<ReportVo> co = new Comparator<ReportVo>() {
				public int compare(ReportVo o1, ReportVo o2) {
					return (o2.getCheckCard() - o1.getCheckCard());
				}
			};
			Collections.sort(reportList, co);
		}

		for (int i = 0; i < reportList.size(); i++) {
			System.out.println(reportList.get(i).toString());
		}
	}
}
