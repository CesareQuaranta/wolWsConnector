package edu.wol.server.connector.ws.encoders;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import edu.wol.dom.shape.PlaneShape;

public class PlaneShapeSerializer extends AbstractShapeSerializer<PlaneShape> {
	final static Logger logger = LoggerFactory.getLogger(PlaneShapeSerializer.class);
	
	@Override
	public JsonElement serialize(PlaneShape src, Type typeOfSrc,JsonSerializationContext context) {
		JsonObject jsonShape=new JsonObject();
		jsonShape.add("type", new JsonPrimitive("plane"));
		serializeVertices(jsonShape,context,src.getVertices(),src.getFaces());
		return jsonShape;
	}

}
