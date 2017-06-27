package edu.wol.server.connector.ws.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.encoders.GsonFactory;

public class CommandMessageDecoder implements Decoder.Text<Command>{
	public static String PREFIX = "xC:";
	private Gson gson;
	@Override
	public void init(EndpointConfig config) {
		 gson = GsonFactory.getInstance();
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command decode(String s) throws DecodeException {
		String commandString=s.substring(PREFIX.length());
		return gson.fromJson(commandString,Command.class);
	}

	@Override
	public boolean willDecode(String s) {
		return (s!=null && s.startsWith(PREFIX));
	}

}
