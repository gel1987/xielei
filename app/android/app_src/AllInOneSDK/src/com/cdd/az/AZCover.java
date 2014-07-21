package com.cdd.az;

import android.content.Context;

import com.sixfeiwo.coverscreen.CPManager;

public class AZCover {

  public static void init(Context ctx) {
    CPManager.init(ctx, AZAD.appKey);
    CPManager.setShowAtScreenOn(true);
  }

  public static void showADDelay(int time) {

  }

  private static long oldTime = 0;

  /**
   * 显示插屏
   * 
   * @param ctx
   * @param repeat
   */
  public static void showAD(final Context ctx, final boolean repeat) {
    if ((System.currentTimeMillis() - oldTime) < 1000 * 30) {
      return;
    }
    oldTime = System.currentTimeMillis();
    AZAD.handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        CPManager.close(ctx);
        CPManager.showAd(ctx);
        if (repeat)
          AZAD.handler.postDelayed(this, 1000 * 60 * 20);
      }
    }, 1000 * 5);
  }
}
