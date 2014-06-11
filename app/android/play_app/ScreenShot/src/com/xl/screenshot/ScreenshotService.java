package com.xl.screenshot;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class ScreenshotService extends Service {

  private NotificationManager notificationManager = null;
  private boolean isNotified = false;
  private ArrayList<ScreenshotObserver> fileObservers = new ArrayList<ScreenshotObserver>();

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    observerFile();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (!isNotified) {
      notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      Notification notification = new Notification(R.drawable.ic_launcher, getString(R.string.app_name),
          System.currentTimeMillis());
      notification.flags = Notification.FLAG_NO_CLEAR;
      Intent nintent = new Intent(this, getClass());
      PendingIntent pintent = PendingIntent.getService(this, 0, nintent, PendingIntent.FLAG_UPDATE_CURRENT);
      notification.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.running), pintent);
      notificationManager.notify(0, notification);
      isNotified = true;
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    for (ScreenshotObserver observer : fileObservers) {
      observer.stopWatching();
    }
  }

  private void observerFile() {
    initFileObservers();
    for (ScreenshotObserver observer : fileObservers) {
      observer.startWatching();
    }
  }

  private void initFileObservers() {
    File sdcard = new File(Environment.getExternalStorageDirectory().getPath());
    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String filename) {
        if (filename.toLowerCase().contains("pictures")) {
          return true;
        } else if (filename.toLowerCase().contains("dcim")) {
          return true;
        } else if (filename.toLowerCase().contains("screens")) {
          return true;
        } else {
          return false;
        }
      }
    };
    File[] paths = sdcard.listFiles(filter);
    if (paths != null) {
      for (File path : paths) {
        loopDir(path);
      }
    }
  }

  private void loopDir(File dir) {
    if (dir.isDirectory()) {
      if (dir.getName().toLowerCase().contains("screens")) {
        ScreenshotObserver observer = new ScreenshotObserver(dir.getPath());
        if (!fileObservers.contains(observer)) {
          fileObservers.add(observer);
        }
      } else {
        for (File child : dir.listFiles()) {
          if (child.isDirectory()) {
            // 这里是文件夹
            if (child.getName().toLowerCase().contains("screens")) {
              ScreenshotObserver observer = new ScreenshotObserver(child.getPath());
              if (!fileObservers.contains(observer)) {
                fileObservers.add(observer);
              }
            }
            loopDir(child);
          }
        }
      }
    }
  }
}
