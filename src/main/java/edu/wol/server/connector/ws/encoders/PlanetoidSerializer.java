package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.WolEntity;
import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.space.Asteroid;
import edu.wol.dom.space.Planetoid;

public class PlanetoidSerializer<E extends Planetoid> implements JsonSerializer<E> {

	@Override
	public JsonElement serialize(E src, Type typeOfSrc,
			JsonSerializationContext context) {
		//Id
		//Shape
		JsonObject jsonPlt=new JsonObject();
		jsonPlt.add("ID", new JsonPrimitive(src.getID()));
		String type=null;
		if(src instanceof Asteroid){
			type="A";
		}
		if(type!=null){
			jsonPlt.add("type", new JsonPrimitive(type));
		}
		if(src.getMateria()!=null && !src.getMateria().isEmpty()){
			jsonPlt.add("materiaID",new JsonPrimitive(String.join("",src.getMateria())));
		}
		JsonElement jsonShape = context.serialize(src.getShape());
		jsonPlt.add("geometry", jsonShape);
		return jsonPlt;
	}

}
