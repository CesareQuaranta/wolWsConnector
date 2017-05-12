package edu.wol.server.connector.ws.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;
import edu.wol.server.connector.ws.SessionStartMessage;

public class StartMessageDecoder implements Decoder.Text<SessionStartMessage>{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SessionStartMessage decode(String s) throws DecodeException {
		SessionStartMessage m = new SessionStartMessage();
		return m;
	}

	@Override
	public boolean willDecode(String s) {
		return (s!=null && s.startsWith("xToken"));
	}

}
