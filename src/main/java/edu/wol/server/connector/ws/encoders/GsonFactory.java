package edu.wol.server.connector.ws.encoders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wol.dom.Phenomen;
import edu.wol.dom.Prospective;
import edu.wol.dom.commands.Command;
import edu.wol.dom.shape.AsteroidShape;
import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.space.Asteroid;
import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Position;
import edu.wol.dom.space.Vector;
import edu.wol.server.connector.ws.decoders.CommandDeserializer;
import edu.wol.server.connector.ws.decoders.PositionDeserializer;

public class GsonFactory {
	private static Gson instance = null;
	public synchronized static Gson getInstance(){
		if(instance==null){
			GsonBuilder builder= new GsonBuilder();
			builder.enableComplexMapKeySerialization();
			builder.setPrettyPrinting();//TODO Disable 4 production
			//Serializer
			builder.registerTypeAdapter(Vector.class, new VectorSerializer());
			builder.registerTypeAdapter(Position.class, new PositionSerializer());
			builder.registerTypeAdapter(Prospective.class, new ProspectiveSerializer());
			builder.registerTypeAdapter(AsteroidShape.class, new AsteroidShapeSerializer());
			builder.registerTypeAdapter(PlanetShape.class, new PlanetShapeSerializer());
			builder.registerTypeAdapter(Asteroid.class, new PlanetoidSerializer<Asteroid>());
			builder.registerTypeAdapter(Phenomen.class, new PhenomenSerializer<Phenomen<Planetoid>>());
			//Deserializers
			builder.registerTypeAdapter(Position.class, new PositionDeserializer());
			builder.registerTypeAdapter(Command.class, new CommandDeserializer());
			
			instance =builder.create();
		}
		return instance;
	}
}
