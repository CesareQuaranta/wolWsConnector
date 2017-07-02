package edu.wol.server.connector.ws.decoders;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;
import edu.wol.dom.space.Position;

public class PositionDeserializer implements JsonDeserializer<Position>{

	@Override
	public Position deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonElement jsonX = jsonObject.get("x");
		JsonElement jsonY = jsonObject.get("y");
		JsonElement jsonZ = jsonObject.get("z");
	   
		return new Position(Math.round(jsonX.getAsFloat()),Math.round(jsonY.getAsFloat()),Math.round(jsonZ.getAsFloat()));
	}

}
