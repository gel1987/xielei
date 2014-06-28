package com.dex;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

public class DexUtil {

  /**
   * 加密
   */
  public static void encrpt() {
  }

  /**
   * 解密
   * 
   * @param is
   * @return
   */
  public static byte[] decrypt(InputStream is) {
    GZIPInputStream inPutStream = null;
    try {
      inPutStream = new GZIPInputStream(is);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] readBuffer = new byte[1024];
      int len = 0;
      while ((len = inPutStream.read(readBuffer)) != -1) {
        buffer.write(readBuffer, 0, len);
      }
      byte[] bytes = buffer.toByteArray();
      buffer.close();
      return bytes;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * InputStream to Bytes
   * 
   * @param is
   * @return
   */
  public static byte[] is2Byte(InputStream is) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      int len = -1;
      byte[] buffer = new byte[1024];
      while ((len = is.read(buffer)) != -1) {
        bos.write(buffer, 0, len);
      }
      return bos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        bos.close();
      } catch (Exception e) {
      }
    }
    return null;
  }

  /**
   * Dex 拷贝到文件
   * 
   * @param dexIs
   * @param fileOS
   */
  public static void dex2File(InputStream dexIs, OutputStream fileOS) {
    int len = -1;
    byte[] b = new byte[1024];
    try {
      while ((len = dexIs.read(b)) != -1) {
        fileOS.write(b, 0, len);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (dexIs != null) {
          dexIs.close();
        }
      } catch (Exception e) {
      }
      try {
        if (fileOS != null) {
          fileOS.close();
        }
      } catch (Exception e) {
      }
    }
  }

  /**
   * 加密过的Dex 拷贝到文件
   * 
   * @param dexIs
   * @param fileOS
   */
  public static void lockDex2File(InputStream dexIs, OutputStream fileOS) {
    try {
      fileOS.write(decrypt(dexIs));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (dexIs != null) {
          dexIs.close();
        }
      } catch (Exception e) {
      }
      try {
        if (fileOS != null) {
          fileOS.close();
        }
      } catch (Exception e) {
      }
    }
  }
}
