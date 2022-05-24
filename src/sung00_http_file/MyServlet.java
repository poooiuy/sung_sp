package sung00_http_file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("Request : " + req.getRequestURL());

		// 날짜 요청에 대한 응답
		if (req.getRequestURI().equals("/requestDate")) {
			res.setStatus(200);
			res.getWriter().write(new Date().toString());
		}

		// 파일 전송 (byte array로 전송)
		else if (req.getRequestURI().equals("/requestFile")) {
			File file = new File("C:\\temp\\LENA.jpg");
			byte[] fileContent = Files.readAllBytes(file.toPath());
			res.setStatus(200);
			ServletOutputStream stream = res.getOutputStream();
			stream.flush();
			stream.write(fileContent);
			stream.flush();
			stream.close();
		}

		// MAP의 Value에 binary를 담았는데, 그냥 전송할 수 없어서 serialize하여 전송

		else if (req.getRequestURI().equals("/requestFile2")) {

			File file = new File("C:\\temp\\LENA.jpg");

			byte[] fileContent = Files.readAllBytes(file.toPath());
			LinkedHashMap<String, byte[]> content = new LinkedHashMap<String, byte[]>();
			content.put("file", fileContent);
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(content);
			res.setStatus(200);
			ServletOutputStream stream = res.getOutputStream();
			stream.flush();
			stream.write(byteOut.toByteArray()); // byte array로 변경하여 전송
			stream.flush();
			stream.close();
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("Request : " + req.getRequestURL());

		// Client로부터 전달된 json data 저장
		if (req.getRequestURI().equals("/fileList")) {
			File destFolder = new File("./OUTPUT");
			if (!destFolder.exists()) {
				destFolder.mkdirs();
			}

			LocalTime currentTime = LocalTime.now();
			String fname = String.format("./OUTPUT/%02d%02d%02d.json", currentTime.getHour(), currentTime.getMinute(),
					currentTime.getSecond());
			PrintWriter printWriter = new PrintWriter(new FileWriter(new File(fname)));
			BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String buffer;

			while ((buffer = input.readLine()) != null) {
				printWriter.print(buffer);
			}

			input.close();
			printWriter.close();
			res.setStatus(200);
			res.getWriter().write(fname + " saved!");

		}

		// Key는 파일이름으로 사용, Value는 json파일로 저장

		else if (req.getRequestURI().equals("/fileList2")) {

			Gson gson = new Gson();
			System.out.println("Request : " + req.getRequestURL());

			File destFolder = new File("./SERVER");
			if (!destFolder.exists()) {
				destFolder.mkdirs();
			}

			BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String buffer;
			StringBuilder sb = new StringBuilder();
			while ((buffer = input.readLine()) != null) {
				sb.append(buffer + "\n");
			}

			String strBody = sb.toString();
			input.close();
			JsonObject jObj = gson.fromJson(strBody, JsonObject.class);
			String fileName = jObj.get("FileName").getAsString();
			String fileContent = jObj.get("FileContent").getAsString();
			PrintWriter printWriter = new PrintWriter(new FileWriter(new File("./SERVER/" + fileName)));
			printWriter.print(fileContent);
			printWriter.close();

			res.setStatus(200);
			res.getWriter().write(fileName + " saved!");

		}
	}

}