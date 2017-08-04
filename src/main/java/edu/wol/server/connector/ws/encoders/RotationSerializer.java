package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.space.Rotation;

public class RotationSerializer implements JsonSerializer<Rotation<?>> {

	@Override
	public JsonElement serialize(Rotation<?> src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonRt=new JsonObject();
		JsonElement jsonAxis = context.serialize(src.getAxis());
		jsonRt.add("ax", jsonAxis);
		jsonRt.add("r", new JsonPrimitive(new Double(src.getRadians())));
		jsonRt.add("t", new JsonPrimitive(new Long(src.getTime())));
		return jsonRt;
	}

}
