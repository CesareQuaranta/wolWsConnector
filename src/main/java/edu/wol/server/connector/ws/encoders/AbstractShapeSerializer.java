package edu.wol.server.connector.ws.encoders;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.wol.dom.shape.AbstractCustomShape;
import edu.wol.dom.shape.Triangle;
import edu.wol.dom.space.Vector3f;

public abstract class AbstractShapeSerializer<T extends AbstractCustomShape> implements JsonSerializer<T> {
	final static Logger logger = LoggerFactory.getLogger(AbstractShapeSerializer.class);
	
	protected void serializeVertices(JsonObject jsonShape,JsonSerializationContext context,List<Vector3f> vertices,List<Triangle> faces){
		JsonArray jsonVertices=new JsonArray();
		jsonShape.add("vertices",jsonVertices);
		for(Vector3f vertice:vertices){
			jsonVertices.add(context.serialize(vertice));
		}
		JsonArray jsonFaces=new JsonArray();
		jsonShape.add("faces",jsonFaces);
		for(Triangle face:faces){
			JsonObject jsonFace=new JsonObject();
			jsonFace.add("v1", new JsonPrimitive(new Integer(vertices.indexOf(face.getV1()))));
			jsonFace.add("v2", new JsonPrimitive(new Integer(vertices.indexOf(face.getV2()))));
			jsonFace.add("v3", new JsonPrimitive(new Integer(vertices.indexOf(face.getV3()))));
			jsonFaces.add(jsonFace);
		}
	}

}
