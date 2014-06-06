package com.gad;

import android.app.Activity;
import android.os.Handler;

public class GAD {

  public static String UID_BANNER = "a1535711c02b135";
  public static String UID_COVER = "ca-app-pub-8002125689398145/8030638516";

  public static Handler handler = new Handler();

  public static void showbannerT(Activity ctx) {
    ADBanner.addBanner(ctx, 1);
  }

  public static void showbannerB(Activity ctx) {
    ADBanner.addBanner(ctx, 1);
  }

  private static long oldTime = 0;

  public static void showCover(Activity ctx) {
    if ((System.currentTimeMillis() - oldTime) > 1000 * 30) {
      ADCover.init(ctx);
      oldTime = System.currentTimeMillis();
    }
  }
}
