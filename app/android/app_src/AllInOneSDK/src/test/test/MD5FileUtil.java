package test.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5FileUtil {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ȡ�ļ�MD5
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5String(File file) {
		FileInputStream in = null;
		FileChannel ch = null;
		try {
			in = new FileInputStream(file);
			ch = in.getChannel();
			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
					0, file.length());
			messagedigest.update(byteBuffer);
			return bufferToHex(messagedigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ch != null)
					ch.close();
			} catch (Exception e1) {
			} finally {
				try {
					if (in != null)
						in.close();
				} catch (Exception e2) {
				}
			}

		}
	}

	public static String getStreamMD5String(InputStream is) {
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = is.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			messagedigest.update(bos.toByteArray());
			return bufferToHex(messagedigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e1) {
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e2) {
				}
			}

		}
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

}
