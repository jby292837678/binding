package com.binding.model.data.encrypt.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt {

	private static final byte[] IV_AES128_CTR = {0x25, 0x20, 0x78, 0x69, 0x67, 0x75, 0x26, 0x69, 0x6b, 0x65, 0x20, 0x49, 0x56, 0x20, 0x20, 0x25};


	public static final byte[] AES_EN_KEY_FIXED = {0x25, 0x20, 0x78, 0x69, 0x67, 0x75, 0x26, 0x69, 0x6b, 0x65, 0x20, 0x4b, 0x45, 0x59, 0x20, 0x25};


	public static char[] encryptAES(char[] content, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] cIn = new byte[content.length];
		for (int i = 0; i < cIn.length; i++)
			cIn[i] = (byte) content[i];
		byte[] out    = encryptAES(cIn, key);
		char   cOut[] = new char[cIn.length];
		for (int i = 0; i < cIn.length; i++)
			cOut[i] = (char) (out[i] & 0xff);
		return cOut;
	}

	public static byte[] encryptAES(byte[] content, byte[] key) {
		try {
			SecretKeySpec secretKeySpec   = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_AES128_CTR);
			Cipher cipher          = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static char[] decryptAES(char[] content, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] cIn = new byte[content.length];
		for (int i = 0; i < cIn.length; i++)
			cIn[i] = (byte) content[i];
		byte[] out    = decryptAES(cIn, key);
		char   cOut[] = new char[cIn.length];
		for (int i = 0; i < cIn.length; i++)
			cOut[i] = (char) (out[i] & 0xff);
		return cOut;
	}

	public static byte[] decryptAES(byte[] content, byte[] key) {
		try {
			SecretKeySpec secretKey       = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_AES128_CTR);
			Cipher cipher          = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

}
