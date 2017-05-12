package edu.wol.server.connector.ws;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class MessageLatchTestClientEndpoint extends Endpoint{
	private static final String SENT_MESSAGE = "Hello Testing World";
	private CountDownLatch messageLatch = null;
	
	public MessageLatchTestClientEndpoint(CountDownLatch messageLatch) {
		this.messageLatch = messageLatch;
	}
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		try {
            session.addMessageHandler(new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String message) {
                    System.out.println("TEST CLIENT Received message: "+message);
                    messageLatch.countDown(); // signal that the message was received by the client
                }
            });
            session.getBasicRemote().sendText(SENT_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
