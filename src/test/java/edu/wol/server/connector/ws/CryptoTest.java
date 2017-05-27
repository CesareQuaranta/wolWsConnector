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

import edu.wol.server.connector.ws.decoders.RSADecoder;

public class CryptoTest {	
	private static RSADecoder decoder;
	
	  @BeforeClass
		public static void init() throws Exception {
		  decoder = new RSADecoder();
		  decoder.init("src/test/resources/private.key");
	  }
	@Test
	public void testKeyGeneration() throws Exception{
		KeyPairGenerator kpg=null;
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			KeyPair kp = kpg.generateKeyPair();
			PublicKey pubblica=kp.getPublic();
			PrivateKey privata=kp.getPrivate();
			
			
			System.out.println("Test generazioni chiavi a 1024 bit");
			System.out.println("Chiave Pubblica:"+ new String(Base64.encodeBase64(pubblica.getEncoded())));
			System.out.println("Chiave Privata:"+new String(Base64.encodeBase64(privata.getEncoded())));
	}
	
	@Test
	public void testCodifica() throws Exception{
		String messaggio="Messaggio segreto del:"+System.currentTimeMillis();
		System.out.println("Test codifica con chiave pubblica");
		String msgCodificato = codifica(messaggio);
		System.out.println(msgCodificato);
		String msgDecodificato =decoder.decode(msgCodificato);
		Assert.assertTrue("Codifica fallita", messaggio.equals(msgDecodificato));
		System.out.println("Check Codifica OK");
	}
	
	@Test
	public void testDecodifica() throws Exception{
		String encodedMessage="pNtuGzV31rMRGGicknzPfUVq8g0cJMSQsD6rNY473FN+m+Kh6DakInJDD5myRIcALUDPSox4BWMQIhuXHjOcLBDrMzonWTZub3S4zOKNkjOWspr2nz8Fly2yAg5/cj1ANCEEuMl6ApBcsqdhoJdn8PxTAnNfNXRSzcipDPvUt4IYN7RtLgS5af9o6A8Tkgkh3G/A0w/H9gtUw9TF2Lz5mKllJKOmhX+O5t8Xe7bTgzDN30QnAK+FwbYnaHB9a7GzkgO2+EVDJaISHhcizPJOjiHIUEJoBL6ojESE0Q9JGELFwAt/++kDMSBt46xwHaV82K9YCZcqi6EJMsdxYMo7EQ==";
		String originalMessage="Messaggio segreto di prova";
		System.out.println("Test decodifica con chiave private");
		System.out.println(encodedMessage);
		String decodedMessage=decoder.decode(encodedMessage);
		Assert.assertTrue("Decodifica fallita", originalMessage.equals(decodedMessage));
		System.out.println("Check Decodifica OK");
	}
	
	@Test
	public void testDecodificaAes() throws Exception{
		String token="fBPn1wfKvvVugCcUeHDATDMMmsd67etNkmSIGZaICPyYoWlMNk8vNdlbcEbOqjMuHSDlU4ZhqDWE073IBkZEsQ==";
		System.out.println("Test decodifica AES");
		System.out.println(token);
		byte[] keyb = "somepassword".getBytes("UTF-8");
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] thedigest = md.digest(keyb);
	    SecretKeySpec skey = new SecretKeySpec(thedigest, "AES");
	    Cipher dcipher = Cipher.getInstance("AES");
	    dcipher.init(Cipher.DECRYPT_MODE, skey);

	    byte[] clearbyte = dcipher.doFinal(Base64.decodeBase64(token));
	    String decodedToken = new String(clearbyte);
		Assert.assertTrue("Decodifica fallita", decodedToken.startsWith("cesare.quaranta@gmail.com"));
		System.out.println("Check Decodifica Token <"+decodedToken+"> OK");
	}
	private String codifica(String msg) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		 File f = new File("src/test/resources/public.key");
         FileInputStream fis = new FileInputStream(f);
         DataInputStream dis = new DataInputStream(fis);
         byte[] keyBytes = new byte[(int) f.length()];
         dis.readFully(keyBytes);
         dis.close();

         String temp = new String(keyBytes);
         String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
         publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");


         byte[] decoded = Base64.decodeBase64(publicKeyPEM);//Base64.decodeBase64(encodedPublicKey.getBytes())

		PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");	
		c.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] originalMessageBytes=msg.getBytes();
		byte[] encodeTest = Base64.encodeBase64(c.doFinal(originalMessageBytes));
		return new String(encodeTest);
	}
	
	

}
