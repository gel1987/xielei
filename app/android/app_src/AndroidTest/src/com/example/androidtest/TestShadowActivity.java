package com.example.androidtest;

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
//    finish();

  }
}
