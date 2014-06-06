package com.gonglv.bydr2;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

public class up {

  private static Activity activity;

  private static String baseUrl = "http://download-souce.googlecode.com/svn/trunk/up/";

  private static Handler handler = null;

  public static void checkUp(Activity act) {
    activity = act;
    handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        switch (msg.what) {
          case 0:
            showUpdateDlg((String) msg.obj);
            break;
        }
      }
    };
    new Thread() {
      @Override
      public void run() {
        InputStreamParser<String> parser = new InputStreamParser<String>() {
          @Override
          public String parser(InputStream inputStream) {
            ByteArrayOutputStream out = null;
            try {
              out = new ByteArrayOutputStream();
              int len = -1;
              byte[] b = new byte[1024];
              while ((len = inputStream.read(b)) != -1) {
                out.write(b, 0, len);
              }
              handler.sendMessage(Message.obtain(handler, 0, out.toString()));
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              if (out != null) {
                try {
                  out.close();
                } catch (Exception e) {
                }
              }
            }
            return null;
          }
        };
        String url = baseUrl + activity.getPackageName();
        HttpUtils.get(url, parser);
      }
    }.start();
  }

  private static void showUpdateDlg(String info) {
    try {
      String[] up = info.split("\\|");

      // *|2*|3*
      // * : 版本号
      // 2* : 更新提示语
      // 3* : 更新地址
      int version = Integer.valueOf(up[0]);
      final String upaddr = up[2];
      int currentVersion = PackageUtils.getVersionCode(activity);
      if (version > currentVersion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("更新").setMessage("有新版本需要升级,请先升级再使用.");
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Uri uri = Uri.parse(upaddr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
          }
        });
        builder.setCancelable(false);
        builder.show();
      }
    } catch (Exception e) {
    }
  }
}
