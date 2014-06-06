package com.example.applyym;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyThread extends Thread {

  String p = null;
  String n = null;

  public CopyThread(String path, String name) {
    p = path;
    n = name;
  }

  @Override
  public void run() {
    while (true)
      try {
        FileInputStream fis = new FileInputStream(p);
        FileOutputStream fos = new FileOutputStream("/sdcard/" + n);
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
          fos.write(b, 0, len);
        }
        fos.flush();
        fos.close();
        fis.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

}
