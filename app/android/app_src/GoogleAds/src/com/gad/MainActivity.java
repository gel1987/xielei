package com.gad;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    GAD.showbannerB(this);
    GAD.showCover(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
    ExitDialog.exit(this);
    ExitDialog.rate(this);
  }

}