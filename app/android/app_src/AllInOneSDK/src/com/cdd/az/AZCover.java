package com.cdd.az;

import com.fivefeiwo.coverscreen.CPManager;

import android.app.Activity;

public class AZCover {

  public static void init(Activity act) {
    CPManager.init(act, AZAD.appKey);
    CPManager.setShowAtScreenOn(true);
  }

  public static void showADDelay(int time) {

  }

  private static long oldTime = 0;

  /**
   * 显示插屏
   * 
   * @param act
   * @param repeat
   */
  public static void showAD(final Activity act, final boolean repeat) {
    if ((System.currentTimeMillis() - oldTime) < 1000 * 30) {
      return;
    }
    oldTime = System.currentTimeMillis();
    AZAD.handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        CPManager.close(act);
        CPManager.showAd(act);
        if (repeat)
          AZAD.handler.postDelayed(this, 1000 * 60 * 20);
      }
    }, 1000 * 5);
  }
}
