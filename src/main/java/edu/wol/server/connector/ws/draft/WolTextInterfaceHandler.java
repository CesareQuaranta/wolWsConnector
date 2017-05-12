package edu.wol.server.connector.ws.draft;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WolTextInterfaceHandler extends TextWebSocketHandler{
	 @Override
	    public void handleTextMessage(WebSocketSession session, TextMessage message) {
	       System.out.println("Ricevuto:"+message.getPayload());
	    }
}
