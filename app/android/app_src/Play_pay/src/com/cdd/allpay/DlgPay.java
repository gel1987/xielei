package com.cdd.allpay;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.cdd.allpay.callback.LeitingPay;
import com.cdd.allpay.util.IabHelper;
import com.cdd.allpay.util.IabResult;
import com.cdd.allpay.util.Inventory;
import com.cdd.allpay.util.Purchase;
import com.cdd.allpay.util.SkuDetails;

public class DlgPay extends Activity {

  private static final String TAG = "Pay";

  private static String base64EncodedPublicKey = Key.key;

  public static Handler handler = new Handler();

  private PayMent payMent = null;
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
        showError(item);
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
      mHelper.launchPurchaseFlow(DlgPay.this, item, 100, mPurchaseFinishedListener);
    }
  };
  // Callback for when a purchase is finished
  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
      Log.e(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
      if (result.isFailure()) {
        complain("Error purchasing: " + result);
        showError(item);
        return;
      }

      if (!verifyDeveloperPayload(purchase)) {
        complain("Error purchasing. Authenticity verification failed.");
        showError(item);
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
        showSucess(item);
      } else {
        Log.e(TAG, "支付失败");
        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        complain("Error while consuming: " + result);
      }
      Log.e(TAG, "End consumption flow.");
    }
  };

  private void setCallback() {
    payMent = new LeitingPay();
  }

  protected void startPay(String payItem) {
    this.item = payItem;
    setCallback();
    // Create the helper, passing it our context and the public key to
    // verify signatures with
    Log.e(TAG, "Creating IAB helper.");
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
          showError(item);
          return;
        }

        // Hooray, IAB is fully set up. Now, let's get an inventory of
        // stuff we own.
        Log.e(TAG, "Setup successful. Querying inventory.");
        ArrayList<String> additionalSkuList = new ArrayList<String>();
        additionalSkuList.add(DlgPay.this.item);
        mHelper.queryInventoryAsync(true, additionalSkuList, mGotInventoryListener);
      }

    });
  }

  private void showError(final String item) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(getApplication(), "Pay Error!", Toast.LENGTH_LONG).show();
        onFailed(item);
      }
    });
  }

  protected void onFailed(String item) {
  }

  protected void onSucess(String item) {
  }

  private void showSucess(final String item) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(getApplication(), "Pay Sucess!", Toast.LENGTH_LONG).show();
        onSucess(item);
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

}
