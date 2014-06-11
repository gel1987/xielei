package com.xl.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    String help = DataStoreUtils.readLocalInfo(this, "help");
    if (TextUtils.isEmpty(help)) {
      Intent intent = new Intent(this, StartActivity.class);
      startActivity(intent);
      DataStoreUtils.saveLocalInfo(this, "help", "true");
    }
    finish();
  }

}
