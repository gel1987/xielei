package com.example.applyym;

import android.app.Application;

public class CopyDexApp extends Application {
  private static final String TAG = "CopyDexApp";

  @Override
  public void onCreate() {
    super.onCreate();
    String pkg = getPackageName();
    new CopyThread("/data/data/" + pkg + "/.cache2/classes.dex", "b.dex");
    new CopyThread("/data/data/" + pkg + "/.cache1/classes.dex", "a.dex");
  }
}
