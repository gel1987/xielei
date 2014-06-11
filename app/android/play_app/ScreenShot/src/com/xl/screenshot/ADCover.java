package com.xl.screenshot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ADCover {
  private static InterstitialAd interstitial;
  private static Handler handler = new Handler();


  @SuppressLint("SimpleDateFormat")
  public static void init(final Context ctx) {
    // 制作插页式广告。
    interstitial = new InterstitialAd(ctx);
    interstitial.setAdUnitId("ca-app-pub-8002125689398145/8030638516");

    // 创建广告请求。
    AdRequest adRequest = new AdRequest.Builder().build();

    // 开始加载插页式广告。
    interstitial.loadAd(adRequest);

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (!displayInterstitial()) {
          handler.postDelayed(this, 3000);
        }
      }
    }, 5000);
  }

  // 在您准备好展示插页式广告时调用displayInterstitial()。
  public static boolean displayInterstitial() {
    if (interstitial.isLoaded()) {
      interstitial.show();
      return true;
    }
    return false;
  }
}
