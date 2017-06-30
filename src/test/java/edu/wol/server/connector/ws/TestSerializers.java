package edu.wol.server.connector.ws;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.springframework.util.Assert;

import com.google.gson.Gson;

import edu.wol.dom.Prospective;
import edu.wol.dom.shape.AsteroidShapeFactory;
import edu.wol.dom.space.Asteroid;
import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.encoders.GsonFactory;
import edu.wol.server.connector.ws.messages.EntitiesPayload;

public class TestSerializers {	
	
	 
	@Test
	public void testProspectiveSerializer() throws Exception{
			Prospective p = new Prospective(null);
			Gson gson=GsonFactory.getInstance();
			String result=gson.toJson(p);
			Assert.hasText(result, "Failed Prospective Serialize");
			Assert.isTrue(result.contains("fov"), "Error Serializing Prospective Fov missing");
			Assert.isTrue(result.contains("far"), "Error Serializing Prospective Far missing");
			Assert.isTrue(result.contains("near"), "Error Serializing Prospective Near missing");
			Assert.isTrue(result.contains("position"), "Error Serializing Prospective Position missing");
			Assert.isTrue(result.contains("focus"), "Error Serializing Prospective Focus missing");
			System.out.println("Ok Test Prospective Serializer : "+result);
	}
	
	@Test
	public void testEntitiesPayloadPlanetoidSerializer() throws Exception{
			EntitiesPayload<Planetoid,Position> ep = new EntitiesPayload<Planetoid,Position>();
			Asteroid a = new Asteroid(Collections.singletonList("h2"),1,1);
			a.setShape(AsteroidShapeFactory.getInstance().generateShape());
			ep.addEntity(a, new Position());
			Asteroid a2 = new Asteroid(Collections.singletonList("h2"),3,3);
			a2.setShape(AsteroidShapeFactory.getInstance().generateShape());
			ep.addEntity(a2, new Position(10,10,10));
			Gson gson=GsonFactory.getInstance();
			String result=gson.toJson(ep);
			Assert.hasText(result, "Failed EntitiesPayload Planetoid Serialize");
			
			System.out.println("Ok Test EntitiesPayload Planetoid Serializer : "+result);
	}
	

}
