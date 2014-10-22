package com.tapjoy.easyapp;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tapjoy.TJError;
import com.tapjoy.TJEvent;
import com.tapjoy.TJEventCallback;
import com.tapjoy.TJEventPreloadStatus;
import com.tapjoy.TJEventRequest;
import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConnectNotifier;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoyOffersNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyVideoNotifier;
import com.tapjoy.TapjoyViewNotifier;

public class TJWall implements TapjoyNotifier, TJEventCallback {

  protected static final String TAG = "TJWall";

  private static Activity act;

  private static int coins = 0;

  private static Handler handler = new Handler();

  public static int position = 1;

  public static boolean isAddBanner = false;

  private static String tapjoyAppID = "56a7ca02-7237-4a18-817d-5d8d2ca4047a";
  private static String tapjoySecretKey = "K52MeFhINWNcUv0bOIM3";

  private static TJEvent directPlayEvent;
  private static boolean dpEventContentIsAvailable;
  private static TJWall instance = null;

  public static void initInstace(final Activity activity) {
    instance = new TJWall();
    new Thread() {
      @Override
      public void run() {
        try {
          Thread.sleep(15000);
        } catch (Exception e) {
        }
        instance.init(activity);
      }
    }.start();
  }

  private TJWall() {
  }

  public void init(Activity activity) {
    act = activity;

    // Enables logging to the console.
    // TapjoyLog.enableLogging(true);

    // OPTIONAL: For custom startup flags.
    Hashtable<String, String> connectFlags = new Hashtable<String, String>();
    connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
    // Connect with the Tapjoy server. Call this when the application first
    // starts.
    // REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
    // REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
    tapjoyAppID = MetaDataUtil.getApplicationMetaData(activity, "tjID", tapjoyAppID);
    tapjoySecretKey = MetaDataUtil.getApplicationMetaData(activity, "tjskey", tapjoySecretKey);
    // NOTE: This is the only step required if you're an advertiser.

    TapjoyConnect.requestTapjoyConnect(act.getApplicationContext(), tapjoyAppID, tapjoySecretKey, connectFlags,
        new TapjoyConnectNotifier() {
          @Override
          public void connectSuccess() {
            onConnectSuccess();
          }

          @Override
          public void connectFail() {
            Log.e(TAG, "Tapjoy connect call failed.");
          }
        });

  }

