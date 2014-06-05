package com.cdd.guomob;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.guomob.screen.GuomobInScreenAd;
import com.guomob.screen.OnInScreenAdListener;

public class GuomobCover extends Activity {

  public static void showCoverOnce(Activity act) {
    showCover(act, false);
  }

  public static void showCoverRepeate(Activity act) {
    showCover(act, true);
  }

  public static void showCover(Activity act, boolean repeate) {

    final GuomobInScreenAd m_InScreenAd = new GuomobInScreenAd(act, true);
    m_InScreenAd.LoadInScreenAd(true);
    m_InScreenAd.setOnInScreenAdListener(new OnInScreenAdListener() {
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
        Log.e("GuomobLog", "onLoadAdError:" + arg0);
      }

      // 用户关闭广告
      public void onClose() {
        Log.e("GuomobLog", "onClose");
      }
    });

    m_InScreenAd.ShowInScreenAd();

    if (repeate) {
      final Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          if (m_InScreenAd.IsInScreenAdLoad()) {
            m_InScreenAd.ShowInScreenAd();
          } else {
            m_InScreenAd.LoadInScreenAd(true);
          }
        }
      }, 10 * 60 * 1000);
    }
  }

}
