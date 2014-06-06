package com.mdplay.androgensroms;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.vending.expansion.downloader.Helpers;
import com.mdplay.gensroms.R;

public class MainActivity extends Activity {

  protected static final int SUCESS = 0;
  protected static final int FAILED = 1;

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case SUCESS:
          Toast.makeText(MainActivity.this, R.string.sucess, Toast.LENGTH_LONG).show();
          break;
        case FAILED:
          Toast.makeText(MainActivity.this, R.string.failed, Toast.LENGTH_LONG).show();
          break;
      }
      if (waitingDlg != null) {
        waitingDlg.cancel();
      }
      finish();
    }
  };

  String fileName = null;
  ProgressDialog waitingDlg = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int versionCode = 14;
    fileName = getExpansionAPKFileName(versionCode);
    if (TextUtils.isEmpty(fileName)) {
      Toast.makeText(this, R.string.no_file, Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle(R.string.title);
    alert.setMessage(R.string.message);
    alert.setPositiveButton(R.string.ok, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        if (waitingDlg != null) {
          waitingDlg.cancel();
        }
        waitingDlg = new ProgressDialog(MainActivity.this);
        waitingDlg.setCancelable(false);
        waitingDlg.setCanceledOnTouchOutside(false);
        waitingDlg.setMessage(getString(R.string.waiting));
        waitingDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDlg.setButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            finish();
            System.exit(0);
          }
        });
        waitingDlg.show();
        unzip();
      }
    });
    alert.setNegativeButton(R.string.cancel, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        finish();
      }
    });
    alert.show();
  }

  private String getExpansionAPKFileName(int versionCode) {
    String fileName = Helpers.getExpansionAPKFileName(this, true, versionCode);
    fileName = Helpers.generateSaveFileName(this, fileName);
    File file = new File(fileName);
    if (file.exists()) {
      return fileName;
    }
    return null;
  }

  private void unzip() {
    new Thread() {
      @Override
      public void run() {
        File zipFile = new File(fileName);
        String folderPath = Environment.getExternalStorageDirectory() + "/Genesis/roms/";
        if (ZipUtil.unZipFile(zipFile, folderPath)) {
          handler.sendEmptyMessage(SUCESS);
        } else {
          handler.sendEmptyMessage(FAILED);
        }
      }
    }.start();
  }
}
