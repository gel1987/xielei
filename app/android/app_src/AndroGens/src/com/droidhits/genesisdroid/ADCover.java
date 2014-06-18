package com.droidhits.genesisdroid;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mdplay.androgens.R;

public class ADCover {
  private static InterstitialAd interstitial;

  public static void init(Context ctx) {
    // 制作插页式广告。
    interstitial = new InterstitialAd(ctx);
    interstitial.setAdUnitId(ctx.getResources().getString(R.string.admob_cover_id));

    // 创建广告请求。
    AdRequest adRequest = new AdRequest.Builder().build();

    // 开始加载插页式广告。
    interstitial.loadAd(adRequest);
    final Handler handler = new Handler();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (!displayInterstitial()) {
          handler.postDelayed(this, 2000);
        }
      }
    }, 500);
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
