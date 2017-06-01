package edu.wol.server.connector.ws.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;
import edu.wol.server.connector.ws.messages.CommandMessage;

public class CommandDecoder implements Decoder.Text<CommandMessage>{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommandMessage decode(String s) throws DecodeException {
		Command c = new GravityPower();
		CommandMessage msg = new CommandMessage(s);
		msg.setCommand(c);
		return msg;
	}

	@Override
	public boolean willDecode(String s) {
		return (s!=null && s.startsWith("xC"));
	}

}
