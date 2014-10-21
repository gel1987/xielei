package com.cdd.pay;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.cdd.pay.callback.Leiting2014Pay;
import com.cdd.pay.util.IabHelper;
import com.cdd.pay.util.IabResult;
import com.cdd.pay.util.Inventory;
import com.cdd.pay.util.Purchase;
import com.cdd.pay.util.SkuDetails;

public class Pay extends Activity {

  private static final String TAG = "Pay";

  private static final String PARAM_ITEM = "item";

  private static String base64EncodedPublicKey = Key.key;

  private static Handler handler;
  // The helper object
  private IabHelper mHelper;
  private String item = "item1";
  // Listener that's called when we finish querying the items and
  // subscriptions we own
  IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {

    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
      Log.e(TAG, "Query inventory finished.");
      if (result.isFailure()) {
        complain("Failed to query inventory: " + result);
        finish();
        return;
      }

      Log.e(TAG, "Query inventory was successful.");

      /*
       * Check for items we own. Notice that for each purchase, we check the
       * developer payload to see if it's correct! See verifyDeveloperPayload().
       */
      // Check for gas delivery -- if we own gas, we should fill up the tank
      // immediately
      Purchase purchase = inventory.getPurchase(item);
      if (purchase != null && verifyDeveloperPayload(purchase)) {
        Log.d(TAG, "We have gas. Consuming it.");
        mHelper.consumeAsync(inventory.getPurchase(item), mConsumeFinishedListener);
        return;
      }

      // Do we have the premium upgrade?
      SkuDetails item1SkuDetail = inventory.getSkuDetails(item);
      Log.e(TAG, "item1SkuDetail is " + item1SkuDetail);
      mHelper.launchPurchaseFlow(Pay.this, item, 100, mPurchaseFinishedListener);
    }
  };
  // Callback for when a purchase is finished
  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
      Log.e(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
      if (result.isFailure()) {
        complain("Error purchasing: " + result);
        finish();
        return;
      }

      if (!verifyDeveloperPayload(purchase)) {
        complain("Error purchasing. Authenticity verification failed.");
        finish();
        return;
      }

      Log.e(TAG, "Purchase successful.");

      if (purchase.getSku().equals(item)) {
        // bought 1/4 tank of gas. So consume it.
        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        // bought the premium upgrade!
        Log.e(TAG, "Purchase is SKU_JieShuo_SHAIDAO. Congratulating user.");
      }
    }
  };

  // Called when consumption is complete
  IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
    public void onConsumeFinished(Purchase purchase, IabResult result) {
      Log.e(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

      // We know this is the "gas" sku because it's the only one we
      // consume,
      // so we don't check which sku was consumed. If you have more than
      // one
      // sku, you probably should check...
      if (result.isSuccess()) {
        // successfully consumed, so we apply the effects of the item in
        // our
        // game world's logic, which in our case means filling the gas
        // tank a bit
        Log.e(TAG, "支付成功");
        payMent.onSucess(item);
        finish();
      } else {
        Log.e(TAG, "支付失败");
        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        complain("Error while consuming: " + result);
      }
      Log.e(TAG, "End consumption flow.");
    }
  };

  public static PayMent payMent = null;

  private static void setCallback() {
    payMent = new Leiting2014Pay();
  }

  public static void startPay(Activity act, String item, Handler pHandler) {
    handler = pHandler;
    startPay(act, item);
  }

  public static void pay(Activity ctx, String item) {
    Intent intent = new Intent(ctx, Pay.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra(PARAM_ITEM, payMent.convertItem(item));
    ctx.startActivity(intent);
  }

  public static void pay(Activity ctx, int item) {
    startPay(ctx, "" + item);
  }

  public static void startPay(final Activity act, final int item) {
    startPay(act, "" + item);
  }

  public static void startPay(final Activity act, final String item) {
    setCallback();
    act.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        pay(act, item);
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    item = getIntent().getStringExtra(PARAM_ITEM);
    // Create the helper, passing it our context and the public key to
    // verify signatures with
    Log.e(TAG, "Creating IAB helper. item:" + item);
    mHelper = new IabHelper(this, base64EncodedPublicKey);

    // enable debug logging (for a production application, you should set
    // this to false).
    mHelper.enableDebugLogging(true);

    // Start setup. This is asynchronous and the specified listener
    // will be called once setup completes.
    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
      public void onIabSetupFinished(IabResult result) {
        Log.e(TAG, "Setup finished.");
        if (!result.isSuccess()) {
          // Oh noes, there was a problem.
          complain("Problem setting up in-app billing: " + result);
          finish();
          return;
        }

        // Hooray, IAB is fully set up. Now, let's get an inventory of
        // stuff we own.
        Log.e(TAG, "Setup successful. Querying inventory.");
        ArrayList<String> additionalSkuList = new ArrayList<String>();
        additionalSkuList.add(item);
        mHelper.queryInventoryAsync(true, additionalSkuList, mGotInventoryListener);
      }
    });
  }

  void complain(String message) {
    // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    payMent.onFailed(item);
    Log.e(TAG, "**** TrivialDrive Error: " + message);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    try {
      if (mHelper != null)
        mHelper.dispose();
    } catch (Exception e) {
    }
    mHelper = null;
  }

  /** Verifies the developer payload of a purchase. */
  boolean verifyDeveloperPayload(Purchase p) {
    // String payload = p.getDeveloperPayload();

    /*
     * verify that the developer payload of the purchase is correct. It will be
     * the same one that you sent when initiating the purchase.
     * 
     * WARNING: Locally generating a random string when starting a purchase and
     * verifying it here might seem like a good approach, but this will fail in
     * the case where the user purchases an item on one device and then uses
     * your app on a different device, because on the other device you will not
     * have access to the random string you originally generated.
     * 
     * So a good developer payload has these characteristics:
     * 
     * 1. If two different users purchase an item, the payload is different
     * between them, so that one user's purchase can't be replayed to another
     * user.
     * 
     * 2. The payload must be such that you can verify it even when the app
     * wasn't the one who initiated the purchase flow (so that items purchased
     * by the user on one device work on other devices owned by the user).
     * 
     * Using your own server to store and verify developer payloads across app
     * installations is recommended.
     */

    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.e(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

    // Pass on the activity result to the helper for handling
    if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
      // not handled, so handle it ourselves (here's where you'd
      // perform any handling of activity results not related to in-app
      // billing...
      super.onActivityResult(requestCode, resultCode, data);
    } else {
      Log.e(TAG, "onActivityResult handled by IABUtil.");
    }
  }

  @Override
  public void finish() {
    if (handler != null && payMent.getMessage() != null) {
      handler.sendMessageDelayed(payMent.getMessage(), 800);
    }
    super.finish();
  }

}
