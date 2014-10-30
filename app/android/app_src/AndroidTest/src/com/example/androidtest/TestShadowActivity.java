package com.example.androidtest;

import testnever.test.ResourceUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.overlay.service.OverlayService;

public class TestShadowActivity extends Activity {
  private final String url = "http://www.4399.cn/mobile/news-id-325089.html#?gameId=26816";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startService(new Intent(this, OverlayService.class));
    Intent intent = new Intent(this, WebPageActivity.class);
    intent.putExtra(WebPageActivity.WEBPAGE_URL, url);
    // startActivity(intent);
    // finish();
    // getPackageManager().getPackageInfo("dd", 0).signatures;

    try {
      byte[] sign = getPackageManager().getPackageInfo("com.v0001.g0185", 64).signatures[0].toByteArray();
      String str1 = ResourceUtil.parseSignature(getPackageManager().getPackageInfo("com.v0001.g0185", 64).signatures[0].toByteArray());
      
      System.out.println(sign.toString());
    } catch (Exception e) {
    }

    ResourceUtil.getLayoutId(this, "bd6e2945dc519325");
  }
}
