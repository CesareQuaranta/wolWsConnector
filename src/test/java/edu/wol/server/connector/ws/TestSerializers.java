package edu.wol.server.connector.ws;

import org.junit.Test;

import com.google.gson.Gson;

import edu.wol.dom.Prospective;
import edu.wol.server.connector.ws.encoders.GsonFactory;
import edu.wol.server.connector.ws.encoders.ProspectiveEncoder;

public class TestSerializers {	
	private static ProspectiveEncoder prEncoder;
	
	 
	@Test
	public void testProspectiveSerializer() throws Exception{
			Prospective p = new Prospective(null);
			Gson gson=GsonFactory.getInstance();
			String result=gson.toJson(p);
			System.out.println("Test Prospective Encoder : "+result);
	}
	
	
	

}
