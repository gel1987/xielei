package com.cdd.ym;

import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsManager;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.cdd.utils.SPUtils;

public class YMWall implements PointsChangeNotify {

  private static Activity activity;
  private static OffersManager offersManager;
  private static PointsManager pointsManager;

  private static int coins = 0;
  private static Handler h = null;

  public static void init(Activity act) {
    activity = act;
    offersManager = OffersManager.getInstance(act);
    offersManager.onAppLaunch();
    pointsManager = PointsManager.getInstance(activity);
    pointsManager.registerNotify(new YMWall());
    pointsManager.setEnableEarnPointsToastTips(false);
    pointsManager.setEnableEarnPointsNotification(false);
    coins = pointsManager.queryPoints();
    int initFlag = SPUtils.getIntValue(activity, "ah");
    if (initFlag == 0 && pointsManager.awardPoints(30)) {
      SPUtils.saveValue(activity, "ah", 1);
    }
    h = new Handler();
  }

  @Override
  public void onPointBalanceChange(int coin) {
    Log.v("dddd", "coin :" + coin);
    coins = coin;
  }

  public static void showOfferWall() {
    offersManager.showOffersWall();
  }

  public static int queryCoin() {
    return pointsManager.queryPoints();
  }

  public static boolean earnCoin(int coin) {
    return pointsManager.awardPoints(coin);
  }

  public static boolean spendCoin(final int coin) {
    if (coins < coin) {
      h.post(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(activity, "积分不足,请先获取积分", Toast.LENGTH_SHORT).show();
          showOfferWall();
        }
      });
      return false;
    } else {
      if (!pointsManager.spendPoints(coin)) {
        h.post(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(activity, "消费积分失败,请重试", Toast.LENGTH_SHORT).show();
          }
        });
        return false;
      }
      return true;
    }
  }

  public static boolean spend5Coins() {
    return spendCoin(5);
  }

  public static boolean spend10Coins() {
    return spendCoin(10);
  }

  public static boolean spend20Coins() {
    return spendCoin(20);
  }

  public static boolean spend30Coins() {
    return spendCoin(30);
  }

  public static boolean spend50Coins() {
    return spendCoin(50);
  }

  public static boolean spend200Coins() {
    return spendCoin(200);
  }

  public static boolean spend100Coins() {
    return spendCoin(100);
  }
}
