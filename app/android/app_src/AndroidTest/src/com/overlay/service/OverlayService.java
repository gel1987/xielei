package com.overlay.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.overlay.ui.view.OverlayView;

public class OverlayService extends IntentService implements OnTouchListener {
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
    windowParams.type = LayoutParams.TYPE_PHONE; // 设置window type
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
    windowManager.addView(overlayView, windowParams);
  }

  @Override
  protected void onHandleIntent(Intent intent) {

  }

  private float tempX = 0;
  private float tempY = 0;
  private float left = 0;
  private float top = 0;
  private Handler handler = new Handler();

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
        Log.e(TAG, "ACTION_DOWN:" + action);
        left = rawX - x;
        top = rawY - y - statusBarHeight;
        tempX = rawX;
        tempY = rawY;
        Log.e(TAG, String.format("tempX=%f,tempY=%f,rawX=%f,rawY=%f", tempX, tempY, rawX, rawY));
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
      case MotionEvent.ACTION_CANCEL:
        Log.e(TAG, "ACTION_UP:" + action);
        x = left + (rawX - tempX);
        y = top + (rawY - tempY);
        float toX = x < screenWidth / 2 ? 0 : screenWidth - overlayView.getWidth();
        float toY = 0;
        if (y < screenHeight / 10) {
          toY = 20;
        } else if (y < screenHeight / 10 * 2) {
          toY = screenHeight / 10 * 2 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 3) {
          toY = screenHeight / 10 * 3 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 4) {
          toY = screenHeight / 10 * 4 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 5) {
          toY = screenHeight / 10 * 5 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 6) {
          toY = screenHeight / 10 * 6 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 7) {
          toY = screenHeight / 10 * 7 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 8) {
          toY = screenHeight / 10 * 8 - overlayView.getHeight();
        } else if (y < screenHeight / 10 * 9) {
          toY = screenHeight / 10 * 9 - overlayView.getHeight();
        } else {
          toY = screenHeight - overlayView.getHeight() - statusBarHeight;
        }
        final int toXPoint = (int) toX;
        final int toYPoint = (int) toY;
        Log.e(TAG, String.format("toX=%f,toY=%f", toX, toY));
        Runnable task = new Runnable() {
          @Override
          public void run() {
            windowParams.x = toXPoint;
            windowParams.y = toYPoint;
            windowManager.updateViewLayout(overlayView, windowParams);
          }
        };
        handler.post(task);
        break;
      case MotionEvent.ACTION_MOVE:
      case MotionEvent.ACTION_OUTSIDE:
        Log.e(TAG, "ACTION_MOVE:" + action);
        x = left + (rawX - tempX);
        y = top + (rawY - tempY);
        Log.e(TAG, String.format("rawX=%f,rawY=%f,x=%f,y=%f", rawX, rawY, x, y));
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
    return true;
  }
}
