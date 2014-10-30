package testnever.test;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Resources;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@SuppressLint({"DefaultLocale"})
public class ResourceUtil
{
  private static String str;
  private static final char[] wJ = "0123456789abcdef".toCharArray();

  public static String decrypt(String paramString, Context paramContext)
  {
    if (str == null)
      str = getSingInfo(paramContext);
    try
    {
      String str1 = new A(str).decrypt(paramString);
      return str1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString;
  }

  public static int getAnimId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "anim", paramContext.getPackageName());
  }

  public static int getArrayId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "array", paramContext.getPackageName());
  }

  public static int getAttrId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "attr", paramContext.getPackageName());
  }

  public static int getBoolId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "bool", paramContext.getPackageName());
  }

  public static int getColorId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "color", paramContext.getPackageName());
  }

  public static int getDimenId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "dimen", paramContext.getPackageName());
  }

  public static int getDrawableId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "drawable", paramContext.getPackageName());
  }

  public static int getId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "id", paramContext.getPackageName());
  }

  public static int getIdInterger(String paramString)
  {
    String str1 = getString(paramString);
    if (str1.indexOf("0x") != -1)
      str1 = str1.substring(2);
    return Integer.parseInt(str1, 16);
  }

  public static int getIntegerId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "integer", paramContext.getPackageName());
  }

  public static int getLayoutId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "layout", paramContext.getPackageName());
  }

  public static int getMenuId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "menu", paramContext.getPackageName());
  }

  public static int getRawId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "raw", paramContext.getPackageName());
  }

  private static String getSingInfo(Context paramContext)
  {
    try
    {
      String str1 = parseSignature(paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures[0].toByteArray());
      return str1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String getString(String paramString)
  {
    try
    {
      String str1 = new A().decrypt(paramString);
      return str1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString;
  }

  public static int getStringId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "string", paramContext.getPackageName());
  }

  public static int getStyleId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "style", paramContext.getPackageName());
  }

  public static int getXmlId(Context paramContext, String paramString)
  {
    String str1 = decrypt(paramString, paramContext);
    return paramContext.getResources().getIdentifier(str1, "xml", paramContext.getPackageName());
  }

  private static byte[] j(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      return arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return null;
  }

  private static String k(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder(3 * paramArrayOfByte.length);
    int i = paramArrayOfByte.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return localStringBuilder.toString().toUpperCase();
      int k = 0xFF & paramArrayOfByte[j];
      localStringBuilder.append(wJ[(k >> 4)]);
      localStringBuilder.append(wJ[(k & 0xF)]);
    }
  }

  public static String parseSignature(byte[] paramArrayOfByte)
  {
    try
    {
      String str1 = k(j(((X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(paramArrayOfByte))).getEncoded()));
      return str1;
    }
    catch (CertificateException localCertificateException)
    {
      localCertificateException.printStackTrace();
    }
    return null;
  }
}