  public void onConnectSuccess() {
    // NOTE: The get/spend/awardTapPoints methods will only work if your virtual
    // currency
    // is managed by Tapjoy.
    //
    // For NON-MANAGED virtual currency,
    // TapjoyConnect.getTapjoyConnectInsance().setUserID(...)
    // must be called after requestTapjoyConnect.

    // Start preloading your TJEvent content as soon as possible
    directPlayEvent = new TJEvent(act, "video_unit", this);
    directPlayEvent.enablePreload(true);
    directPlayEvent.send();

    // Get notifications when Tapjoy views open or close.
    TapjoyConnect.getTapjoyConnectInstance().setTapjoyViewNotifier(new TapjoyViewNotifier() {
      @Override
      public void viewWillOpen(int viewType) {
        Log.i(TAG, "viewWillOpen  did open");
      }

      @Override
      public void viewWillClose(int viewType) {
        Log.i(TAG, "viewWillClose  did open");
      }

      @Override
      public void viewDidOpen(int viewType) {
        Log.i(TAG, "viewDidOpen  did open");
      }

      @Override
      public void viewDidClose(int viewType) {
        Log.i(TAG, "viewDidClose  did open");

        // Best Practice: We recommend calling getTapPoints as often as possible
        // so the user�s balance is always up-to-date.
        TapjoyConnect.getTapjoyConnectInstance().getTapPoints(TJWall.this);
      }
    });

    // Get notifications on video start, complete and error
    TapjoyConnect.getTapjoyConnectInstance().setVideoNotifier(new TapjoyVideoNotifier() {

      @Override
      public void videoStart() {
        Log.i(TAG, "video has started");
      }

      @Override
      public void videoError(int statusCode) {
        Log.i(TAG, "there was an error with the video: " + statusCode);
      }

      @Override
      public void videoComplete() {
        Log.i(TAG, "video has completed");

        // Best Practice: We recommend calling getTapPoints as often as possible
        // so the user�s balance is always up-to-date.
        TapjoyConnect.getTapjoyConnectInstance().getTapPoints(TJWall.this);
      }

    });

    // NOTE: The get/spend/awardTapPoints methods will only work if your virtual
    // currency
    // is managed by Tapjoy.
    //
    // For NON-MANAGED virtual currency,
    // TapjoyConnect.getTapjoyConnectInsance().setUserID(...)
    // must be called after requestTapjoyConnect.
    if (0 == SPUtils.getIntValue(act, "tjinit")) {
      // 赠送5金币
      TapjoyConnect.getTapjoyConnectInstance().awardTapPoints(15, new TapjoyAwardPointsNotifier() {
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
        try {
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
        } catch (Exception e) {
        }
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
    // Check if content is available and if it is ready to show
    if (dpEventContentIsAvailable) {
      if (directPlayEvent.isContentReady()) {
        directPlayEvent.showContent();
      } else {
        // not ready
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            showCover();
          }
        }, 3000);
      }
    } else {
      // dp not available
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          if (count > 3) {
            return;
          }
          count++;
          directPlayEvent = new TJEvent(act, "video_unit", instance);
          directPlayEvent.enablePreload(true);
          directPlayEvent.send();
        }
      }, 10000);
    }
  }

  public static int count = 0;

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
          Toast.makeText(act, "only " + coins + " coins,need " + coin + " coins,please get more!", Toast.LENGTH_LONG)
              .show();
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
  }

  private static void onPayFailed(String payCode) {
  }

  // TJEventCallback Methods

  @Override
  public void sendEventCompleted(TJEvent event, boolean contentAvailable) {
    // If content is not available you can note it here and act accordingly as
    // best suited for your app
    dpEventContentIsAvailable = contentAvailable;
    showCover();
    Log.i(TAG, "Tapjoy send event 'video_unit' completed, contentAvailable: " + contentAvailable);
  }

  @Override
  public void sendEventFail(TJEvent event, TJError error) {
    Log.i(TAG, "Tapjoy send event 'video_unit' failed with error: " + error.message);
  }

  @Override
  public void contentIsReady(TJEvent event, int status) {
    Log.i(TAG, "Tapjoy direct play content is ready");
    switch (status) {
      case TJEventPreloadStatus.STATUS_PRELOAD_COMPLETE:
        // handle partial load of cache
        break;
      case TJEventPreloadStatus.STATUS_PRELOAD_PARTIAL:
        // Handle complete load of cache
        break;
      default:
        // Should never get here
    }
  }

  @Override
  public void contentDidShow(TJEvent event) {
    Log.i(TAG, "Tapjoy direct play content did show");
  }

  @Override
  public void contentDidDisappear(TJEvent event) {
    Log.i(TAG, "Tapjoy direct play content did disappear");

    // Best Practice: We recommend calling getTapPoints as often as possible so
    // the user's balance is always up-to-date.
    TapjoyConnect.getTapjoyConnectInstance().getTapPoints(this);

    // Begin preloading the next event after the previous one is dismissed
    directPlayEvent = new TJEvent(act, "video_unit", this);
    directPlayEvent.enablePreload(true);
    directPlayEvent.send();
  }

  @Override
  public void didRequestAction(TJEvent event, TJEventRequest request) {
  }

  @Override
  public void getUpdatePointsFailed(String error) {
    Log.e(TAG, "getUpdatePointsFailed :" + error);
  }

  @Override
  public void getUpdatePoints(String currencyName, int pointTotal) {
    coins = pointTotal;
  }
}
