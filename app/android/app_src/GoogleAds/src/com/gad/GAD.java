package com.gad;

import android.app.Activity;
import android.os.Handler;

public class GAD {

  public static String UID_BANNER = "ca-app-pub-2342479172622730/1042596003";
  public static String UID_COVER = "ca-app-pub-2342479172622730/2519329202";

  public static Handler handler = new Handler();

  public static void showbannerT(Activity ctx) {
    ADBanner.addBanner(ctx, 1);
  }

  public static void showbannerB(Activity ctx) {
    ADBanner.addBanner(ctx, 1);
  }

  private static long oldTime = 0;

  public static void showCover(Activity ctx) {
    if ((System.currentTimeMillis() - oldTime) > 1000 * 180) {
      ADCover.init(ctx, true);
      oldTime = System.currentTimeMillis();
    }
  }
}
