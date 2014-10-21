package test.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.cdd.allpay.Pay;
import com.tapjoy.easyapp.TJWall;

public class A extends Activity {

  Handler handler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Pay.startPay(this, "coin_product5");
    finish();
    
    TJWall.init(this);
    TJWall.showBanner();
    TJWall.showCover();
    TJWall.spendCoin10();
  }

  public void t() {
    Pay.startPay(this, "");
    Pay.startPay(this, 1);
  }
}
