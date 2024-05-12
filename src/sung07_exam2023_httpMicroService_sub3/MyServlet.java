package sung07_exam2023_httpMicroService_sub3;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String requestURL = req.getRequestURL().toString();
		String path = requestURL.substring(requestURL.lastIndexOf("/") + 1);

		System.out.println(path);
		
		try {
			new Executor().run(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.setStatus(200);
		res.getWriter().close();
	}
}
