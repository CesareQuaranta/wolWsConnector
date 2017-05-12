package edu.wol.server.connector.ws;

import javax.websocket.MessageHandler;
import javax.websocket.Session;

import edu.wol.dom.commands.Command;

public class CommandMessageHandler implements MessageHandler.Whole<Command>{
	final Session session;
	public CommandMessageHandler(Session session) {
		this.session=session;
	}

	@Override
	public void onMessage(Command command) {
		session.getAsyncRemote().sendText("Ricevuto Comando "+command.getClass().getSimpleName()+" Thanks!");
	}

}
