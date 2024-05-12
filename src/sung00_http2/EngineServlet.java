package sung00_http2;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EngineServlet extends HttpServlet {

	private static final long serialVersionUID = 8572241974921679005L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String requestURL = request.getRequestURL().toString();
		String path = requestURL.substring(requestURL.lastIndexOf("/") + 1);

		System.out.println(path);

		try {
//			StateManager.get(path).run();
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}