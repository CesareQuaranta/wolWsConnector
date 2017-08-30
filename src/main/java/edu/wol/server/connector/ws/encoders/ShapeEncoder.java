package edu.wol.server.connector.ws.encoders;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import edu.wol.dom.shape.AsteroidShape;
import edu.wol.dom.shape.PlaneShape;
import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.shape.Shape;
import edu.wol.dom.shape.SphericalShape;

public class ShapeEncoder implements Encoder.Text< Shape >{
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
	public String encode(Shape shape) throws EncodeException {
		if(shape instanceof SphericalShape || shape instanceof PlanetShape || shape instanceof AsteroidShape || shape instanceof PlaneShape){
			return gson.toJson(shape);
		}else{
			return "{unsupported:true}";
		}
		
	}

}
