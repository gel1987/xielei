package com.tapjoy.easyapp;

import java.util.Hashtable;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConnectNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoyOffersNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyViewNotifier;

public class TJWall {

  protected static final String TAG = "TJWall";

  private static Context ctx;

  private static int coins = 0;

  private static Handler handler = new Handler();

  public static void init(Context act) {
    ctx = act;

    // Enables logging to the console.
    // TapjoyLog.enableLogging(true);

    // OPTIONAL: For custom startup flags.
    Hashtable<String, String> flags = new Hashtable<String, String>();
    flags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
    // Connect with the Tapjoy server. Call this when the application first
    // starts.
    // REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
    // REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
    String tapjoyAppID = "56a7ca02-7237-4a18-817d-5d8d2ca4047a";
    String tapjoySecretKey = "K52MeFhINWNcUv0bOIM3";
    // NOTE: This is the only step required if you're an advertiser.
    TapjoyConnect.requestTapjoyConnect(ctx, tapjoyAppID, tapjoySecretKey, flags, new TapjoyConnectNotifier() {
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
    if (0 == SPUtils.getIntValue(ctx, "tjinit")) {
      // 赠送5金币
      TapjoyConnect.getTapjoyConnectInstance().awardTapPoints(5, new TapjoyAwardPointsNotifier() {
        @Override
        public void getAwardPointsResponseFailed(String arg0) {
        }

        @Override
        public void getAwardPointsResponse(String arg0, int arg1) {
          SPUtils.saveValue(ctx, "tjinit", 1);
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
      }

      @Override
      public void viewWillClose(int viewType) {
      }

      @Override
      public void viewDidOpen(int viewType) {
      }

      @Override
      public void viewDidClose(int viewType) {
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
  }

  public static void showWall() {
    showWall(ctx);
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
          Toast.makeText(ctx, "coin is not enough,you need " + coin + " coins", Toast.LENGTH_LONG).show();
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
                  Toast.makeText(ctx, "error,please retry!", Toast.LENGTH_LONG).show();
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
                  Toast.makeText(ctx, "Sucess!", Toast.LENGTH_LONG).show();
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
}
