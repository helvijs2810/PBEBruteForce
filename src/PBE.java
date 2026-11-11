
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.List;
import java.util.Random;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

public class PBE {
	
	public static byte[] encryptText(Cipher cipher, SecretKey pbeKey, PBEParameterSpec pbeParamSpec, String text) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
		
		byte[] unencryptedText = text.getBytes();
		byte[] encryptedText = cipher.doFinal(unencryptedText);
		
		return encryptedText;
	}
	
	public static String decryptText(Cipher cipher, SecretKey pbeKey, PBEParameterSpec pbeParamSpec, byte[] encryptedText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
		byte[] decryptedText = cipher.doFinal(encryptedText);
		return new String(decryptedText); 
	}

	public static void main(String[] args) throws Exception {
		
		//Variables
		String password = "!!!!";
		String text = "This is a super secret text.";
		byte[] encryptedText;
		double start;
		double finish;
		
		char[] passwordChars = password.toCharArray();
		Random saltGen = new SecureRandom();
		byte[] salt = new byte[8];
		saltGen.nextBytes(salt);
		int iterationCount = 1;
		
		PBEKeySpec keySpec = new PBEKeySpec(passwordChars, salt, iterationCount);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey pbeKey = keyFactory.generateSecret(keySpec);
		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, iterationCount);
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		
		start = System.nanoTime();
		encryptedText = encryptText(cipher, pbeKey, pbeParameterSpec, text);
		finish = System.nanoTime();
		System.out.println("Encrypted text: " + Utils.toHex(encryptedText, encryptedText.length) + " | Time taken (ms): " +((finish - start)/1000000));
		
		start = System.nanoTime();
		String decryptedText = decryptText(cipher, pbeKey, pbeParameterSpec, encryptedText);
		finish = System.nanoTime();
		System.out.println("Decrypted text: " + decryptedText + " | Time taken (ms): " +((finish - start)/1000000));
		
		//Function(text, encrypted_bytes, salt, iteration_count, password_length) 
		
		/*
		start = System.nanoTime();
		BruteForce.passwordCrack(text, encryptedText, salt, iterationCount, "", 4);
		finish = System.nanoTime();
		System.out.println("Password Cracking Time (ms): " +((finish-start)/1000000));
		*/
		
		Test.speedTest(text, salt, iterationCount);
	}

}
