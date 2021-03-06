package edu.wol.server.connector.ws.encoders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wol.dom.Phenomen;
import edu.wol.dom.Prospective;
import edu.wol.dom.commands.Command;
import edu.wol.dom.shape.AsteroidShape;
import edu.wol.dom.shape.PlaneShape;
import edu.wol.dom.shape.PlanetShape;
import edu.wol.dom.shape.SphericalShape;
import edu.wol.dom.space.Asteroid;
import edu.wol.dom.space.LiquidSphere;
import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Position;
import edu.wol.dom.space.Rotation;
import edu.wol.dom.space.Vector3f;
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
			builder.registerTypeAdapter(Vector3f.class, new VectorSerializer());
			builder.registerTypeAdapter(Position.class, new PositionSerializer());
			builder.registerTypeAdapter(Prospective.class, new ProspectiveSerializer());
			builder.registerTypeAdapter(Rotation.class, new RotationSerializer());
			builder.registerTypeAdapter(AsteroidShape.class, new AsteroidShapeSerializer());
			builder.registerTypeAdapter(PlanetShape.class, new PlanetShapeSerializer());
			builder.registerTypeAdapter(SphericalShape.class, new SphereShapeSerializer());
			builder.registerTypeAdapter(PlaneShape.class, new PlaneShapeSerializer());
			builder.registerTypeAdapter(Asteroid.class, new PlanetoidSerializer<Asteroid>());
			builder.registerTypeAdapter(LiquidSphere.class, new PlanetoidSerializer<LiquidSphere>());
			builder.registerTypeAdapter(Phenomen.class, new PhenomenSerializer<Phenomen<Planetoid>>());
			//Deserializers
			builder.registerTypeAdapter(Position.class, new PositionDeserializer());
			builder.registerTypeAdapter(Command.class, new CommandDeserializer());
			
			instance =builder.create();
		}
		return instance;
	}
}
