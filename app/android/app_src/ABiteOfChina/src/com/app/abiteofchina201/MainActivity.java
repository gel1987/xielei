package com.app.abiteofchina201;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.vending.expansion.downloader.Helpers;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int versionCode = 10;
    String fileName = getExpansionAPKFileName(versionCode);
    if (TextUtils.isEmpty(fileName)) {
      Toast.makeText(this, R.string.no_file, Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    Uri uri = Uri.parse(fileName);
    // 调用系统自带的播放器
    Intent intent = new Intent(Intent.ACTION_VIEW);
    Log.v("URI:::::::::", uri.toString());
    intent.setDataAndType(uri, "video/mp4");
    startActivity(intent);
    finish();
  }

  public String getExpansionAPKFileName(int versionCode) {
    String fileName = Helpers.getExpansionAPKFileName(this, true, versionCode);
    fileName = Helpers.generateSaveFileName(this, fileName);
    File file = new File(fileName);
    if (file.exists()) {
      return fileName;
    }
    return null;
  }

}
