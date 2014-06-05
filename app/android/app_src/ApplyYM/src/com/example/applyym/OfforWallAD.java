package com.example.applyym;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsManager;

public class OfforWallAD implements PointsChangeNotify {

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
		pointsManager.registerNotify(new OfforWallAD());
		pointsManager.setEnableEarnPointsToastTips(false);
		pointsManager.setEnableEarnPointsNotification(false);
		coins = pointsManager.queryPoints();
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

	public static boolean spendCoin(int coin) {
		if (coins < coin) {
			h.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(activity, "积分不足,请先获取积分.", Toast.LENGTH_SHORT)
							.show();
					showOfferWall();
				}
			});
			return false;
		} else {
			if (!pointsManager.spendPoints(coin)) {
				h.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(activity, "消费积分失败,请重试",
								Toast.LENGTH_SHORT).show();
					}
				});
				return false;
			}
			return true;
		}
	}

	public static boolean spend50Coins() {
		return spendCoin(50);
	}
}
