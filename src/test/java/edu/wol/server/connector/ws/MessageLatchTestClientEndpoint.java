package edu.wol.server.connector.ws;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class MessageLatchTestClientEndpoint extends Endpoint{
	private static final String SENT_MESSAGE = "xToken:DuyniivSfVj6vEddcxRTIp1ddZmBDBjIcEV0LlX3ao3J/XuYkTuwX/8DfZFGfzPXpG5grn4TP/91Gj3HUpHzie52WlUV5bj6q2NAdx2n04sJrcJy1SUwEa28DhlRqaQSSX6RhwCVNIFozYQmgOUcohnguf58CMrUoiezRkJnHTxIU69sUvTMKZcSeWpI2QsS9c9gZPUZOhcJ2TFvsKWqi2GVacd5538vIcSKcHxqeoJrcuVe2+l5s5Pk+pz88KFS5G4VhEhv7rt75tvCT6x5Yvu2BRebcpOy5coX//Y171Xn9MZZbE7xoj3rPENREs3bKCBYV8yCaogxATiEllOcBw==";
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
