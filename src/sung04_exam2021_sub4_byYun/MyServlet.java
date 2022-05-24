package sung04_exam2021_sub4_byYun;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        JsonObject result = new JsonObject();
        if (uri.startsWith("/RECEIVE")) {
            ProcessHandlerNoTimer.getInstance().receiveProc(uri, result);
        } else if (uri.startsWith("/DLQ")) {
            ProcessHandlerNoTimer.getInstance().dlqProc(uri, result);
        }
        resp.getWriter().write(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        JsonObject result = new JsonObject();
        if (uri.startsWith("/CREATE")) {
            ProcessHandlerNoTimer.getInstance().createProc(req, uri, result);
        } else if (uri.startsWith("/SEND")) {
            ProcessHandlerNoTimer.getInstance().sendProc(req, uri, result);
        } else if (uri.startsWith("/ACK")) {
            ProcessHandlerNoTimer.getInstance().successProc(uri, result);
        } else if (uri.startsWith("/FAIL")) {
            ProcessHandlerNoTimer.getInstance().failProc(uri, result);
        }
        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().write(result.toString());
    }
}
