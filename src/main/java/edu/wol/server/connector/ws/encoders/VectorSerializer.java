package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.space.Position;
import edu.wol.dom.space.Vector;

public class VectorSerializer implements JsonSerializer<Vector> {

	@Override
	public JsonElement serialize(Vector src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonPos=new JsonObject();
		jsonPos.add("x", new JsonPrimitive(src.getX()));
		jsonPos.add("y", new JsonPrimitive(src.getY()));
		jsonPos.add("z", new JsonPrimitive(src.getZ()));
		return jsonPos;
	}

}
