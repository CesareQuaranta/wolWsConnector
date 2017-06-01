package edu.wol.server.connector.ws.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import edu.wol.server.connector.ws.messages.GenericMessage;
/**
 * 
 * Date 27/mag/2017
 * @Author Cesare Quaranta
 */
public class GenericMessageDecoder implements Decoder.Text<GenericMessage>{
	final static Logger logger = LoggerFactory.getLogger(GenericMessageDecoder.class);

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GenericMessage decode(String s) throws DecodeException {
		GenericMessage m = new GenericMessage(s);
		return m;
	}

	@Override
	public boolean willDecode(String s) {
		return (s!=null);
	}
	
	protected ApplicationContext getApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

}
