package edu.wol.server.connector.ws;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
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
import edu.wol.dom.WolEntity;
import edu.wol.dom.WorldContainer;
import edu.wol.dom.iEvent;
import edu.wol.dom.iEventObserver;
import edu.wol.dom.commands.Command;
import edu.wol.dom.services.UserInterface;
import edu.wol.dom.space.NewPosition;
import edu.wol.dom.space.Planetoid;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.decoders.GenericMessageDecoder;
import edu.wol.server.connector.ws.encoders.ProspectiveEncoder;
import edu.wol.server.connector.ws.encoders.ShapeEncoder;
import edu.wol.server.connector.ws.messages.EntitiesPayload;
import edu.wol.server.connector.ws.messages.GenericMessage;
import edu.wol.server.connector.ws.messages.UserPayload;

@ServerEndpoint( 
value = "/ws", 
subprotocols  = {"wol/1.0"},
encoders = { ShapeEncoder.class, ProspectiveEncoder.class }, 
decoders = { GenericMessageDecoder.class},
configurator = SpringConfigurator.class
) 
public class WebSocketEndpoint implements iEventObserver<WolEntity> {
	final static Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);
	private static final Map< Session, User > sessions = new ConcurrentHashMap< Session,User >(); 
	private User curUser=null;
	private Session curSession=null;
	@Autowired
	private UserInterface<?> ui;
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
		if(session!=null){
			sessions.remove( session );
			logger.info("ws connection closed: " + session.getId());
		}
	}
	
	@OnMessage
	public void onMessage( final GenericMessage msg, final Session session ) throws IOException, EncodeException {
		if(ui!=null){
			Object msgPayload =msg.getPayload();
			if(msgPayload!=null){
				try{
					if(msgPayload instanceof UserPayload){//TODO Da verificare e spostare in SessionStartMessageHandler
						UserPayload ssm = (UserPayload)msgPayload;
						logger.debug("Processing UserPayload "+ssm.getToken());
						User user=ui.loadUser(ssm.getUsername());
						if(user!=null){
							sessions.put(session, user);
							curUser=user;
							curSession=session;
							logger.info("User "+ssm.getUsername()+" logged " + session.getId());
							session.getAsyncRemote().sendObject(user.getProspective());
							if(user.getProspective().getWolID()!=null){
								WorldContainer<WolEntity,Position> wol=(WorldContainer<WolEntity,Position>)ui.loadWol(Long.parseLong(user.getProspective().getWolID().split("-")[1]));
								if(wol!=null){//TODO send all wolEntity
									EntitiesPayload<Planetoid,Position> ep= new EntitiesPayload<Planetoid,Position>();
									Collection<WolEntity> ce=(Collection<WolEntity>) wol.getSpace().getAllEntities();
									for(WolEntity e : ce){
										if(e instanceof Planetoid){
											Position p=wol.getSpace().getPosition(e);
											ep.addEntity((Planetoid)e, p);
										}
									}
									session.getAsyncRemote().sendObject(ep);
									wol.addEventObserver(this);//TODO add listener for push change
								}
							}
							
							
						}else{
							logger.warn("No user found:"+ssm.getUsername());
							session.getAsyncRemote().sendText("Invalid authentication");
							session.close();
						}
						
					}else if (msgPayload instanceof Command){
						Command cmd=(Command)msgPayload;
						User user=sessions.get(session);
						logger.info("User "+user.getUsername()+" " + session.getId()+" command:"+cmd);
						ui.executeUserCommand(user, cmd);
					}
				}catch(Exception e){
					logger.error("Error processing message "+msg.getSource(),e);
				}
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




	@Override
	public void processEvent(iEvent event) {
		if(event instanceof NewPosition){
			EntitiesPayload<Planetoid,Position> ep = new EntitiesPayload<Planetoid,Position>();
			NewPosition<Planetoid> np=(NewPosition<Planetoid>)event;
			ep.addEntity(np.getEntity(), np.getNewPosition());
			curSession.getAsyncRemote().sendObject(ep);
		}
		
	}
}
