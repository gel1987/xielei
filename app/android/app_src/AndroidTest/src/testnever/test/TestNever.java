package testnever.test;

import java.util.HashMap;

public class TestNever {
  public static final String TAG = "TestNever";

  public void test(String paycode) {
    HashMap paramHashMap = new HashMap();
    paramHashMap.put("Paycode", paycode);
    onBillingFinish(0x66, paramHashMap );
  }

  public void onBillingFinish(int paramInt, HashMap paramHashMap) {

  }
}
