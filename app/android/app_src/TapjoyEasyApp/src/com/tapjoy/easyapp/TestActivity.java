package com.tapjoy.easyapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity implements OnClickListener {

  private Button spendPoints;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    TJWall.init(this);

    spendPoints = (Button) findViewById(R.id.SpendPointsButton);
    spendPoints.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    new Thread() {
      @Override
      public void run() {
        TJWall.spendCoin10("");
      }
    }.start();
  }

}
