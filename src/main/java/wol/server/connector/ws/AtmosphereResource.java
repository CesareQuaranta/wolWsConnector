package wol.server.connector.ws;

import static org.atmosphere.cpr.ApplicationConfig.MAX_INACTIVE;
import static org.atmosphere.cpr.ApplicationConfig.DROP_ACCESS_CONTROL_ALLOW_ORIGIN_HEADER;

import java.io.IOException;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.Heartbeat;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.interceptor.CorsInterceptor;
import org.slf4j.LoggerFactory;

//,interceptors={CorsInterceptor.class}
//,"org.atmosphere.cpr.ApplicationConfig.dropAccessControlAllowOriginHeader=true"
@ManagedService (path="/atmosphere/atm",atmosphereConfig = {MAX_INACTIVE+ "=120000"})
public class AtmosphereResource {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(AtmosphereResource.class);
	
	@Get
	public void onGet(AtmosphereResource r) {
		  logger.info("Ping");
	}
	
	@Ready
    public void onReady(final AtmosphereResource r) {
        logger.info("Browser {} connected.");
    }

	//Richieste dal client sia sincrone che asincrone
	public void onRequest(AtmosphereResource resource)throws IOException{
		
	}
	
	//Risposte verso il client
	public void onStateChange(AtmosphereResourceEvent event) throws IOException{
		event.setMessage("");
		//AtmosphereResponse.newInstance().getWriter();
	}
	
   @org.atmosphere.config.service.Message(encoders = {JacksonEncoder.class}, decoders = {JacksonDecoder.class})
    public String onMessage(AtmosphereResource r,String message) throws IOException {
        logger.info("{} just send {}");
        return message;
    }

    @Heartbeat
    public void onHeartbeat(final AtmosphereResourceEvent event) {
        logger.trace("Heartbeat send by {}", event.getResource());
    }
	
    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

	    /*
    public class JacksonEncoder implements Encoder<JacksonEncoder.Encodable, String> {
    	
    	    private final ObjectMapper mapper = new ObjectMapper();
    	
    	    @Override
    	    public String encode(Encodable m) {
    	        try {
    	            return mapper.writeValueAsString(m);
    	        } catch (IOException e) {
    	           throw new RuntimeException(e);
    	        }
    	    }
    	    public static interface Encodable {
            }
    }*/
    

}
