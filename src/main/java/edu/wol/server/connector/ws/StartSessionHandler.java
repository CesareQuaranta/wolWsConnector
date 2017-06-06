package edu.wol.server.connector.ws;

import javax.websocket.MessageHandler;
import javax.websocket.Session;

import edu.wol.server.connector.ws.messages.GenericMessage;
import edu.wol.server.connector.ws.messages.UserPayload;

public class StartSessionHandler implements MessageHandler.Whole<GenericMessage>{
	final Session session;
	public StartSessionHandler(Session session) {
		this.session=session;
	}

	@Override
	public void onMessage(GenericMessage message) {
		if (message!=null && message.getPayload() instanceof UserPayload){
			session.getAsyncRemote().sendText("Ricevuto Start message: "+message.toString()+" Thanks!");
			session.removeMessageHandler(this);
			session.addMessageHandler(new CommandMessageHandler(session));
		}
	}

}
