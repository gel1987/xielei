package com.cdd.ym;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.smart.SmartBannerManager;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cdd.utils.MetaDataUtil;

public class YMAd {

  private static String id;
  private static String scu;

  public static void init(Activity act) {
    String key = MetaDataUtil.getApplicationMetaData(act, "youmikey");
    if (key == null) {
      Log.e("ERROR", "get youmi id error");
      throw new RuntimeException();
    }
    id = key;
    String sct = MetaDataUtil.getApplicationMetaData(act, "youmiscu");
    if (sct == null) {
      Log.e("ERROR", "get youmi scu error");
      throw new RuntimeException();
    }
    scu = sct;
    AdManager.getInstance(act).init(id, scu, false);
  }

  public static void initWithOffer(Activity act) {
    init(act);
    YMWall.init(act);
  }

  public static void showBannerAd(Activity act) {
    showBannerAd(act, 0);
  }

  public static void showBannerAdBottom(Activity act) {
    showBannerAd(act, 1);
  }

  public static void showBannerAd(Activity act, int position) {
    RelativeLayout.LayoutParams parentLayputParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    View parentLayput = getBannerView(act, position);
    // 调用Activity的addContentView函数
    act.addContentView(parentLayput, parentLayputParams);
  }

  /**
   * Banner
   * 
   * @param ctx
   * @param position
   * @return
   */
  public static View getBannerView(Context ctx, int position) {
    // 实例化LayoutParams(重要)
    final RelativeLayout parentLayput = new RelativeLayout(ctx);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    if (position == 0) {
      layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
    } else {
      layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
    }
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

    // 实例化广告条
    AdView adView = new AdView(ctx, AdSize.FIT_SCREEN);
    parentLayput.addView(adView, layoutParams);

    // 监听广告条接口
    adView.setAdListener(new AdViewListener() {

      @Override
      public void onSwitchedAd(AdView arg0) {
        Log.i("YoumiSample", "广告条切换");
      }

      @Override
      public void onReceivedAd(AdView arg0) {
        Log.i("YoumiSample", "请求广告成功");

      }

      @Override
      public void onFailedToReceivedAd(AdView arg0) {
        Log.i("YoumiSample", "请求广告失败");
      }
    });
    return parentLayput;
  }

  public static void showSmartBannerAd(Activity act) {
    // SmartBanner初始化接口,在应用开启的时候调用一次即可
    SmartBannerManager.init(act);
    // 调用展示飘窗
    SmartBannerManager.show(act);
  }

  public static void showSmartBannerAdMoreTime(final Activity act) {
    // SmartBanner初始化接口,在应用开启的时候调用一次即可
    SmartBannerManager.init(act);

    // 调用展示飘窗
    final Handler handler = new Handler();
    final Runnable task = new Runnable() {
      @Override
      public void run() {
        SmartBannerManager.show(act);
        handler.postDelayed(this, 1000 * 60 * 3);
      }
    };

    handler.postDelayed(task, 1000 * 10);
  }

  public static void showOfferWall() {
    YMWall.showOfferWall();
  }

  private static SpotManager spot = null;

  public static void showScreen(final Activity act) {
    if (spot == null) {
      spot = SpotManager.getInstance(act);
      spot.setSpotTimeout(30000); // 5秒
      spot.setAutoCloseSpot(true); // 设置关闭插屏时间
      spot.setCloseTime(5000); // 5秒
      spot.loadSpotAds();
    }

    final Handler h = new Handler();
    Runnable r = new Runnable() {
      @Override
      public void run() {
        spot.showSpotAds(act);
        h.postDelayed(this, 1000 * 60 * 45);
      }
    };
    h.postDelayed(r, 1000 * 3);
  }
}
