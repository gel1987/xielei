package com.example.applyym;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PJWeb extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = MetaDataUtil.getApplicationMetaData(this, "PJurl");
		WebView wb = new WebView(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		wb.setLayoutParams(params);
		wb.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		wb.setDownloadListener(new MyWebViewDownLoadListener());
		WebSettings localWebSettings = wb.getSettings();
		localWebSettings.setSupportZoom(true);
		localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		localWebSettings.setJavaScriptEnabled(true);
		localWebSettings.setBuiltInZoomControls(true);
		wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wb.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					setProgressBarIndeterminateVisibility(false);
				} else {
					setProgressBarIndeterminateVisibility(true);
				}
			}

		});
		wb.loadUrl(url);
		Toast.makeText(this, "正在加载页面,请稍等....", Toast.LENGTH_LONG).show();
		setContentView(wb);
	}

	class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}

	public static void tipInstallPj(final Context ctx) {
		String result = DataStoreUtils.readLocalInfo(ctx, DataStoreUtils.SP_PJ);
		if (DataStoreUtils.VALUE_TRUE.equals(result)) {
			Intent intent = new Intent(ctx, PJWeb.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(intent);
		} else {
			try {
				String str = MetaDataUtil.getApplicationMetaData(ctx,
						"PJInstall");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				final Date d = sdf.parse(str);
				Callback callback = new Callback() {
					@Override
					public void callback(Object obj) {
						Long date = (Long) obj;
						boolean result = d.before(new Date(date));
						// 不大于当前时间
						if (result) {
							DataStoreUtils.saveLocalInfo(ctx,
									DataStoreUtils.SP_PJ,
									DataStoreUtils.VALUE_TRUE);
						}
					}
				};
				DateUtil.getNetDate(callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
