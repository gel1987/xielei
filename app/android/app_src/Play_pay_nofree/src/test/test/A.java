package test.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.cdd.pay.Pay;

public class A extends Activity {

  Handler handler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Pay.startPay(this, "1");
    // finish();

  }

  public void t() {
    Pay.startPay(this, "");
    Pay.startPay(this, 1);
  }
}
