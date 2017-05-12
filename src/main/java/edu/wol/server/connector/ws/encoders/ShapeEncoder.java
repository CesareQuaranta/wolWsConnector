package edu.wol.server.connector.ws.encoders;

import java.util.UUID;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.shape.iShape;

public class ShapeEncoder implements Encoder.Text< iShape >{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(iShape shape) throws EncodeException {
		JsonObjectBuilder jbuilder = Json.createObjectBuilder();
		if(shape instanceof PlanetShape){
			PlanetShape p = (PlanetShape) shape;
			jbuilder.add("Planetoid", 
					Json.createObjectBuilder().add("id", UUID.randomUUID().toString())
					.add("radius", p.getRadius()));
		}
		return jbuilder.build().toString();
	}

}
