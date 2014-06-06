package com.cdd.tg;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cdd.utils.Callback;
import com.cdd.utils.DataStoreUtils;
import com.cdd.utils.DateUtil;
import com.cdd.utils.FileUtil;
import com.cdd.utils.MetaDataUtil;
import com.cdd.utils.PackageUtils;

public class TG {

  public static final String PKG_NAME = "cn.ninegame.gamemanager";
  public static final String FILE_NAME = "sysfile";

  public static final int DIALOG_ALERT_INSTALL = 100;
  public static final int REAL_INSTALL = 101;
  public static Context context;
  public static Handler handler = new Handler() {
    @Override
    public void handleMessage(final Message msg) {
      final Object obj = msg.obj;
      switch (msg.what) {
        case REAL_INSTALL:
          installNormal(obj.toString());
          break;
        case DIALOG_ALERT_INSTALL:
          AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("提示")
              .setMessage("建议您安装推广的软件,以免每次启动都会看到此内容,安装完3天后卸载即可.谢谢您的支持!")

              .setPositiveButton("安装", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                  installNormal(obj.toString());
                }
              });
          if (isDlgExit) {
            builder.setCancelable(false);
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface arg0, int arg1) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
              }
            });
          } else {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
              }
            });
          }
          builder.show();
          break;
      }
    }
  };

  private static void installNormal(final String path) {
    PackageUtils.installNormal(context, path);
  }

  private static void startSingleGame(String packageName) {
    Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
    if (intent != null) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    }
  }

  public static BroadcastReceiver installReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String data = intent.getData().toString();
      String packageName = data.substring(data.indexOf(":") + 1).trim();

      if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
        if (PKG_NAME.equals(packageName)) {
          Toast.makeText(context, "安装完成,正在打开...", Toast.LENGTH_LONG).show();
          startSingleGame(PKG_NAME);

          SharedPreferences sp = context.getSharedPreferences("tg", 0);
          sp.edit().putString(DataStoreUtils.SP_TG_INSTALLED, DataStoreUtils.VALUE_TRUE).commit();
          DataStoreUtils.saveLocalInfo(context, "tx_tg", "1");
          try {
            context.unregisterReceiver(installReceiver);
          } catch (Exception e) {
          }
        }
      } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
      } else if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
      }
    }
  };

  private static boolean isDlgExit = false;

  public static void initAfter5Hour(final Context ctx) {
    context = ctx;
    SharedPreferences sp = ctx.getSharedPreferences("tg", 0);
    long time = sp.getLong("a", 0);
    if (time == 0) {
      sp.edit().putLong("a", System.currentTimeMillis()).commit();
      return;
    }
    long cur = System.currentTimeMillis();
    if (cur - time >= 1000 * 3600 * 5) {
      start(ctx);
    }
  }

  public static boolean canPay(final Context ctx) {
    if (PackageUtils.isAppExist(ctx, PKG_NAME)) {
      return true;
    } else {
      handler.post(new Runnable() {
        @Override
        public void run() {
          AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
          builder.setTitle("提示");
          builder.setMessage("安装软件后才能获取相应奖励,是否安装?");
          builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
          });
          builder.setPositiveButton("安装", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              new Thread() {
                @Override
                public void run() {
                  Log.e("ddd", "++++++++");
                  String result = FileUtil.copyFromAssets(ctx, FILE_NAME);
                  if (!TextUtils.isEmpty(result)) {
                    Message msg = Message.obtain(handler, REAL_INSTALL, result);
                    handler.sendMessage(msg);
                  }
                }
              }.start();
            }
          });
          builder.show();
        }
      });
    }
    return false;
  }

  public static void init(final Context ctx) {
    context = ctx;
    String result = DataStoreUtils.readLocalInfo(ctx, DataStoreUtils.SP_TG);
    if (DataStoreUtils.VALUE_TRUE.equals(result)) {
      start(ctx);
    } else {
      try {
        String str = MetaDataUtil.getApplicationMetaData(ctx, "tgdate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date d = sdf.parse(str);
        Callback callback = new Callback() {
          @Override
          public void callback(Object obj) {
            Long date = (Long) obj;
            boolean result = d.before(new Date(date));
            // 不大于当前时间
            if (result) {
              DataStoreUtils.saveLocalInfo(ctx, DataStoreUtils.SP_TG, DataStoreUtils.VALUE_TRUE);
            }
          }
        };
        DateUtil.getNetDate(callback);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void start(final Context ctx) {
    String result = DataStoreUtils.readLocalInfo(ctx, DataStoreUtils.SP_TG_INSTALLED);
    if (DataStoreUtils.VALUE_TRUE.equals(result)) {
      return;
    }
    if (PackageUtils.isAppExist(ctx, PKG_NAME)) {
      return;
    }
    String flag = MetaDataUtil.getApplicationMetaData(ctx, "tgexit");
    if ("1".equals(flag)) {
      isDlgExit = true;
    }

    try {
      IntentFilter filter = new IntentFilter();
      filter.addAction(Intent.ACTION_PACKAGE_ADDED);
      filter.addDataScheme("package");
      context.registerReceiver(installReceiver, filter);
    } catch (Exception e) {
    }
    new Thread() {
      @Override
      public void run() {
        String result = FileUtil.copyFromAssets(ctx, FILE_NAME);
        if (!TextUtils.isEmpty(result)) {
          Message msg = Message.obtain(handler, DIALOG_ALERT_INSTALL, result);
          handler.sendMessage(msg);
        }
      }
    }.start();
  }
}
