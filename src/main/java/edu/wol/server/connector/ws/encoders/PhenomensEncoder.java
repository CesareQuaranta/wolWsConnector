package edu.wol.server.connector.ws.encoders;

import java.util.Collection;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import edu.wol.dom.Phenomen;
import edu.wol.dom.Prospective;
import edu.wol.dom.space.Planetoid;

public class PhenomensEncoder implements Encoder.Text<Collection<Phenomen<Planetoid>>>{
	private Gson gson;
	@Override
	public void init(EndpointConfig config) {
		gson=GsonFactory.getInstance();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Collection<Phenomen<Planetoid>> phenomens) throws EncodeException {
		return "{\"Phenomens\":"+gson.toJson(phenomens)+"}";
	}

}
