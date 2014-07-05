package com.cdd.obb;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.cdd.utils.FileUtil;

public class CopyObb {
  public static final String TAG = "CopyObb";

  /**
   * Expansion path where we store obb files
   */
  public static final String EXP_PATH = File.separator + "Android" + File.separator + "obb" + File.separator;

  static Handler handler = new Handler();

  public static void copyMainObb(final Activity act) {
    final ProgressDialog dlg = new ProgressDialog(act);
    dlg.setCancelable(false);
    dlg.setTitle("Copying Files...");
    dlg.setIndeterminate(true);
    dlg.show();
    new Thread() {
      @Override
      public void run() {
        try {
          String filename = getExpansionAPKFileName(act, true,
              act.getPackageManager().getPackageInfo(act.getPackageName(), 0).versionCode);
          String destName = generateSaveFileName(act, filename);
          FileUtil.copyFromAssets(act, "sys.main.obb", destName);
          dlg.cancel();
          act.finish();
          handler.post(new Runnable() {
            @Override
            public void run() {
              restartApp(act);
            }
          });
          System.exit(0);
        } catch (Exception e) {
        }
      }
    }.start();
  }

  public static void copyPatchObb(final Activity act) {
    new Thread() {
      @Override
      public void run() {
        try {
          String filename = getExpansionAPKFileName(act, false,
              act.getPackageManager().getPackageInfo(act.getPackageName(), 0).versionCode);
          String destName = generateSaveFileName(act, filename);
          FileUtil.copyFromAssets(act, "sys.patch.obb", destName);
        } catch (Exception e) {
        }
      }
    }.start();
  }

  public static String getExpansionAPKFileName(Context c, boolean mainFile, int versionCode) {
    return (mainFile ? "main." : "patch.") + versionCode + "." + c.getPackageName() + ".obb";
  }

  static public String getSaveFilePath(Context c) {
    File root = Environment.getExternalStorageDirectory();
    String path = root.toString() + EXP_PATH + c.getPackageName();
    return path;
  }

  static public String generateSaveFileName(Context c, String fileName) {
    String path = getSaveFilePath(c) + File.separator + fileName;
    return path;
  }

  public static void restartApp(Context ctx) {
    Toast.makeText(ctx, "Restart After 2s.", Toast.LENGTH_LONG).show();
    try {
      Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(ctx.getPackageName());
      PendingIntent sender = PendingIntent.getActivity(ctx, 0, intent, 0);
      // 设定一个五秒后的时间
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(System.currentTimeMillis());
      calendar.add(Calendar.SECOND, 2);

      AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
      alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
      // 或者以下面方式简化
      // alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5*1000,
      // sender);
    } catch (Exception e) {
    }
  }
}
