package com.tapjoy.easyapp;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cdd.pay.Pay;
import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConnectNotifier;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyFullScreenAdNotifier;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoyOffersNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyViewNotifier;

public class TJWall {

  protected static final String TAG = "TJWall";

  private static Activity act;

  private static int coins = 0;

  private static Handler handler = new Handler();

  public static int position = 1;

  public static boolean isAddBanner = false;

  private static String tapjoyAppID = "56a7ca02-7237-4a18-817d-5d8d2ca4047a";
  private static String tapjoySecretKey = "K52MeFhINWNcUv0bOIM3";

  public static void init(Activity activity) {
    act = activity;

    // Enables logging to the console.
    // TapjoyLog.enableLogging(true);

    // OPTIONAL: For custom startup flags.
    Hashtable<String, String> flags = new Hashtable<String, String>();
    flags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
    // Connect with the Tapjoy server. Call this when the application first
    // starts.
    // REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
    // REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
    tapjoyAppID = MetaDataUtil.getApplicationMetaData(activity, "tjID", tapjoyAppID);
    tapjoySecretKey = MetaDataUtil.getApplicationMetaData(activity, "tjskey", tapjoySecretKey);
    // NOTE: This is the only step required if you're an advertiser.
    TapjoyConnect.requestTapjoyConnect(act, tapjoyAppID, tapjoySecretKey, flags, new TapjoyConnectNotifier() {
      @Override
      public void connectSuccess() {
        Log.e(TAG, " connectSuccess ");
        onConnectSuccess();
      }

      @Override
      public void connectFail() {
        Log.e(TAG, " connectFail ");
      }
    });

  }

  public static void onConnectSuccess() {
    // NOTE: The get/spend/awardTapPoints methods will only work if your virtual
    // currency
    // is managed by Tapjoy.
    //
    // For NON-MANAGED virtual currency,
    // TapjoyConnect.getTapjoyConnectInsance().setUserID(...)
    // must be called after requestTapjoyConnect.
    if (0 == SPUtils.getIntValue(act, "tjinit")) {
      // 赠送5金币
      TapjoyConnect.getTapjoyConnectInstance().awardTapPoints(5, new TapjoyAwardPointsNotifier() {
        @Override
        public void getAwardPointsResponseFailed(String arg0) {
        }

        @Override
        public void getAwardPointsResponse(String arg0, int arg1) {
          SPUtils.saveValue(act, "tjinit", 1);
          coins = arg1;
        }
      });
    }
    // Get notifications whenever Tapjoy currency is earned.
    TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier() {
      @Override
      public void earnedTapPoints(int amount) {
        coins = amount;
      }
    });

    // Get notifications when Tapjoy views open or close.
    TapjoyConnect.getTapjoyConnectInstance().setTapjoyViewNotifier(new TapjoyViewNotifier() {
      @Override
      public void viewWillOpen(int viewType) {
        Log.e(TAG, "viewWillOpen Sucess!");
      }

      @Override
      public void viewWillClose(int viewType) {
        Log.e(TAG, "viewWillClose Sucess!");
      }

      @Override
      public void viewDidOpen(int viewType) {
        Log.e(TAG, "viewDidOpen Sucess!");
      }

      @Override
      public void viewDidClose(int viewType) {
        Log.e(TAG, "viewDidClose Sucess!");
      }
    });

    TapjoyConnect.getTapjoyConnectInstance().getTapPoints(new TapjoyNotifier() {
      @Override
      public void getUpdatePointsFailed(String error) {
      }

      @Override
      public void getUpdatePoints(String currencyName, int pointTotal) {
        Log.i(TAG, "currencyName: " + currencyName);
        Log.i(TAG, "pointTotal: " + pointTotal);
        coins = pointTotal;
      }
    });

