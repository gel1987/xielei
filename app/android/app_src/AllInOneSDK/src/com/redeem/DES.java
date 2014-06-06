package com.redeem;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
  /**
   * 加密
   * 
   * @param datasource
   * @param password
   * @return
   */
  private static byte[] encode(byte[] datasource, String password) {
    try {
      SecureRandom random = new SecureRandom();
      DESKeySpec desKey = new DESKeySpec(password.getBytes());
      // 创建一个密匙工厂，然后用它把DESKeySpec转换成
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey securekey = keyFactory.generateSecret(desKey);
      // Cipher对象实际完成加密操作
      Cipher cipher = Cipher.getInstance("DES");
      // 用密匙初始化Cipher对象
      cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
      // 现在，获取数据并加密
      // 正式执行加密操作
      return cipher.doFinal(datasource);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解密
   * 
   * @param src
   * @param password
   * @return
   * @throws Exception
   */
  private static byte[] decode(byte[] src, String password) {
    try {
      // DES算法要求有一个可信任的随机数源
      SecureRandom random = new SecureRandom();
      // 创建一个DESKeySpec对象
      DESKeySpec desKey = new DESKeySpec(password.getBytes());
      // 创建一个密匙工厂
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      // 将DESKeySpec对象转换成SecretKey对象
      SecretKey securekey = keyFactory.generateSecret(desKey);
      // Cipher对象实际完成解密操作
      Cipher cipher = Cipher.getInstance("DES");
      // 用密匙初始化Cipher对象
      cipher.init(Cipher.DECRYPT_MODE, securekey, random);
      // 真正开始解密操作
      return cipher.doFinal(src);
    } catch (Exception e) {
    }
    return null;
  }

  public static String aes(String src) {
    try {
      String password = "K9wNECXAzh0=";
      byte[] or = Base64.decode(src);
      return new String(decode(or, password));
    } catch (Exception e) {
      return null;
    }
  }

  public static void main(String[] args) {
    // 待加密内容
    String str = "10000";
    // 密码，长度要是8的倍数
    String password = "K9wNECXAzh0=";
    byte[] result = encode(str.getBytes(), password);
    System.out.println("加密后内容为：" + new String(result));
    String b64result = Base64.encode(result);
    System.out.println("加密后内容为：" + b64result);
    result = Base64.decode(b64result);
    // 直接将如上内容解密
    try {
      byte[] decryResult = decode(result, password);
      System.out.println("解密后内容为：" + new String(decryResult));
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

}
