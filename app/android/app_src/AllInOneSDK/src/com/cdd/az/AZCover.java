package com.cdd.az;

import android.content.Context;
import android.content.SharedPreferences;

import com.feiwo.view.FwInterstitialManager;

public class AZCover {

  public static void init(Context ctx) {
    FwInterstitialManager.init(ctx, AZAD.appKey);
  }

  public static void showADAfterDays(final Context ctx, int days) {
    showADDelay(ctx, days * 3600 * 24);
  }

  public static void showADDelay(final Context ctx, int time) {
    SharedPreferences sp = ctx.getSharedPreferences("data", 0);
    long cur = System.currentTimeMillis();
    long date = sp.getLong("h", cur);
    if (cur - date > time) {
      AZCover.showAD(ctx, true);
    }
    if (cur == date) {
      sp.edit().putLong("h", cur).commit();
    }
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
        FwInterstitialManager.showInterstitial();
        if (repeat)
          AZAD.handler.postDelayed(this, 1000 * 60 * 20);
      }
    }, 1000 * 5);
  }
}
