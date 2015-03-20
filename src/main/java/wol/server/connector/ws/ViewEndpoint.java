package wol.server.connector.ws;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint("/ws/view")
public class ViewEndpoint {
	static Queue<Session> queue = new ConcurrentLinkedQueue<>();
	public ViewEndpoint() {
		// TODO Auto-generated constructor stub
	}
	
	 @OnOpen
	   public void open(Session session) {
	      session.getUserProperties().put("previousMsg", " ");
	      queue.add(session);
	   }
	 
	@OnMessage
	   public void onMessage(Session session, String msg) {
	      try {
	         session.getBasicRemote().sendText(msg);
	         for (Session sess : session.getOpenSessions()) {
	             if (sess.isOpen())
	                sess.getBasicRemote().sendText(msg);
	         }
	      } catch (IOException e) {  }
	   }
	
	
	 @OnError
	   public void error(Session session, Throwable t) {
	      t.printStackTrace();
	   }
}
