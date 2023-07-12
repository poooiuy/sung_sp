package sung05_exam2022_httpProxyServer_sub4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.eclipse.jetty.client.api.ContentResponse;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	LogWriter lw;
	
	public MyServlet(String path) {
		this.path = path;
		System.out.println("path:" + path);
		lw = new LogWriter();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		MyClient myClient = new MyClient();
		ContentResponse contentResponse = null;
		String callerId = System.currentTimeMillis()+"";
		
		try {
			Map<String, String[]> map = req.getParameterMap();
			contentResponse = myClient.getRequest(path.concat(req.getRequestURI()), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String writer = req.getHeader("x-requestId") + "|" + System.currentTimeMillis() + "|" + path.concat(req.getRequestURI()) + "|" + contentResponse.getStatus();
		System.out.println(writer);
		lw.wirteLog(null, writer);
		
		res.setStatus(contentResponse.getStatus());
		res.getWriter().write(contentResponse.getContentAsString());
		
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		MyClient myClient = new MyClient();
		ContentResponse contentResponse = null;
		
		try {
			Map<String, String[]> map = req.getParameterMap();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
	        String buffer;
	        StringBuilder sb = new StringBuilder();
	        while ((buffer = input.readLine()) != null) {
	            sb.append(buffer + "\n");
	        }
	        String strBody = sb.toString();
	        input.close(); 

	        
			contentResponse = myClient.postRequest(path.concat(req.getRequestURI()), strBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String writer = req.getHeader("x-requestId") + "|" + path.concat(req.getRequestURI()) + "|" + contentResponse.getStatus();
		System.out.println(writer);
		lw.wirteLog(null, writer);
		
		res.setStatus(contentResponse.getStatus());
		res.getWriter().write(contentResponse.getContentAsString());
		
    }
	
}