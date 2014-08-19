package com.cdd.az;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.feiwo.view.FwBannerManager;

public class AZbanner {
  /** 广告Layout */
  private static RelativeLayout adLayout;

  /**
   * 顶部添加Banner
   * 
   * @param act
   */
  public static void addBanner(Activity act) {
    addBanner(act, 1);
  }

  /**
   * 底部添加Banner
   * 
   * @param act
   */
  public static void addBannerBottom(Activity act) {
    addBanner(act, 0);
  }

  /**
   * 顶部添加Banner size 66%
   * 
   * @param act
   */
  public static void addBannerPre60(Activity act) {
    addBannerPre60(act, 1);
  }

  /**
   * 底部添加Banner size 66%
   * 
   * @param act
   */
  public static void addBannerBottomPre60(Activity act) {
    addBannerPre60(act, 0);
  }

  /**
   * 添加Banner
   * 
   * @param act
   * @param position
   *          0:底部 1:顶部
   */
  public static void addBanner(final Activity act, int position) {
    addBanner(act, position, 1f);
  }

  /**
   * 添加Banner size 66%
   * 
   * @param act
   * @param position
   *          0:底部 1:顶部
   */
  public static void addBannerPre60(final Activity act, int position) {
    addBanner(act, position, 0.6f);
  }

  /**
   * 设置广告条显示/隐藏
   * 
   * @param flag
   */
  public static void setVisible() {
    AZAD.bannerHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (adLayout != null) {
          if (adLayout.getVisibility() == View.GONE) {
            adLayout.setVisibility(View.VISIBLE);
          } else {
            adLayout.setVisibility(View.GONE);
          }
          AZAD.bannerHandler.postDelayed(this, 30 * 1000);
        }
      }
    }, 30 * 1000);
  }

  /**
   * 
   * @param act
   * @param position
   *          0:底部 1:顶部
   * @param pre
   *          size百分比
   */
  public static void addBanner(final Activity act, int position, float pre) {
    RelativeLayout.LayoutParams parentLayputParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    if (position == 1) {
      parentLayputParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
    } else {
      parentLayputParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
    }
    parentLayputParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

    WindowManager manage = act.getWindowManager();
    Display display = manage.getDefaultDisplay();

    int size = Math.min(display.getWidth(), display.getHeight());
    adLayout = getBannerView(act, (int) (size * pre));
    act.addContentView(adLayout, parentLayputParams);
//    setVisible();
    
  }

  /**
   * 创建 Banner
   * 
   * @param ctx
   * @param width
   * @return
   */
  public static RelativeLayout getBannerView(Context ctx, int width) {
    FwBannerManager.init(ctx,AZAD.appKey);
    RelativeLayout adLayout = new RelativeLayout(ctx);
    RelativeLayout.LayoutParams parentLayputParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    parentLayputParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
    RelativeLayout adLayout1 = new RelativeLayout(ctx);
    adLayout.addView(adLayout1, parentLayputParams);
    FwBannerManager.setParentView(adLayout1);
    return adLayout;
  }
}
