

public class Utils {
	
	public static String toHex(byte[] data, int length) {
		final String digits = "0123456789abcdef";
		StringBuffer stringBuffer = new StringBuffer();
		
		for(int i = 0; i != length; i++) {
			int v = data[i] & 0xff;
			stringBuffer.append(digits.charAt(v >> 4));
			stringBuffer.append(digits.charAt(v & 0xf));
		}
		
		return stringBuffer.toString();
	}
}
