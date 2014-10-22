package com.cdd.admob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class CPObb extends Activity {

  ProgressDialog dlg = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String cpobb = DataStoreUtils.readLocalInfo(this, "cpobb");
    if (TextUtils.isEmpty(cpobb)) {
      dlg = new ProgressDialog(this);
      dlg.setTitle("初始化游戏");
      dlg.setMessage("正在拷贝必要数据,请耐心等待!");
      dlg.setIndeterminate(true);
      dlg.show();
    }else{
      Intent intent = new Intent();
      intent.setClassName(getPackageName(), "com.ayopagames.robotsloveicecream.PlaygroundLauncherActivity");
      startActivity(intent );
    }
  }
}
