package edu.wol.server.connector.ws;

import java.io.IOException;

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

import edu.wol.dom.services.TestInterface;
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
			logger.info("tet connection closed: " + session.getId());
		}
	}
	
	@OnMessage
	public void onMessage( String msg, final Session session ) throws IOException, EncodeException {
		logger.debug("Processing msg "+msg);
		try {
			session.getAsyncRemote().sendObject(ui.generateAsteroidShape());
		} catch (Exception e) {
			logger.error("Error testing generation asteroid shape " + session.getId(),e);
		}
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

}
