package edu.wol.server.connector.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.util.Assert;

import com.google.gson.Gson;

import edu.wol.dom.Phenomen;
import edu.wol.dom.Prospective;
import edu.wol.dom.shape.AsteroidShapeFactory;
import edu.wol.dom.space.Asteroid;
import edu.wol.dom.space.LiquidSphere;
import edu.wol.dom.space.Movement;
import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Position;
import edu.wol.dom.space.Rotation;
import edu.wol.dom.space.Vector3f;
import edu.wol.server.connector.ws.encoders.GsonFactory;

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
	public void testPhenomenSerializer() throws Exception{
			Phenomen<Planetoid> ph = new Phenomen<Planetoid>();
			Asteroid a = new Asteroid(Collections.singletonList("h2"),1,1);
			a.setShape(AsteroidShapeFactory.getInstance().generateHidrogenGemShape());
			ph.setEntity(a);
			ph.setPosition(new Position());
			ph.addEffect(new Rotation<Planetoid>(a,new Vector3f(1,0,0),Math.PI/2));
			
			Phenomen<LiquidSphere> ph2 = new Phenomen<LiquidSphere>();
			LiquidSphere hb = new LiquidSphere(Arrays.asList(new String[]{"h2","he","lt"}),3,3);
			ph2.setEntity(hb);
			ph2.setPosition(new Position(10,10,10));
			ph2.addEffect(new Rotation<LiquidSphere>(hb,new Vector3f(0,1,0),Math.PI));
			ph2.addEffect(new Movement<LiquidSphere>(hb,new Vector3f(0.01f,0.1f,1f)));
			
			List<Phenomen<?>> phenomens = new ArrayList<Phenomen<?>>(2);
			phenomens.add(ph);
			phenomens.add(ph2);
			
			Gson gson=GsonFactory.getInstance();
			String result=gson.toJson(phenomens);
			Assert.hasText(result, "Failed Phenomen Serialize");
			
			System.out.println("Ok Test Phenomen Serializer : "+result);
	}
	

}
