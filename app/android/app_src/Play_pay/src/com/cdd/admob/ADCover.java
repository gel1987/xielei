package com.cdd.admob;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ADCover {
  private static InterstitialAd interstitial;

  
  public static void initOld(Context ctx){
  }
  
  @SuppressLint("SimpleDateFormat")
  public static void init(final Context ctx) {
    String cover_d = DataStoreUtils.readLocalInfo(ctx, "cover_d");
    if (TextUtils.isEmpty(cover_d)) {
      try {
        String str = MetaDataUtil.getApplicationMetaData(ctx, "cover_d");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date d = sdf.parse(str);
        Callback callback = new Callback() {
          @Override
          public void callback(Object obj) {
            Long date = (Long) obj;
            boolean result = d.before(new Date(date));
            // 不大于当前时间
            if (result) {
              DataStoreUtils.saveLocalInfo(ctx, "cover_d", DataStoreUtils.VALUE_TRUE);
            }
          }
        };
        DateUtil.getNetDate(callback);
        return;
      } catch (Exception e) {
      }
    }
    // 制作插页式广告。
    interstitial = new InterstitialAd(ctx);
    interstitial.setAdUnitId(GAD.UID_COVER);

    // 创建广告请求。
    AdRequest adRequest = new AdRequest.Builder().build();

    // 开始加载插页式广告。
    interstitial.loadAd(adRequest);

    GAD.handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (!displayInterstitial()) {
          GAD.handler.postDelayed(this, 3000);
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
