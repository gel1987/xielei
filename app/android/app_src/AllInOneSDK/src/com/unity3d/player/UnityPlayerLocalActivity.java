package com.unity3d.player;

import com.cdd.freetime.FreeTime;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

public class UnityPlayerLocalActivity extends Activity {

  private xb sign;

  @Override
  public Context getBaseContext() {
    return super.getBaseContext();
  }

  @Override
  public PackageManager getPackageManager() {
    if (sign == null) {
      sign = new xb();
      sign.setPackageManager(super.getPackageManager());
    }
    return sign;
  }

  @Override
  public Context getApplicationContext() {
    return super.getApplicationContext();
  }

  @Override
  public String getPackageName() {
    return "";
  }

  public static void unity() {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        try {
          if (!FreeTime.canPlay) {
            System.exit(0);
          }
        } catch (Exception e) {
          System.exit(0);
        }
      }
    }, 1000 * 60 * 10);
  }
}
