package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.Effect;
import edu.wol.dom.Phenomen;
import edu.wol.dom.space.Movement;
import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Rotation;

public class PhenomenSerializer<E extends Phenomen<Planetoid>> implements JsonSerializer<E> {
	final static Logger logger = LoggerFactory.getLogger(PhenomenSerializer.class);
	@Override
	public JsonElement serialize(E src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonPhenomen= (JsonObject) context.serialize(src.getEntity());
		jsonPhenomen.add("position", context.serialize(src.getPosition()));
		for(Effect<Planetoid> e:src.getEffects()){
			if(e instanceof Movement){
				jsonPhenomen.add("velocity", context.serialize(((Movement<Planetoid>)e).getVector()));
			}else if(e instanceof Rotation){
				jsonPhenomen.add("rotation", context.serialize(((Rotation<Planetoid>)e).getVector()));
			}else{
				logger.warn("Unsupported Effect "+e.getClass().getCanonicalName());
			}
		}
		return jsonPhenomen;
		/*JsonObject jsonPayload=new JsonObject();
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
		return jsonPayload;*/
	}

}
