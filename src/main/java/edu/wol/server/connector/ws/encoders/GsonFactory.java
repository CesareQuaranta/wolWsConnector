package edu.wol.server.connector.ws.encoders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wol.dom.Prospective;
import edu.wol.dom.commands.Command;
import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.decoders.CommandDeserializer;
import edu.wol.server.connector.ws.decoders.PositionDeserializer;

public class GsonFactory {
	private static Gson instance = null;
	public synchronized static Gson getInstance(){
		if(instance==null){
			GsonBuilder builder= new GsonBuilder();
			builder.enableComplexMapKeySerialization();
			builder.setPrettyPrinting();//TODO Disable 4 production
			builder.registerTypeAdapter(Position.class, new PositionSerializer());
			builder.registerTypeAdapter(Prospective.class, new ProspectiveSerializer());
			builder.registerTypeAdapter(PlanetShape.class, new PlanetShapeSerializer());
			builder.registerTypeAdapter(Position.class, new PositionDeserializer());
			builder.registerTypeAdapter(Command.class, new CommandDeserializer());
			
			instance =builder.create();
		}
		return instance;
	}
}
