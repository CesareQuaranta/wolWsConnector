package edu.wol.server.connector.ws.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;

public class CommandDecoder implements Decoder.Text<Command>{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command decode(String s) throws DecodeException {
		Command c = new GravityPower();
		return c;
	}

	@Override
	public boolean willDecode(String s) {
		return (s!=null && s.startsWith("xC"));
	}

}
