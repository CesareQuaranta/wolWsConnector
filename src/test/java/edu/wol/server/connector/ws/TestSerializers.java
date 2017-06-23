package edu.wol.server.connector.ws;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wol.dom.Prospective;
import edu.wol.server.connector.ws.decoders.RSADecoder;
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
