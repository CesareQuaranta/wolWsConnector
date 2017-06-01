package edu.wol.server.connector.ws.encoders;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import edu.wol.dom.Prospective;
import edu.wol.dom.space.Position;

public class ProspectiveEncoder implements Encoder.Text< Prospective >{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Prospective prospective) throws EncodeException {
		JsonObjectBuilder jbuilder = Json.createObjectBuilder();
		//Conversione in unità di misura pro rendering
		Position pos=prospective.getPosition();
		Position horizon = prospective.getHorizon();
		double maxFar=pos.getDistance(horizon);
		double relativeFar=maxFar*prospective.getFarRatio();
		double relativeNear=maxFar*prospective.getNearRatio();
		prospective.getFarRatio();
		jbuilder.add("Prospective", 
				Json.createObjectBuilder().add("fov", prospective.getFov())
				.add("near",relativeNear )
				.add("far",relativeFar )
				);
		return jbuilder.build().toString();
	}

}
