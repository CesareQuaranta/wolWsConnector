package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.shape.SphericalShape;

public class SphereShapeSerializer implements JsonSerializer<SphericalShape> {

	@Override
	public JsonElement serialize(SphericalShape src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonShape=new JsonObject();
		jsonShape.add("radius", new JsonPrimitive(new Double(src.getRadius())));
		return jsonShape;
	}

}
