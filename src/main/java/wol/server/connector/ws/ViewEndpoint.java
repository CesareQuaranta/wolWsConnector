package wol.server.connector.ws;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
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
	      System.out.println("Session open:"+session.getId());
	   }
	 
	@OnMessage
	   public void onMessage(Session session, String msg) {
	      
	    	  System.out.println("Session ("+session.getId()+") message:"+msg);
	         
	     
	   }
	
	public void sendMessage(String msg){
		try {
		for (Session sess : queue) {
            if (sess.isOpen())
               sess.getBasicRemote().sendText(msg);
        	}
		 } catch (IOException e) { e.printStackTrace(); }
	}
	
	 @OnError
	   public void error(Session session, Throwable t) {
	      t.printStackTrace();
	   }
	 
	 @OnClose
	    public void onClose(Session session) {
		 System.out.printf("Close connection for client: {0}", 
	                session.getId());
	    }
}
