package sung08_exam2023_httpMicroService_solve_sub5.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sung08_exam2023_httpMicroService_solve_sub5.WorkflowManager;

public class EngineServlet extends HttpServlet {

	private static final long serialVersionUID = 8572241974921679005L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String requestURL = request.getRequestURL().toString();
		String path = requestURL.substring(requestURL.lastIndexOf("/") + 1);

		System.out.println(path);
		
		response.setContentType("application/json");
		try {
			String result = WorkflowManager.get(path).run().toString();
			System.out.println("result: " + result);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}