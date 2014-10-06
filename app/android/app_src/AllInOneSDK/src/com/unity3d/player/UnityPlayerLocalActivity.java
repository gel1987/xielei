package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

public class UnityPlayerLocalActivity extends Activity {

  private xb sign;

  @Override
  public Context getBaseContext() {
    return super.getBaseContext();
  }

  @Override
  public PackageManager getPackageManager() {
    if(sign == null){
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
}
