package test;

import com.cdd.allpay.Pay;
import com.cdd.tapjoy.TJWall;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
  public static final String TAG = "TestActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Pay.setActivity(this);
    TJWall.setActivity(this);
    TJWall.initInstace();
    TJWall.setHideVideo();
    TJWall.setShowBanner();

    Pay.startPay(this, "115");
    // Pay.startPay(this, 1);
    // Pay.startPay(this, "", null);
  }

}
