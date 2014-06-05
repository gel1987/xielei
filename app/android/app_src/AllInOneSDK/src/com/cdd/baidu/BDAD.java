package com.cdd.baidu;

import android.app.Activity;
import android.os.Handler;

public class BDAD {

  public static Handler handler = new Handler();

  public static void init(Activity ctx) {
    BDBannerAD.addBanner(ctx, 1);
    BDCover.initCover(ctx);
    BDCover.showAD(ctx, true);
  }

  public static void runUiThread(Runnable task) {
    BDAD.handler.post(task);
  }
}
