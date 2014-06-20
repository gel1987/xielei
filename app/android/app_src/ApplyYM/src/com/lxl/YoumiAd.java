package com.lxl;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;

import com.lxl.br.pcagsc;
import com.lxl.br.pcajsc;
import com.lxl.br.pcajscListener;
import com.lxl.smt.pcbosc;
import com.lxl.st.pcbrsc;

public class YoumiAd {

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
    pcacsc.getInstance(act).init(id, scu, false);
    // 插屏
    pcbrsc.pmavsm(act).pmbgsm();
    pcbrsc.pmavsm(act).pmbtsm(true);// 设置自动关闭插屏开关
    // smartbanner
    pcbosc.pmbdsm(act);
  }

  public static void showBannerAd(Activity act) {
    showBannerAd(act, 0);
  }

  public static void showBannerAdBottom(Activity act) {
    showBannerAd(act, 1);
  }

  public static void showBannerAd(Activity act, int position) {
    // 实例化LayoutParams(重要)
    final RelativeLayout parentLayput = new RelativeLayout(act);
    RelativeLayout.LayoutParams parentLayputParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    if (position == 0) {
      layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
    } else {
      layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
    }
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

    // 实例化广告条
    pcajsc adView = new pcajsc(act, pcagsc.FIT_SCREEN);
    parentLayput.addView(adView, layoutParams);
    // 调用Activity的addContentView函数
    act.addContentView(parentLayput, parentLayputParams);
    // 监听广告条接口
    adView.pmbpsm(new pcajscListener() {
      @Override
      public void pmbhsm(pcajsc arg0) {
      }

      @Override
      public void pmbism(pcajsc arg0) {
      }

      @Override
      public void pmblsm(pcajsc arg0) {
      }
    });
  }

  public static void showSmartBanner(Activity act) {
    // 调用展示飘窗
    showSmartBannerMoreTime(act, true);
  }

  public static void showSmartBannerOnce(Activity act) {
    // 调用展示飘窗
    showSmartBannerMoreTime(act, false);
  }

  private static void showSmartBannerMoreTime(final Activity act, final boolean repeat) {
    // 调用展示飘窗
    final Handler handler = new Handler();
    final Runnable task = new Runnable() {
      @Override
      public void run() {
        pcbosc.pmcgsm(act);
        if (repeat)
          handler.postDelayed(this, 1000 * 60 * 5);
      }
    };

    handler.postDelayed(task, 1000 * 30);
  }

  public static void showScreen(Activity act) {
    showScreen(act, true);
  }

  public static void showScreenOnce(Activity act) {
    showScreen(act, false);
  }

  private static long oldTime = 0;

  private static void showScreen(final Activity act, final boolean repeat) {
    if ((System.currentTimeMillis() - oldTime) < 1000 * 30) {
      return;
    }
    oldTime = System.currentTimeMillis();
    final String mateYoumiCover = "ym_cover_d";
    String cover_d = DataStoreUtils.readLocalInfo(act, mateYoumiCover);
    if (TextUtils.isEmpty(cover_d)) {
      try {
        String str = MetaDataUtil.getApplicationMetaData(act, mateYoumiCover);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date d = sdf.parse(str);
        Callback callback = new Callback() {
          @Override
          public void callback(Object obj) {
            Long date = (Long) obj;
            boolean result = d.before(new Date(date));
            // 不大于当前时间
            if (result) {
              DataStoreUtils.saveLocalInfo(act, mateYoumiCover, DataStoreUtils.VALUE_TRUE);
            }
          }
        };
        DateUtil.getNetDate(callback);
        return;
      } catch (Exception e) {
      }
    }

    final Handler h = new Handler();
    Runnable r = new Runnable() {
      @Override
      public void run() {
        pcbrsc.pmavsm(act).pmcgsmSpotAds(act);
        if (repeat)
          h.postDelayed(this, 1000 * 60 * 20);
      }
    };
    h.postDelayed(r, 1000 * 3);
  }
}
