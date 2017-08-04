package edu.wol.server.connector.ws;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.wol.dom.services.TestInterface;
import edu.wol.dom.shape.AsteroidShape;
import edu.wol.dom.shape.PlaneShape;
import edu.wol.dom.shape.PlanetShapeFactory;
import edu.wol.server.connector.ws.encoders.GsonFactory;
import edu.wol.server.connector.ws.encoders.ShapeEncoder;

@ServerEndpoint( 
value = "/test", 
subprotocols  = {"wol/1.0"},
encoders = { ShapeEncoder.class}, 
configurator = SpringConfigurator.class
) 
public class WebSocketTestsEndpoint {
	final static Logger logger = LoggerFactory.getLogger(WebSocketTestsEndpoint.class);
	@Autowired
	private TestInterface ui;
	@OnOpen
	public void onOpen( final Session session ) {
		logger.info("test connection opened: " + session.getId());
		session.getAsyncRemote().sendText("Menu:");
	}

	@OnClose
	public void onClose( final Session session ) {
		if(session!=null){
			logger.info("test connection closed: " + session.getId());
		}
	}
	
	@OnMessage
	public void onMessage( String msg, final Session session ) throws IOException, EncodeException {
		logger.debug("Processing msg "+msg);
		try {
			if(msg.startsWith("{\"hg\":")){
				session.getAsyncRemote().sendObject(processHidrogenGemRequest(msg.substring(6, msg.length()-1)));
			}else if(msg.startsWith("{\"isl\":")){
				session.getAsyncRemote().sendObject(processIslandRequest(msg.substring(7, msg.length()-1)));
			}else{
				logger.warn("Unsupported message:"+msg);
			}
			
		} catch (Exception e) {
			logger.error("Error testing generation asteroid shape " + session.getId(),e);
		}
	}
	private AsteroidShape processHidrogenGemRequest(String msg) throws IOException, Exception{
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Gson gson=GsonFactory.getInstance();
		Map<String, String> params=gson.fromJson(msg, type);
		String l1=params.get("l1");
		String l2=params.get("l2");
		String l3=params.get("l3");
		String l4=params.get("l4");
		String aX=params.get("aX");
		String a3X=params.get("a3X");
		String a1Y=params.get("a1Y");
		String a2Y=params.get("a2Y");
		String a3Y=params.get("a3Y");
		String aZ=params.get("aZ");
		String cXZ=params.get("cXZ");
		String cY=params.get("cY");
		float length1=Float.parseFloat(l1);
		float length2=Float.parseFloat(l2);
		float length3=Float.parseFloat(l3);
		float length4=Float.parseFloat(l4);
		double angoloX=Double.parseDouble(aX);
		double angolo3X=Double.parseDouble(a3X);
		double angolo1Y=Double.parseDouble(a1Y);
		double angolo2Y=Double.parseDouble(a2Y);
		double angolo3Y=Double.parseDouble(a3Y);
		double angoloZ=Double.parseDouble(aZ);
		float correctXZ=Float.parseFloat(cXZ);
		float correctY=Float.parseFloat(cY);
		return ui.generateHidrogenGemShape(length1,length2,length3,length4,angoloX,angolo3X,angolo1Y,angolo2Y,angolo3Y,angoloZ,correctXZ,correctY);
	}
	
	private PlaneShape processIslandRequest(String msg){
		int lod=0;
		double roughness=0;
		double maxHeigth=0;
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Gson gson=GsonFactory.getInstance();
		Map<String, String> params=gson.fromJson(msg, type);
		String pLod=params.get("lod");
		String pRoughness=params.get("rgh");
		String pHeigth=params.get("h");
		lod=Integer.parseInt(pLod);
		roughness=Double.parseDouble(pRoughness);
		maxHeigth=Double.parseDouble(pHeigth);
		return ui.generateIsland(lod, roughness, maxHeigth);
	}
	
	@OnError
    public void onWebSocketError(Session session,Throwable cause)
    {
		
		try {
			logger.error("ws generic connection exception: " + session.getId(),cause);
			session.close();
		} catch (IOException e) {
			logger.error("Remote io Error",e);
		}
    }
	
	private void generateHidrogenGem(){
		
	}

}
