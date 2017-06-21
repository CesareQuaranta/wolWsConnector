package edu.wol.server.connector.ws.encoders;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import edu.wol.dom.Prospective;
import edu.wol.dom.space.Position;

public class ProspectiveEncoder implements Encoder.Text< Prospective >{
	PositionEncoder posEncoder;
	@Override
	public void init(EndpointConfig config) {
		posEncoder=new PositionEncoder();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Prospective prospective) throws EncodeException {
		JsonObjectBuilder jbuilder = Json.createObjectBuilder();
		JsonObjectBuilder propertyBuilder=Json.createObjectBuilder();
		//TODO Conversione in unità di misura pro rendering
		Position pos=prospective.getPosition();
		Position focus = prospective.getFocus();
		propertyBuilder.add("fov", prospective.getFov());
		if(pos != null){
			propertyBuilder.add("position", posEncoder.encode(pos));			
		}
		if(focus != null){
			propertyBuilder.add("focus", posEncoder.encode(focus));	
		}
		propertyBuilder.add("near",prospective.getNear() ).add("far",prospective.getFar() );
		jbuilder.add("Prospective", propertyBuilder.build());
		return jbuilder.build().toString();
	}

}
