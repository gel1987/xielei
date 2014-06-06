package com.lxl;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    YoumiAd.init(this);
    YoumiAd.showBannerAd(this);
    YoumiAd.showBannerAdBottom(this);
    YoumiAd.showScreen(this);
    YoumiAd.showScreenOnce(this);
    YoumiAd.showSmartBanner(this);
    YoumiAd.showSmartBannerOnce(this);
  }

}
