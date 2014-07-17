package com.cdd.mainthread;

import android.content.Context;
import android.os.Handler;

public class MainThread {
  public static final String TAG = "MainThread";

  public static Handler handler;

  public static void init(Context ctx) {
    handler = new Handler();
  }

  public static void runOnUIThread(Handler uiHandler) {
    uiHandler.post(new Runnable() {
      @Override
      public void run() {
        method1();
      }
    });
  }

  public static void method1() {
  }
}
