package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.space.Position;
import edu.wol.dom.space.Vector3f;

public class VectorSerializer implements JsonSerializer<Vector3f> {

	@Override
	public JsonElement serialize(Vector3f src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonPos=new JsonObject();
		jsonPos.add("x", new JsonPrimitive(new Float(src.getX())));
		jsonPos.add("y", new JsonPrimitive(new Float(src.getY())));
		jsonPos.add("z", new JsonPrimitive(new Float(src.getZ())));
		return jsonPos;
	}

}
