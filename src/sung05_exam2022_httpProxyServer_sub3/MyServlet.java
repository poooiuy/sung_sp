package sung05_exam2022_httpProxyServer_sub3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.eclipse.jetty.client.api.ContentResponse;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	public MyServlet(String path) {
		this.path = path;
		System.out.println("path:" + path);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		MyClient myClient = new MyClient();
		ContentResponse contentResponse = null;
		
		try {
			Map<String, String[]> map = req.getParameterMap();
			contentResponse = myClient.getRequest(path.concat(req.getRequestURI()), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		res.setStatus(contentResponse.getStatus());
		res.getWriter().write(contentResponse.getContentAsString());
		
    }
	
}