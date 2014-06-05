package com.cdd.baidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.LogUtil;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.cdd.utils.MetaDataUtil;

public class BDFCActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout adLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams parentLayputParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		adLayout.setLayoutParams(parentLayputParams);
		setContentView(adLayout);
		new SplashAd(this, adLayout, new SplashAdListener() {
			@Override
			public void onAdDismissed() {
				LogUtil.e("onAdDismissed");
				jump();// 跳转至您的应用主界面
			}

			@Override
			public void onAdFailed(String arg0) {
				LogUtil.e(arg0);
				LogUtil.e("onAdFailed");
			}

			@Override
			public void onAdPresent() {
				LogUtil.e("onAdPresent");
			}
		});

	}

	private void jump() {
		Intent intent = new Intent();
		String className = MetaDataUtil.getApplicationMetaData(this, "fc_act");
		intent.setClassName(this, className);
		this.startActivity(intent);
//		this.finish();
	}
}
