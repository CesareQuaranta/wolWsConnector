package edu.wol.server.connector.ws.decoders;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;
import edu.wol.dom.commands.Movement;
import edu.wol.dom.space.Position;

public class CommandDeserializer implements JsonDeserializer<Command>{

	@Override
	public Command deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonElement jsonType = jsonObject.get("type");
	    String type = jsonType.getAsString();
	    Command c = null;
	    switch(type){
	    case "Gravity":
	    	c=new GravityPower();
	    	JsonElement jsonPos=jsonObject.get("pos");
	    	JsonElement jsonMag=jsonObject.get("mag");
	    	if(jsonPos!=null){
	    		Position position=context.deserialize(jsonPos,Position.class);
	    		if(position!=null){
	    			((GravityPower) c).setPosition(position);
	    		}
	    	}
	    	if(jsonMag!=null){
	    		((GravityPower) c).setMagnitudo(jsonMag.getAsLong());
	    	}
	    	break;
	    case "Position":
	    	c = new Movement();
	    	JsonElement jsonMov=jsonObject.get("pos");
	    	if(jsonMov!=null){
	    		Position position=context.deserialize(jsonMov,Position.class);
	    		if(position!=null){
	    			((Movement) c).setPosition(position);
	    		}
	    	}
	    	break;
	    }
		return c;
	}

}
