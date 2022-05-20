package com.lgcns.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Http2Servlet extends HttpServlet{
	
private static final long serialVersionUID = 1L;
static MessageQueue mq = new MessageQueue();
	
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
			
		default :
			break;
		}
		
		res.setStatus(200);
//		res.getWriter().write("Welcome to my server.");
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		Gson gson = new Gson();
//		Gson gson = new GsonBuilder().create();

		
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
				mq.createQueue(param[2], requestBody.getQueueSize()+"");
				res.getWriter().write("{\"Result\" : \"Ok\"}");
			}
			break;
		case "SEND" :
			QueueObject qo = mq.getQueueManager().get((queueName));
			if( qo.getQueue().size() >= qo.getSize()) {
				res.getWriter().write("{\"Result\" : \"Queue Full\"}");
			}else {
				MessageObject mo = new MessageObject(requestBody.getMessage());
//				System.out.println(requestBody.getMessage());
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
