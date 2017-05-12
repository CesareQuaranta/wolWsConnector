package edu.wol.server.connector.ws;

import java.util.ArrayList;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class TestClientEndpoint extends Endpoint{
	private static final String SENT_MESSAGE = "xToken";
	private Session session;
	private TestMessageHandler msgHadler;
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		this.msgHadler = new TestMessageHandler();
		try {
            this.session.addMessageHandler(msgHadler);
            this.session.getAsyncRemote().sendText(SENT_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void send(String message){
		if(this.session!=null && this.session.isOpen()){
			this.session.getAsyncRemote().sendText(message);
		}
		
	}
	
	private class TestMessageHandler implements MessageHandler.Whole<String>{
		private ArrayList<String> messages = new ArrayList<String>();
		
		@Override
		public void onMessage(String message) {
			messages.add(message);
			System.out.println("Messaggio ricevuto: "+message);
		}
		
	}

}
