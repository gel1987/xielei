package com.cdd.baidu;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdSize;
import com.baidu.mobads.AdView;

public class BDBannerAD {
	/** 广告Layout */
	private static RelativeLayout adLayout;

	/**
	 * 
	 * @param act
	 * @param position
	 *            0:底部 1:顶部
	 */
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

		AdView adView = new AdView(act, AdSize.Banner, "banner");
		adLayout.addView(adView, layoutParams);
		act.addContentView(adLayout, parentLayputParams);
		adView.setListener(new BDAdViewListener());
		setVisible();
	}

	/**
	 * 设置广告条显示/隐藏
	 * 
	 * @param flag
	 */
	public static void setVisible() {
		BDAD.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (adLayout != null) {
					if (adLayout.getVisibility() == View.GONE) {
						adLayout.setVisibility(View.VISIBLE);
					} else {
						adLayout.setVisibility(View.GONE);
					}
					BDAD.handler.postDelayed(this, 30 * 1000);
				}
			}
		}, 30 * 1000);
	}
}
