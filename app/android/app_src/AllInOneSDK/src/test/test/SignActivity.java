package test.test;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.cdd.sign.SignFree;

public class SignActivity extends Activity {
  public static final String TAG = "SignActivity";

  private SignFree sign;

  @Override
  public PackageManager getPackageManager() {
    if (sign == null) {
      sign = new SignFree();
      sign.setPackageManager(super.getPackageManager());
    }
    return sign;
  }
}
