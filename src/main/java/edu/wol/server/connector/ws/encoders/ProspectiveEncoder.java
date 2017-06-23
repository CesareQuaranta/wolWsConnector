package edu.wol.server.connector.ws.encoders;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import edu.wol.dom.Prospective;

public class ProspectiveEncoder implements Encoder.Text< Prospective >{
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
	public String encode(Prospective prospective) throws EncodeException {
		return "{Prospective:"+gson.toJson(prospective)+"}";
	}

}
