package com.cdd.guomob;

import android.app.Activity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.guomob.banner.GuomobAdView;
import com.guomob.banner.OnBannerAdListener;

public class GuomobBanner {
  public static void addBanner(Activity act) {
    GuomobAdView m_adView = new GuomobAdView(act);

    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT);
    lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
    m_adView.setLayoutParams(lp2);

    RelativeLayout m_Relative = new RelativeLayout(act);
    m_Relative.addView(m_adView);

    m_adView.setOnBannerAdListener(new OnBannerAdListener() {

      // 无网络连接
      public void onNetWorkError() {
        Log.e("GuomobLog", "onNetWorkError");
      }

      // 加载广告成功
      public void onLoadAdOk() {
        Log.e("GuomobLog", "onLoadAdOk");
      }

      // 加载广告失败 arg0：失败原因
      public void onLoadAdError(String arg0) {
        Log.e("GuomobLog", "onLoadAdError" + arg0);
      }
    });
    RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT);
    lp3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
    act.addContentView(m_Relative, lp3);
  }
}
