package com.cencosud.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Path("/cencosud/util")
public class EncoderDecoder {

	public static final String KEY_PASS_AES = "E8f.c7e5#3$16w9G";
	
//	public static final String KEY_PASS_AES = "Z8nc.4m5#2$16r2K";
	
	public static final SecretKeySpec SECRET_KEY = new SecretKeySpec(KEY_PASS_AES.getBytes(), "AES");

	@POST
	@Path("/encode")
	@Consumes("application/json")
	public static String encode(JSONObject jsonObject) {
		String password = (String) jsonObject.get("password");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
			final String encryptedString = new BASE64Encoder().encode(cipher.doFinal(password.getBytes("UTF8")));
			return encryptedString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@POST
	@Path("/decode")
	@Consumes("application/json")
	public static String decode(JSONObject jsonObject) {
		String text = (String) jsonObject.get("password");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
			final String decryptedString = new String(cipher.doFinal((new BASE64Decoder()).decodeBuffer(text)));
			return decryptedString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}