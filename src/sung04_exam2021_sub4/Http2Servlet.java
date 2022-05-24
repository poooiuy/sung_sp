package sung04_exam2021_sub4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Http2Servlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	MessageQueue mq = MessageQueue.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		Gson gson = new Gson();
		
		String s = req.getRequestURI();
		String[] param = s.split("/");
		String cmd = param[1];
		String queueName = param[2];
		
		res.setStatus(200);
		res.setContentType("applicaiton/json");
		
		switch(cmd) {
		case "RECEIVE" :
			MessageObject result = mq.receive(queueName);
			if(result == null) {
				res.getWriter().write("{\"Result\" : \"No Message\"}");
			}else {
				Map<String, Object> m = new HashMap<>();
				m.put("Result", "Ok");
				m.put("MessageID", result.getMessageId());
				m.put("Message", result.getMessage());
				res.getWriter().write(gson.toJson(m));
			}
			break;
		case "DLQ" :
			if( (mq.getDeadQueueManager().containsKey(queueName)) && (mq.getDeadQueueManager().get(queueName).size()>0) ) {
				MessageObject mo = mq.getDeadQueueManager().get(queueName).poll();
				Map<String, Object> m = new HashMap<>();
				m.put("Result", "Ok");
				m.put("MessageID", mo.getMessageId());
				m.put("Message", mo.getMessage());
				res.getWriter().write(gson.toJson(m));
			}else {
				res.getWriter().write("{\"Result\" : \"No Message\"}");
			}
			break;
			
		default :
			break;
		}
		
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		Gson gson = new Gson();
		
		String url = req.getRequestURI();
		String[] param = url.split("/");
		String cmd = param[1];
		String queueName = param[2];
		BodyObject requestBody = gson.fromJson(req.getReader(), BodyObject.class);
		
		res.setStatus(200);
		res.setContentType("applicaiton/json");
		
		switch(cmd) {
		case "CREATE" :
			if(mq.getQueueManager().containsKey(queueName)) {
				res.getWriter().write("{\"Result\" : \"Queue Exist\"}");
			} else {
				mq.createQueue(queueName, requestBody);
				res.getWriter().write("{\"Result\" : \"Ok\"}");
			}
			break;
			
		case "SEND" :
			QueueObject qo = mq.getQueueManager().get((queueName));
			System.out.println(requestBody.getMessage() + ":" + qo.getQueue().size());
			if( qo.getQueue().size() >= qo.getSize()) {
				res.getWriter().write("{\"Result\" : \"Queue Full\"}");
			}else {
				MessageObject mo = new MessageObject(requestBody.getMessage());
				qo.getQueue().add(mo);
				res.getWriter().write("{\"Result\" : \"OK\"}");
			}
			break;
			
		case "ACK" :
			mq.ack(queueName, param[3]);
			Map<String, Object> m2 = new HashMap<>();
			m2.put("Result", "Ok");
			res.getWriter().write(gson.toJson(m2));
			break;
			
		case "FAIL" :
			mq.fail(queueName, param[3]);
			Map<String, Object> m3 = new HashMap<>();
			m3.put("Result", "Ok");
			res.getWriter().write(gson.toJson(m3));
			break;
		default :
			break;
		}
	}

}
