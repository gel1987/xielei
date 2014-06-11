package com.xl.screenshot;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;

public class MainApplication extends Application {

  public static MainApplication app = null;

  public static Handler handler = new Handler();

  @Override
  public void onCreate() {
    super.onCreate();
    app = this;
    startService(new Intent(this, ScreenshotService.class));
  }

}
