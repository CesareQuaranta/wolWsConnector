package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.shape.AsteroidShape;
import edu.wol.dom.shape.Triangle;
import edu.wol.dom.space.Vector;

public class AsteroidShapeSerializer implements JsonSerializer<AsteroidShape> {

	@Override
	public JsonElement serialize(AsteroidShape src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonShape=new JsonObject();
		jsonShape.add("type", new JsonPrimitive("custom"));
		if(src.getMateriaID()!=null){
			jsonShape.add("material", new JsonPrimitive(src.getMateriaID()));
		}
		JsonArray jsonVertices=new JsonArray();
		jsonShape.add("vertices",jsonVertices);
		List<Vector> vertices=src.getVertices();
		for(Vector vertice:vertices){
			jsonVertices.add(context.serialize(vertice));
		}
		JsonArray jsonFaces=new JsonArray();
		jsonShape.add("faces",jsonFaces);
		List<Triangle> faces=src.getFaces();
		for(Triangle face:faces){
			JsonObject jsonFace=new JsonObject();
			jsonFace.add("v1", new JsonPrimitive(vertices.indexOf(face.getV1())));
			jsonFace.add("v2", new JsonPrimitive(vertices.indexOf(face.getV2())));
			jsonFace.add("v3", new JsonPrimitive(vertices.indexOf(face.getV3())));
			jsonFaces.add(jsonFace);
		}
		return jsonShape;
	}

}
