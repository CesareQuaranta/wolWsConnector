package edu.wol.server.connector.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import edu.wol.dom.commands.Command;
import edu.wol.server.connector.ws.decoders.CommandDecoder;
import edu.wol.server.connector.ws.decoders.StartMessageDecoder;
import edu.wol.server.connector.ws.encoders.ShapeEncoder;

@ServerEndpoint( 
value = "/ws", 
subprotocols  = {"wol/1.0"},
encoders = { ShapeEncoder.class }, 
decoders = { StartMessageDecoder.class,CommandDecoder.class },
configurator = SpringConfigurator.class
) 
public class WebSocketEndpoint {
	final static Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);
	private static final Set< Session > sessions = Collections.synchronizedSet( new HashSet< Session >() ); 
	
	@OnOpen
	public void onOpen( final Session session ) {
		logger.info("ws connection opened: " + session.getId());
		sessions.add( session );
		session.addMessageHandler(new StartSessionHandler(session));
	}




	@OnClose
	public void onClose( final Session session ) {
	sessions.remove( session );
	logger.info("ws connection closed: " + session.getId());
	}
	/*
	@OnMessage
	public void onMessage( final Command cmd, final Session client ) throws IOException, EncodeException {
		client.getBasicRemote().sendObject( "Ok moto" );
	}*/
	
	@OnError
    public void onWebSocketError(Session session,Throwable cause)
    {
		logger.error("ws connection exception: " + session.getId(),cause);
    }
}
