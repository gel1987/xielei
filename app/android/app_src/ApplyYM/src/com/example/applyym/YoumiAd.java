package com.example.applyym;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.smart.SmartBannerManager;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

public class YoumiAd {

	private static String id = "9390f2f27f9fe6e6";
	private static String scu = "a33829a2cf05d8f6";

	public static void init(Activity act) {
		AdManager.getInstance(act).init(id, scu, false);
	}

	public static void initWithOffer(Activity act) {
		init(act);
		OfforWallAD.init(act);
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
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		if (position == 0) {
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
		} else {
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
					RelativeLayout.TRUE);
		}
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.TRUE);

		// 实例化广告条
		AdView adView = new AdView(act, AdSize.FIT_SCREEN);
		parentLayput.addView(adView, layoutParams);
		// 调用Activity的addContentView函数
		act.addContentView(parentLayput, parentLayputParams);

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
				handler.postDelayed(this, 1000 * 60 * 5);
			}
		};

		handler.postDelayed(task, 1000 * 30);
	}

	public static void showOfferWall() {
		OfforWallAD.showOfferWall();
	}

	private static SpotManager spot = null;

	public static void showScreen(final Activity act) {
		if (spot == null) {
			spot = SpotManager.getInstance(act);
			spot.loadSpotAds();
		}

		final Handler h = new Handler();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				spot.showSpotAds(act);
				h.postDelayed(this, 1000 * 60 * 20);
			}
		};
		h.postDelayed(r, 1000 * 3);
	}
}
