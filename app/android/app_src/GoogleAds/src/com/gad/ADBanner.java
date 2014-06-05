package com.gad;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ADBanner {

	/** 广告Layout */
	private static RelativeLayout adLayout;

	public static void addBanner(final Activity act, int position) {
		adLayout = new RelativeLayout(act);
		RelativeLayout.LayoutParams parentLayputParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		if (position == 1) {
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
		} else {
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
					RelativeLayout.TRUE);
		}
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.TRUE);
		AdView adView = new AdView(act);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(GAD.UID_BANNER);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);

		setVisible();
		adLayout.addView(adView, layoutParams);
		act.addContentView(adLayout, parentLayputParams);
	}

	/**
	 * 设置广告条显示/隐藏
	 * 
	 * @param flag
	 */
	public static void setVisible() {
		GAD.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (adLayout != null) {
					if (adLayout.getVisibility() == View.GONE) {
						adLayout.setVisibility(View.VISIBLE);
					} else {
						adLayout.setVisibility(View.GONE);
					}
					GAD.handler.postDelayed(this, 60 * 1000);
				}
			}
		}, 60 * 1000);
	}
}
