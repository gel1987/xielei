package com.example.androidtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPageActivity extends Activity implements DownloadListener {

  private WebView wb = null;

  private String url = null;

  public static final String WEBPAGE_URL = "WEBPAGE_URL";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    if (intent == null || TextUtils.isEmpty((url = intent.getStringExtra(WEBPAGE_URL)))) {
      finish();
    }
    initview(url);
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void initview(String url) {
    wb = new WebView(this);
    setContentView(wb);
    wb.setWebViewClient(new WebViewClient() {
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.clearCache(true);
//         view.loadUrl("http://yun.baidu.com/share/link?shareid=1131798687&uk=3207806909");
         Uri uri = Uri.parse("http://pan.baidu.com/s/1dDvhlhn");
         Intent intent = new Intent(Intent.ACTION_VIEW, uri);
         startActivity(intent);
        return true;
      }
    });
    WebSettings settings = wb.getSettings();
    settings.setSupportZoom(true);
    settings.setDefaultTextEncodingName("utf-8");
    settings.setRenderPriority(RenderPriority.HIGH);
    settings.setJavaScriptEnabled(true);
    settings.setBlockNetworkImage(true);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
    wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_INSET);
    wb.setWebChromeClient(new WebChromeClient() {
      public void onProgressChanged(WebView view, int progress) {
        setTitle(getWebTitle(wb.getTitle()));
      }

    });
    wb.setDownloadListener(this);
    wb.loadUrl(url);
  }

  protected String getWebTitle(String title) {
    String result = "";
    if (TextUtils.isEmpty(title)) {
      return result;
    }
    int len = title.length();
    if (len > 12) {
      result = title.substring(0, 11);
    } else {
      result = title;
    }
    return result;
  }

  @Override
  public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
      long contentLength) {
    Uri uri = Uri.parse("http://pan.baidu.com/s/1dDvhlhn");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

}
