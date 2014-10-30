package testnever.test;


import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class A
{
  private static String strDefaultKey = "";
  private Cipher decryptCipher = null;

  public A()
    throws Exception
  {
    this(strDefaultKey);
  }

  public A(String paramString)
    throws Exception
  {
    Key localKey = getKey(md5(paramString).getBytes());
    this.decryptCipher = Cipher.getInstance("DES");
    this.decryptCipher.init(2, localKey);
  }

  private Key getKey(byte[] paramArrayOfByte)
    throws Exception
  {
    byte[] arrayOfByte = new byte[8];
    for (int i = 0; ; i++)
    {
      if ((i >= paramArrayOfByte.length) || (i >= arrayOfByte.length))
        return new SecretKeySpec(arrayOfByte, "DES");
      arrayOfByte[i] = paramArrayOfByte[i];
    }
  }

  public static byte[] hexStr2ByteArr(String paramString)
    throws Exception
  {
    byte[] arrayOfByte1 = paramString.getBytes();
    int i = arrayOfByte1.length;
    byte[] arrayOfByte2 = new byte[i / 2];
    for (int j = 0; ; j += 2)
    {
      if (j >= i)
        return arrayOfByte2;
      String str = new String(arrayOfByte1, j, 2);
      arrayOfByte2[(j / 2)] = (byte)Integer.parseInt(str, 16);
    }
  }

  public static String md5(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(32);
    try
    {
      byte[] arrayOfByte = MessageDigest.getInstance("MD5").digest(paramString.getBytes("utf-8"));
      for (int i = 0; ; i++)
      {
        int j = arrayOfByte.length;
        if (i >= j)
          return localStringBuffer.toString();
        localStringBuffer.append(Integer.toHexString(0x100 | 0xFF & arrayOfByte[i]).toUpperCase().substring(1, 3));
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String decrypt(String paramString)
  {
    try
    {
      String str = new String(decrypt(hexStr2ByteArr(paramString)));
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public byte[] decrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return this.decryptCipher.doFinal(paramArrayOfByte);
  }
}
