package edu.wol.server.connector.ws;

import org.junit.Test;
import org.springframework.util.Assert;

import com.google.gson.Gson;

import edu.wol.dom.commands.Command;
import edu.wol.dom.commands.GravityPower;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.encoders.GsonFactory;
import edu.wol.server.connector.ws.encoders.ProspectiveEncoder;

public class TestDeserializers {	
	private static ProspectiveEncoder prEncoder;
	
	 
	@Test
	public void testPositionDeserializer() throws Exception{
			String json="{x:10,y:20,z:30}";
			Gson gson=GsonFactory.getInstance();
			Position p=gson.fromJson(json, Position.class);
			Assert.notNull(p, "Failed Position Deserialize");
			Assert.isTrue(p.getX()==10,"Invalid x Deserialization");
			Assert.isTrue(p.getY()==20,"Invalid y Deserialization");
			Assert.isTrue(p.getZ()==30,"Invalid z Deserialization");
	}
	
	@Test
	public void testGravityCommandDeserializer() throws Exception{
			String json="{type:'Gravity',pos:{x:0,y:0,z:0},mag:10}";
			Gson gson=GsonFactory.getInstance();
			Command c=gson.fromJson(json, Command.class);
			Assert.notNull(c, "Failed Command Deserialize");
			Assert.isTrue(c instanceof GravityPower,"Invalid Command Type Deserialization");
			Assert.notNull(((GravityPower)c).getPosition(), "Failed Command Position Deserialize");
			Assert.notNull(((GravityPower)c).getMagnitudo(), "Failed Command Magnitudo Deserialize");
	}
	

}
