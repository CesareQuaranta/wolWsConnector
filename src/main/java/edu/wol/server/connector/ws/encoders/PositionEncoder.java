package edu.wol.server.connector.ws.encoders;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import edu.wol.dom.space.Position;

public class PositionEncoder implements Encoder.Text< Position >{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Position pos) throws EncodeException {
		JsonObjectBuilder jbuilder = Json.createObjectBuilder();
		jbuilder.add("x", pos.getX());
		jbuilder.add("y", pos.getY());
		jbuilder.add("z", pos.getZ());
		return jbuilder.build().toString();
	}

}
