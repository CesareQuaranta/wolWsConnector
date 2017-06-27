package edu.wol.server.connector.ws;

import javax.websocket.MessageHandler;
import javax.websocket.Session;

import edu.wol.dom.commands.Command;
import edu.wol.server.connector.ws.messages.GenericMessage;
import edu.wol.server.connector.ws.messages.UserPayload;

public class CommandMessageHandler implements MessageHandler.Whole<GenericMessage>{
	final Session session;
	public CommandMessageHandler(Session session) {
		this.session=session;
	}
	@Override
	public void onMessage(GenericMessage message) {
		// TODO Auto-generated method stub
		
	}

}
