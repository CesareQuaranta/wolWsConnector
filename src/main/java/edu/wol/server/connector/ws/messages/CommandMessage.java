package edu.wol.server.connector.ws.messages;

import edu.wol.dom.commands.Command;

public class CommandMessage extends GenericMessage{
	private Command command;

	public CommandMessage(String source) {
		super(source);
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
