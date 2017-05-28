package edu.wol.server.connector.ws.decoders;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class RSADecoder implements InitializingBean{
	public static final String PROPERTY_NAME_PRIVATE_KEY_PATH="ws.private.key";
	private PrivateKey privateKey;
	private Cipher decoder;

	@Resource
    private Environment environment;
	
	public RSADecoder() {
		privateKey=null;
		decoder=null;
	}
	
	public String decode(String encodedMsg) throws GeneralSecurityException, BadPaddingException {
		byte[]decodeTest = decoder.doFinal(Base64.decodeBase64(encodedMsg));
		return new String(decodeTest);

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init(environment.getRequiredProperty(PROPERTY_NAME_PRIVATE_KEY_PATH));
		
		
	}
	
	public void init(String privateKeyPath) throws Exception{
		/*File f = new File(privateKeyPath);
        FileInputStream fis = new FileInputStream(f);*/
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		org.springframework.core.io.Resource pk =resourceLoader.getResource(privateKeyPath);
        DataInputStream dis = new DataInputStream(pk.getInputStream());
        byte[] keyBytes = new byte[(int) pk.contentLength()];
        dis.readFully(keyBytes);
        dis.close();

        String temp = new String(keyBytes);
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

        byte[] decoded = Base64.decodeBase64(privKeyPEM);//Base64.decodeBase64(encodedPrivateKey.getBytes())
        
		privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		decoder = Cipher.getInstance("RSA");//, "BC");
		decoder.init(Cipher.DECRYPT_MODE, privateKey);
	}
}
