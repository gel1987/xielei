package com.overlay.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;

public class OverlayView extends Button {
  public static final String TAG = "OverlayView";

  public OverlayView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public OverlayView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public OverlayView(Context context) {
    super(context);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

//  @Override
//  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//    DisplayMetrics dm = getResources().getDisplayMetrics();
//    int width = dm.widthPixels;
//    int height = dm.heightPixels;
//    super.onMeasure(width, height);
//  }

  // @Override
  // public boolean onKeyDown(int keyCode, KeyEvent event) {
  // Log.e(TAG, "onKeyDown");
  // return super.onKeyDown(keyCode, event);
  // }
  //
  // @Override
  // public boolean dispatchTouchEvent(MotionEvent event) {
  // Log.e(TAG, "dispatchTouchEvent");
  // return false;
  // }
//  @Override
//  public boolean onTouchEvent(MotionEvent event) {
//    Log.e(TAG, "onTouchEvent");
//    return false;
//  }
//
//  @Override
//  public boolean onTrackballEvent(MotionEvent event) {
//    Log.e(TAG, "onTrackballEvent");
//    return super.onTrackballEvent(event);
//  }
//
//  @Override
//  protected void onDetachedFromWindow() {
//    Log.e(TAG, "onDetachedFromWindow");
//    super.onDetachedFromWindow();
//  }
//
//  @Override
//  public boolean dispatchDragEvent(DragEvent event) {
//    Log.e(TAG, "dispatchDragEvent");
//    return super.dispatchDragEvent(event);
//  }
//
//
//  @Override
//  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
//    Log.e(TAG, "dispatchPopulateAccessibilityEvent");
//    return super.dispatchPopulateAccessibilityEvent(event);
//  }
//
//  @Override
//  protected void dispatchSetPressed(boolean pressed) {
//    Log.e(TAG, "dispatchSetPressed");
//    super.dispatchSetPressed(pressed);
//  }
//
//  @Override
//  public boolean dispatchUnhandledMove(View focused, int direction) {
//    Log.e(TAG, "dispatchUnhandledMove");
//    return super.dispatchUnhandledMove(focused, direction);
//  }
//
//  @Override
//  public boolean dispatchKeyEventPreIme(KeyEvent event) {
//    Log.e(TAG, "dispatchKeyEventPreIme");
//    return false;
//  }
//
//  @Override
//  public boolean dispatchKeyEvent(KeyEvent event) {
//    Log.e(TAG, "dispatchKeyEvent");
//    return super.dispatchKeyEvent(event);
//  }
//
//  @Override
//  public boolean dispatchKeyShortcutEvent(KeyEvent event) {
//    Log.e(TAG, "dispatchKeyShortcutEvent");
//    return super.dispatchKeyShortcutEvent(event);
//  }
//
//  @Override
//  protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
//    super.dispatchSaveInstanceState(container);
//    Log.e(TAG, "dispatchSaveInstanceState");
//  }
//
//  @Override
//  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
//    super.dispatchRestoreInstanceState(container);
//    Log.e(TAG, "dispatchRestoreInstanceState");
//  }
//
//  @Override
//  protected void dispatchSetActivated(boolean activated) {
//    Log.e(TAG, "dispatchSetActivated");
//    super.dispatchSetActivated(activated);
//  }
//
//  @Override
//  protected void dispatchSetSelected(boolean selected) {
//    Log.e(TAG, "dispatchSetSelected");
//    super.dispatchSetSelected(selected);
//  }

}
