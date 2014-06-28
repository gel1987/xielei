package com.overlay.service;

import java.lang.reflect.Constructor;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.overlay.ui.view.OverlayView;

public class OverlayService extends IntentService implements OnTouchListener, OnLongClickListener {
  private static final String TAG = "OverlayService";
  private WindowManager.LayoutParams windowParams;
  private WindowManager windowManager = null;
  private OverlayView overlayView = null;

  private int statusBarHeight = 0;

  private float screenWidth = 0;
  private float screenHeight = 0;

  public OverlayService() {
    super(TAG);
  }

  private void initWinParams() {
    windowParams = new WindowManager.LayoutParams();
    windowParams.type = LayoutParams.TYPE_SYSTEM_ALERT; // 设置window type
    windowParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
    // 设置Window flag
    windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    windowParams.alpha = 0.5f;
    /*
     * WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD; 注意，flag的值可以为：
     * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
     * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦 LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
     */
    // 调整悬浮窗口至左上角，便于调整坐标
    windowParams.gravity = Gravity.LEFT | Gravity.TOP;
    // 以屏幕左上角为原点，设置x、y初始值
    windowParams.x = (int) 0;
    windowParams.y = (int) 30;
    // 设置悬浮窗口长宽数据
    windowParams.width = LayoutParams.WRAP_CONTENT;
    windowParams.height = LayoutParams.WRAP_CONTENT;
    // windowParams.height = LayoutParams.MATCH_PARENT;
    statusBarHeight = getStatusBarHeight();

    DisplayMetrics dm = getResources().getDisplayMetrics();
    screenWidth = dm.widthPixels;
    screenHeight = dm.heightPixels;
  }

  /**
   * 获取状态栏高度
   * 
   * @return
   */
  public int getStatusBarHeight() {
    Class<?> c = null;
    Object obj = null;
    java.lang.reflect.Field field = null;
    int x = 0;
    int statusBarHeight = 0;
    try {
      c = Class.forName("com.android.internal.R$dimen");
      obj = c.newInstance();
      field = c.getField("status_bar_height");
      x = Integer.parseInt(field.get(obj).toString());
      statusBarHeight = getResources().getDimensionPixelSize(x);
      return statusBarHeight;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return statusBarHeight;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initWinParams();
    // 获取WindowManager
    windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    overlayView = new OverlayView(this);
    overlayView.setBackgroundColor(Color.BLACK);
    overlayView.setText("T");
    overlayView.setOnTouchListener(this);
    overlayView.setOnLongClickListener(this);
    windowManager.addView(overlayView, windowParams);
  }

  @Override
  protected void onHandleIntent(Intent intent) {

  }

  private float tempX = 0;
  private float tempY = 0;
  private float left = 0;
  private float top = 0;
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      windowParams.x = msg.arg1;
      windowParams.y = msg.arg2;
      windowManager.updateViewLayout(overlayView, windowParams);
    }
  };

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    Log.e(TAG, "onTouch");
    float rawX = event.getRawX();
    float rawY = event.getRawY();
    float x = event.getX();
    float y = event.getY();
    int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_POINTER_DOWN:
        left = rawX - x;
        top = rawY - y - statusBarHeight;
        tempX = rawX;
        tempY = rawY;
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
      case MotionEvent.ACTION_CANCEL:
        x = left + (rawX - tempX);
        y = top + (rawY - tempY);
        final int toXPoint = (int) (x < screenWidth / 2 ? 0 : screenWidth - overlayView.getWidth());
        final int toYPoint = (int) (y > screenHeight - overlayView.getHeight() - statusBarHeight ? screenHeight
            - overlayView.getHeight() - statusBarHeight : y);

        int oldX = windowParams.x;
        float pre = (toXPoint - oldX) / 200f;
        for (int i = 0; i < 150; i++) {
          int tox = toXPoint;
          if (i != 149) {
            tox = (int) (oldX + pre * i);
          }
          Message msg = Message.obtain(handler, 0, tox, toYPoint);
          handler.sendMessageDelayed(msg, i);
        }
        break;
      case MotionEvent.ACTION_MOVE:
      case MotionEvent.ACTION_OUTSIDE:
        x = left + (rawX - tempX);
        y = top + (rawY - tempY);
        if (x < 0) {
          x = 0;
        } else if (x > screenWidth - overlayView.getWidth()) {
          x = screenWidth - overlayView.getWidth();
        }
        if (y < 0) {
          y = 0;
        } else if (y > screenHeight - statusBarHeight - overlayView.getHeight()) {
          y = screenHeight - statusBarHeight - overlayView.getHeight();
        }
        windowParams.x = (int) x;
        windowParams.y = (int) y;
        windowManager.updateViewLayout(overlayView, windowParams);
        break;
    }
    return false;
  }

  @Override
  public boolean onLongClick(View v) {
    try {
      String recentDialogClass = "com.android.internal.policy.impl.RecentApplicationsDialog";
      Class dialogClass = Class.forName(recentDialogClass);
      Constructor ctor = dialogClass.getConstructor(Context.class);
      Dialog dialog = (Dialog) ctor.newInstance(this);
      dialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
