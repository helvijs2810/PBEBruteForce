import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Test {
	
	public static void speedTest(String startingText, byte[] salt, int count) throws Exception {

		PBEKeySpec newKeySpec;
		//boolean isFound = false;
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey newSecret;
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, count);
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		int counter = 0;
		
		double start = System.nanoTime();
		while(true) {
			newKeySpec = new PBEKeySpec("P@S$W0rD".toCharArray(), salt, count);
			newSecret = keyFactory.generateSecret(newKeySpec);
			cipher.init(Cipher.ENCRYPT_MODE, newSecret, paramSpec);
			byte[] newEncrypted = cipher.doFinal(startingText.getBytes());
			String temp = Utils.toHex(newEncrypted, newEncrypted.length);
			counter++;
			//System.out.println("A");
			double finish = System.nanoTime();
			if(finish-start > (double)1000000000) {
				break;
			}
		}

		System.out.println("Encryptions per second: " + counter);
	
	}
}
