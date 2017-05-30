package edu.wol.server.connector.ws.decoders;

import java.security.MessageDigest;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import edu.wol.server.connector.ws.SessionStartMessage;
/**
 * 
 * Date 27/mag/2017
 * @Author Cesare Quaranta
 * Scopo di questa classe è la validazione del primo messaggio di una conversazione, attraverso l'acquisizione e verifica di un token criptato
 * attraverso l'algoritmo asyncrono RSA
 */
public class StartMessageDecoder implements Decoder.Text<SessionStartMessage>{
	public static String PREFIX = "xToken:";
	final static Logger logger = LoggerFactory.getLogger(StartMessageDecoder.class);

	private RSADecoder tokenDecoder;
	@Override
	public void init(EndpointConfig config) {
		tokenDecoder = getApplicationContext().getBean(RSADecoder.class);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SessionStartMessage decode(String s) throws DecodeException {
		SessionStartMessage m = new SessionStartMessage();
		String token=s.substring(PREFIX.length());
		m.setToken(token);
		try {
			String decoded = tokenDecoder.decode(token);
			logger.debug(decoded);
			String[] decodedArray =decoded.split("\\|");
			if(decodedArray.length!=3){
				throw new Exception("Invalid Token");
			}
			long tokenDate = Long.parseLong(decodedArray[2]);
			if(System.currentTimeMillis()-tokenDate>30000){
				throw new Exception("Token Expired");
			}
			//TODO Check formato username
			m.setUsername(decodedArray[0]);
			//TODO Check formato ip e coerenza provenienza
			m.setIp(decodedArray[1]);
		} catch (Exception e) {
			String errorMessage="Errore di decodifica Token:"+token;
			logger.error(errorMessage, e);
			throw new DecodeException(s,errorMessage,e);
		} 
		return m;
	}

	@Override
	public boolean willDecode(String s) {
		return (s!=null && s.startsWith(PREFIX));
	}
	
	protected ApplicationContext getApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

}
