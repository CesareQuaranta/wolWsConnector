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
import edu.wol.dom.space.Vector3f;

public class AsteroidShapeSerializer implements JsonSerializer<AsteroidShape> {

	@Override
	public JsonElement serialize(AsteroidShape src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonShape=new JsonObject();
		jsonShape.add("type", new JsonPrimitive("custom"));
		JsonArray jsonVertices=new JsonArray();
		jsonShape.add("vertices",jsonVertices);
		List<Vector3f> vertices=src.getVertices();
		for(Vector3f vertice:vertices){
			jsonVertices.add(context.serialize(vertice));
		}
		JsonArray jsonFaces=new JsonArray();
		jsonShape.add("faces",jsonFaces);
		List<Triangle> faces=src.getFaces();
		for(Triangle face:faces){
			JsonObject jsonFace=new JsonObject();
			jsonFace.add("v1", new JsonPrimitive(new Integer(vertices.indexOf(face.getV1()))));
			jsonFace.add("v2", new JsonPrimitive(new Integer(vertices.indexOf(face.getV2()))));
			jsonFace.add("v3", new JsonPrimitive(new Integer(vertices.indexOf(face.getV3()))));
			jsonFaces.add(jsonFace);
		}
		return jsonShape;
	}

}
