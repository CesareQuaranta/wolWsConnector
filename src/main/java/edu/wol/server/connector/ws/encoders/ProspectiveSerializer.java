package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.Prospective;
import edu.wol.dom.space.Position;

public class ProspectiveSerializer implements JsonSerializer<Prospective> {

	@Override
	public JsonElement serialize(Prospective src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonPrs=new JsonObject();
		jsonPrs.add("fov", new JsonPrimitive(new Integer(src.getFov())));
		jsonPrs.add("near", new JsonPrimitive(new Float(src.getNear())));
		jsonPrs.add("far", new JsonPrimitive(new Float(src.getFar())));
		JsonElement jsonPos = context.serialize(src.getPosition());
		jsonPrs.add("position", jsonPos);
		JsonElement jsonFoc = context.serialize(src.getFocus());
		jsonPrs.add("focus", jsonFoc);
		return jsonPrs;
	}

}
