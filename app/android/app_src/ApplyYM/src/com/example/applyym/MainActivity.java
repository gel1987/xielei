package com.example.applyym;

import com.lxl.YoumiAd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

  public static final String html = "http://yunpan.cn/QI3Yx83nRgnLz";
  WebView wb;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    wb = new WebView(this);
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
    wb.loadUrl(html);
    Toast.makeText(this, "正在加载页面,请稍等....", Toast.LENGTH_LONG).show();
    setContentView(wb);
    YoumiAd.init(this);
    YoumiAd.showBannerAd(this);
    YoumiAd.showSmartBanner(this);
    YoumiAd.showScreen(this);
    Log.e("ddd", getDeviceInfo(this));
  }

  public static String getDeviceInfo(Context context) {
    try {
      org.json.JSONObject json = new org.json.JSONObject();
      android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
          .getSystemService(Context.TELEPHONY_SERVICE);

      String device_id = tm.getDeviceId();

      android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

      String mac = wifi.getConnectionInfo().getMacAddress();
      json.put("mac", mac);

      if (TextUtils.isEmpty(device_id)) {
        device_id = mac;
      }

      if (TextUtils.isEmpty(device_id)) {
        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
            android.provider.Settings.Secure.ANDROID_ID);
      }

      json.put("device_id", device_id);

      return json.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  class MyWebViewDownLoadListener implements DownloadListener {

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
        long contentLength) {
      Uri uri = Uri.parse(url);
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      startActivity(intent);
    }

  }

  private long exitTime = 0;

  @Override
  public void onBackPressed() {
    if (wb.canGoBack()) {
      wb.goBack();
    } else {
      if (System.currentTimeMillis() - exitTime < 2000) {
        super.onBackPressed();
      } else {
        exitTime = System.currentTimeMillis();
        Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
      }
    }
  }

}
