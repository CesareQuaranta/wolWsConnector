package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.messages.EntitiesPayload;

public class EntitiesPayloadSerializer<E extends EntitiesPayload<Planetoid,Position>> implements JsonSerializer<E> {

	@Override
	public JsonElement serialize(E src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonPayload=new JsonObject();
		JsonArray jsonEntities=new JsonArray();
		jsonPayload.add("entities", jsonEntities);
		Iterator<Planetoid> i=src.iterator();
		while(i.hasNext()){
			Planetoid entity=i.next();
			Position position=src.getPosition(entity);
			JsonElement jsonEntity = context.serialize(entity);
			if(jsonEntity!=null && jsonEntity instanceof JsonObject && position!=null){
				((JsonObject)jsonEntity).add("position", context.serialize(position));
			}
			jsonEntities.add(jsonEntity);	
		}
		return jsonPayload;
	}

}
