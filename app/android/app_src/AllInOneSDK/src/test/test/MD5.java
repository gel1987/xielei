package test.test;

/************************************************
 MD5 算法的Java Bean
 @author:ZHL
 Last Modified:10,Mar,2008
 *************************************************/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static char[] num_chars = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private MD5() {
	}

	public static String toMD5String(String input) {
		final char[] output = new char[32];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] by = md.digest(input.getBytes());
			for (int i = 0; i < by.length; i++) {
				output[2 * i] = num_chars[(by[i] & 0xf0) >> 4];
				output[2 * i + 1] = num_chars[by[i] & 0xf];
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return new String(output);
	}
}
