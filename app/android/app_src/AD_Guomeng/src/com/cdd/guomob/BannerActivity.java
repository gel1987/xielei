package com.cdd.guomob;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BannerActivity extends Activity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    GuomobBanner.addBanner(this);
    GuomobCover.showCover(this, true);
  }

}
