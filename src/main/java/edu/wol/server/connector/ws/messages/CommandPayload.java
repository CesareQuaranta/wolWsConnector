package edu.wol.server.connector.ws.messages;

import edu.wol.dom.space.Position;

public class CommandPayload {
	public enum CommandType {
	    GRAVITY
	};
	
	private CommandType type;
	private Position position;
	private long magnitude;
	
	public CommandType getType() {
		return type;
	}
	public void setType(CommandType type) {
		this.type = type;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public long getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(long magnitude) {
		this.magnitude = magnitude;
	}
	
}
