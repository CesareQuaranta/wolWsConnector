package edu.wol.server.connector.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
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

import edu.wol.dom.User;
import edu.wol.dom.commands.Command;
import edu.wol.dom.services.UserInterface;
import edu.wol.server.connector.ws.decoders.CommandDecoder;
import edu.wol.server.connector.ws.decoders.GenericMessageDecoder;
import edu.wol.server.connector.ws.decoders.StartMessageDecoder;
import edu.wol.server.connector.ws.encoders.ShapeEncoder;
import edu.wol.server.connector.ws.messages.CommandMessage;
import edu.wol.server.connector.ws.messages.GenericMessage;
import edu.wol.server.connector.ws.messages.SessionStartMessage;

@ServerEndpoint( 
value = "/ws", 
subprotocols  = {"wol/1.0"},
encoders = { ShapeEncoder.class }, 
decoders = { StartMessageDecoder.class,CommandDecoder.class,GenericMessageDecoder.class },
configurator = SpringConfigurator.class
) 
public class WebSocketEndpoint {
	final static Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);
	private static final Map< Session, User > sessions = new ConcurrentHashMap< Session,User >(); 
	@Autowired
	private UserInterface ui;
	@OnOpen
	public void onOpen( final Session session ) {
		logger.info("ws connection opened: " + session.getId());
		//sessions.put( session,null );
		if(session.getMessageHandlers().isEmpty()){
			session.addMessageHandler(new StartSessionHandler(session));
			logger.debug("New StartSessionHandler associate to Session " + session.getId());
		}
	}




	@OnClose
	public void onClose( final Session session ) {
	sessions.remove( session );
	logger.info("ws connection closed: " + session.getId());
	}
	
	@OnMessage
	public void onMessage( final GenericMessage msg, final Session session ) throws IOException, EncodeException {
		if(ui!=null){
			if(msg instanceof SessionStartMessage){//TODO Da verificare e spostare in SessionStartMessageHandler
				SessionStartMessage ssm = (SessionStartMessage)msg;
				User user=ui.loadUser(ssm.getUsername());
				if(user!=null){
					sessions.put(session, user);
					logger.info("User "+ssm.getUsername()+" logged " + session.getId());
					session.getAsyncRemote().sendObject(user.getProspective());
					//TODO send all wolEntity
					//TODO add listener for push change
				}
				
			}else if (msg instanceof CommandMessage){
				CommandMessage cmd=(CommandMessage)msg;
				User user=sessions.get(session);
				logger.info("User "+user.getUsername()+" " + session.getId()+" command:"+cmd.getCommand());
			}
		}else{
			logger.warn("No UI Configured message processing disabled");
		}
		
	}
	
	
	@OnError
    public void onWebSocketError(Session session,Throwable cause)
    {
		logger.error("ws connection exception: " + session.getId(),cause);
		try {
		if(cause instanceof DecodeException){
			
			if(((DecodeException)cause).getMessage().contains("Could not decode string")){
				session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY,((DecodeException)cause).getMessage()));	
			}else if(((DecodeException)cause).getMessage().contains("Errore di decodifica Token")){
					session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY,((DecodeException)cause).getMessage()));
			}
		}
		} catch (IOException e) {
			logger.error("Remote io Error",e);
		}
    }
}
