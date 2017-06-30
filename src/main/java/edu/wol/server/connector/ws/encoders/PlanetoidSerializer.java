package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.WolEntity;
import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.space.Planetoid;

public class PlanetoidSerializer<E extends Planetoid> implements JsonSerializer<E> {

	@Override
	public JsonElement serialize(E src, Type typeOfSrc,
			JsonSerializationContext context) {
		//Id
		//Shape
		JsonObject jsonPlt=new JsonObject();
		jsonPlt.add("ID", new JsonPrimitive(src.getID()));
		JsonElement jsonShape = context.serialize(src.getShape());
		jsonPlt.add("geometry", jsonShape);
		return jsonPlt;
	}

}
