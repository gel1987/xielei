package com.mdplay.androgensroms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {
  /**
   * 解压缩功能. 将zipFile文件解压到folderPath目录下.
   * 
   * @throws Exception
   */
  public static boolean unZipFile(File zipFile, String folderPath) {
    ZipFile zfile = null;
    InputStream is = null;
    OutputStream os = null;
    try {
      zfile = new ZipFile(zipFile);
      Enumeration zList = zfile.entries();
      ZipEntry ze = null;
      byte[] buf = new byte[1024];
      while (zList.hasMoreElements()) {
        ze = (ZipEntry) zList.nextElement();
        if (ze.isDirectory()) {
          String dirstr = folderPath + ze.getName();
          // dirstr.trim();
          dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
          File f = new File(dirstr);
          f.mkdir();
          continue;
        }
        os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
        is = new BufferedInputStream(zfile.getInputStream(ze));
        int readLen = 0;
        while ((readLen = is.read(buf, 0, 1024)) != -1) {
          os.write(buf, 0, readLen);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (zfile != null)
          zfile.close();
      } catch (IOException e) {
      }
      try {
        if (is != null)
          is.close();
      } catch (IOException e) {
      }
      try {
        if (os != null)
          os.close();
      } catch (IOException e) {
      }
    }
    return true;
  }

  /**
   * 给定根目录，返回一个相对路径所对应的实际文件名.
   * 
   * @param baseDir
   *          指定根目录
   * @param absFileName
   *          相对路径名，来自于ZipEntry中的name
   * @return java.io.File 实际的文件
   */
  public static File getRealFileName(String baseDir, String absFileName) {
    String[] dirs = absFileName.split("/");
    File ret = new File(baseDir);
    String substr = null;
    if (dirs.length > 1) {
      for (int i = 0; i < dirs.length - 1; i++) {
        substr = dirs[i];
        try {
          // substr.trim();
          substr = new String(substr.getBytes("8859_1"), "GB2312");

        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        ret = new File(ret, substr);

      }
      if (!ret.exists())
        ret.mkdirs();
      substr = dirs[dirs.length - 1];
      try {
        // substr.trim();
        substr = new String(substr.getBytes("8859_1"), "GB2312");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      ret = new File(ret, substr);
      return ret;
    } else {
      return new File(ret, absFileName);
    }
  }
}