    showCover();
    if (isAddBanner) {
      showBanner();
    }
  }

  public static void showWall() {
    showWall(act);
  }

  public static void showWall(Context act) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        // Show the Offers web view from where users can download the latest
        // offers for virtual currency.
        TapjoyConnect.getTapjoyConnectInstance().showOffers(new TapjoyOffersNotifier() {
          @Override
          public void getOffersResponse() {
          }

          @Override
          public void getOffersResponseFailed(String error) {
          }
        });
      }
    });
  }

  public static void showBanner() {
    // Show the display/banner ad.
    TapjoyConnect.getTapjoyConnectInstance().enableDisplayAdAutoRefresh(true);
    TapjoyConnect.getTapjoyConnectInstance().getDisplayAd(act, new TapjoyDisplayAdNotifier() {
      @Override
      public void getDisplayAdResponseFailed(String error) {
        Log.e(TAG, "showBanner Error! " + error);
      }

      @Override
      public void getDisplayAdResponse(View view) {
        Log.e(TAG, "showBanner Sucess!");
        // Using screen width, but substitute for the any width.
        addBanner(act, view);
      }
    });
  }

  /**
   * 
   * @param act
   * @param position
   *          0:底部 1:顶部
   * @param pre
   *          size百分比
   */
  private static void addBanner(final Activity act, View adView) {
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
    adView = scaleDisplayAd(adView, size);
    RelativeLayout adLayout = new RelativeLayout(act);

    LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    adLayout.addView(adView, layoutParams);

    act.addContentView(adLayout, parentLayputParams);
  }

  /**
   * Scales a display ad view to fit within a specified width. Returns a resized
   * (smaller) view if the display ad is larger than the width. This method does
   * not modify the view if the banner is smaller than the width (does not
   * resize larger).
   * 
   * @param adView
   *          Display Ad view to resize.
   * @param targetWidth
   *          Width of the parent view for the display ad.
   * @return Resized display ad view.
   */
  private static View scaleDisplayAd(View adView, int targetWidth) {
    int adWidth = adView.getLayoutParams().width;
    int adHeight = adView.getLayoutParams().height;

    // Scale if the ad view is too big for the parent view.
    if (adWidth > targetWidth) {
      int scale;
      int width = targetWidth;
      Double val = Double.valueOf(width) / Double.valueOf(adWidth);
      val = val * 100d;
      scale = val.intValue();

      ((android.webkit.WebView) (adView)).getSettings().setSupportZoom(true);
      ((android.webkit.WebView) (adView)).setPadding(0, 0, 0, 0);
      ((android.webkit.WebView) (adView)).setVerticalScrollBarEnabled(false);
      ((android.webkit.WebView) (adView)).setHorizontalScrollBarEnabled(false);
      ((android.webkit.WebView) (adView)).setInitialScale(scale);

      // Resize banner to desired width and keep aspect ratio.
      LayoutParams layout = new LayoutParams(targetWidth, (targetWidth * adHeight) / adWidth);
      adView.setLayoutParams(layout);
    }

    return adView;
  }

  public static void showCover() {
    // Show the full screen ad.
    TapjoyConnect.getTapjoyConnectInstance().getFullScreenAd(new TapjoyFullScreenAdNotifier() {
      @Override
      public void getFullScreenAdResponseFailed(int error) {
        Log.e(TAG, "showCover Error! " + error);
      }

      @Override
      public void getFullScreenAdResponse() {
        Log.e(TAG, "showCover Sucess!");
        TapjoyConnect.getTapjoyConnectInstance().showFullScreenAd();
      }
    });
  }

  public static void spendCoin30(String payCode) {
    spendCoin(30, payCode);
  }

  public static void spendCoin20(String payCode) {
    spendCoin(20, payCode);
  }

  public static void spendCoin10(String payCode) {
    spendCoin(10, payCode);
  }

  public static void spendCoin5(String payCode) {
    spendCoin(5, payCode);
  }

  public static void spendCoin30() {
    spendCoin(30, null);
  }

  public static void spendCoin20() {
    spendCoin(20, null);
  }

  public static void spendCoin10() {
    spendCoin(10, null);
  }

  public static void spendCoin5() {
    spendCoin(5, null);
  }

  public static void spendCoin(final int coin, final String payCode) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        if (coin > coins) {
          Toast.makeText(act, "only "+coins+" coins,need " + coin + " coins,please get more coins!", Toast.LENGTH_LONG).show();
          onPayFailed(payCode);
          showWall();
        } else {
          // Spend virtual currency.
          TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(coin, new TapjoySpendPointsNotifier() {
            @Override
            public void getSpendPointsResponseFailed(String error) {
              // 支付失败
              handler.post(new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(act, "error,please retry!", Toast.LENGTH_LONG).show();
                  onPayFailed(payCode);
                }
              });
            }

            @Override
            public void getSpendPointsResponse(String currencyName, final int pointTotal) {
              // 支付成功
              handler.post(new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(act, "Sucess!", Toast.LENGTH_LONG).show();
                  onPaySucess(payCode);
                  coins = pointTotal;
                }
              });
            }
          });
        }
      }
    });
  }

  private static void onPaySucess(String payCode) {
    Pay.payMent.onSucess(payCode);
  }

  private static void onPayFailed(String payCode) {
    Pay.payMent.onFailed(payCode);
  }
}
