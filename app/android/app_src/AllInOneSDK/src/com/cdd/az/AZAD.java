package com.cdd.az;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.cdd.utils.MetaDataUtil;

public class AZAD {

  public static String appKey = "DXaaJcgEHx6u6S960AikfQGq";

  public static Handler handler = new Handler();

  public static Handler bannerHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {

    }
  };

  public static Activity activity;

  public static void init(Activity act) {
    activity = act;
    String key = MetaDataUtil.getApplicationMetaData(act, "anzhikey");
    if (key != null) {
      appKey = key;
    }
    initCover(act);
  }

  public static void initCoverWithKey(Context act) {
    String key = MetaDataUtil.getApplicationMetaData(act, "anzhikey");
    if (key != null) {
      appKey = key;
    }
    initCover(act);
  }

  public static void addADBanner() {
    addADAfterDay(0, 0, 1f, false);
  }

  public static void addADCover() {
    showCoverAfterDay(0, true);
  }

  public static void addADCoverNoRe() {
    showCoverAfterDay(0, false);
  }

  public static void addADCoverAfter1H() {
    showCoverAfterDay(3600 * 1000, true);
  }

  public static void addADCoverNoReAfter1H() {
    showCoverAfterDay(3600 * 1000, false);
  }

  public static void addADDelayed() {
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        addADAfterDay(0, 1, 1f, true);
      }
    }, 1000 * 60);
  }

  /**
   * 立刻添加广告banner
   */
  public static void addAD() {
    addADAfterDay(0, 1, 1f, true);
  }

  /**
   * 3天后添加广告banner
   */
  public static void addAD3Day() {
    addADAfterDay(3, 1, 1f, true);
  }

  /**
   * 5天后添加广告banner
   */
  public static void addAD5Day() {
    addADAfterDay(5, 1, 1f, true);
  }

  /**
   * 立刻添加广告banner
   */
  public static void addADBottom() {
    addADAfterDay(0, 0, 1f, true);
  }

  /**
   * 3天后添加广告banner
   */
  public static void addAD3DayBottom() {
    addADAfterDay(3, 0, 1f, true);
  }

  /**
   * 5天后添加广告banner
   */
  public static void addAD5DayBottom() {
    addADAfterDay(5, 0, 1f, true);
  }

  /**
   * 添加百分比size 广告
   * 
   * @param day
   * @param position
   */
  public static void addADPre(int day, int position) {
    addADAfterDay(day, position, 0.66f, true);
  }

  /**
   * 延迟显示广告
   * 
   * @param day
   *          广告延迟天数
   * @param bannerPosition
   *          广告位置
   * @param bannerSizePre
   *          广告大小 屏幕百分比
   */
  public static void addADAfterDay(int day, int bannerPosition, float bannerSizePre, boolean withCover) {
    SharedPreferences sp = activity.getSharedPreferences("data", 0);
    long cur = System.currentTimeMillis();
    long date = sp.getLong("f", cur);
    if (cur - date > 1000 * 60 * 60 * 24 * day) {
      AZbanner.addBanner(activity, bannerPosition, bannerSizePre);
      if (withCover)
        AZCover.showAD(activity, true);
    }
    if (cur == date) {
      sp.edit().putLong("f", cur).commit();
    }
  }

  /**
   * 延迟显示插屏
   * 
   * @param day
   * @param repeat
   */
  public static void showCoverAfterDay(long time, boolean repeat) {
    SharedPreferences sp = activity.getSharedPreferences("data", 0);
    long cur = System.currentTimeMillis();
    long date = sp.getLong("h", cur);
    if (cur - date > time) {
      AZCover.showAD(activity, repeat);
    }
    if (cur == date) {
      sp.edit().putLong("h", cur).commit();
    }
  }

  /**
   * 初始化插屏
   * 
   * @param act
   */
  public static void initCover(Context act) {
    // 应用启动时，初始化广告组件，
    AZCover.init(act);
  }

}
