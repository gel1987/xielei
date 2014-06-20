package com.cdd.baidu;

import android.app.Activity;
import android.content.Context;

import com.baidu.mobads.InterstitialAd;

public class BDCover {
	public static InterstitialAd cover = null;

	public static void initCover(Context ctx) {
		cover = new InterstitialAd(ctx);
		cover.setListener(new BDCoverListener());
		cover.loadAd();
	}

	 private static long oldTime = 0;

	  
	/**
	 * 显示插屏
	 * 
	 * @param act
	 * @param repeat
	 */
	public static void showAD(final Activity act, final boolean repeat) {
	  if ((System.currentTimeMillis() - oldTime) < 1000 * 30) {
      return;
    }
    oldTime = System.currentTimeMillis();
		BDAD.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 3; i++) {
					if (cover.isAdReady()) {
						cover.showAd(act);
					} else {
						cover.loadAd();
					}

				}
				if (repeat)
					BDAD.handler.postDelayed(this, 1000 * 60 * 50);
			}
		}, 1000 * 60 * 5);
	}
}
