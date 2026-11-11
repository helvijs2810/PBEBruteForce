import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class BruteForce {

	//String startingText, byte[] encryptedBytes, byte[] salt, int count, 
	private static final String CHARSET = "!\\\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	private static boolean passwordFound;
	
	public static void passwordCrack(String startingText, byte[] encryptedBytes, byte[] salt, int count, String combinations , int length) throws Exception {

		PBEKeySpec newKeySpec;
		//boolean isFound = false;
		String encryptedText = Utils.toHex(encryptedBytes, encryptedBytes.length);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey newSecret;
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, count);
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");

		if(combinations.length() == length) {			
			newKeySpec = new PBEKeySpec(combinations.toCharArray(), salt, count);
			newSecret = keyFactory.generateSecret(newKeySpec);
			cipher.init(Cipher.ENCRYPT_MODE, newSecret, paramSpec);
			byte[] newEncrypted = cipher.doFinal(startingText.getBytes());
			String temp = Utils.toHex(newEncrypted, newEncrypted.length);
			if(temp.equals(encryptedText)) {
				System.out.println("Password Found!: " + combinations);
				passwordFound = true;
			}
			return;
		}
		if(!passwordFound) {
			for(int i = 0; i < CHARSET.length(); i++) {
				passwordCrack(startingText, encryptedBytes, salt, count,combinations + CHARSET.charAt(i), length);
			}	
		}	
	}
	
}
