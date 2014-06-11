package com.xl.screenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HandWriteView extends View {
  private Paint paint = null;
  private Bitmap originalBitmap = null;
  private Bitmap new1Bitmap = null;
  private Bitmap new2Bitmap = null;
  private float clickX = 0, clickY = 0;
  private float startX = 0, startY = 0;
  private boolean isMove = true;
  private boolean isClear = false;
  private int color = Color.GREEN;
  private float strokeWidth = 2.0f;

  public HandWriteView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public HandWriteView(Context context) {
    super(context);
  }

  public HandWriteView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setBitmap(String path) {
    originalBitmap = BitmapFactory.decodeFile(path).copy(Config.ARGB_8888, true);
    new1Bitmap = Bitmap.createBitmap(originalBitmap);
    invalidate();
  }

  public void clear() {
    isClear = true;
    if (new1Bitmap != null) {
      new1Bitmap.recycle();
    }
    new1Bitmap = Bitmap.createBitmap(originalBitmap);
    if (new2Bitmap != null) {
      new2Bitmap.recycle();
    }
    new2Bitmap = Bitmap.createBitmap(originalBitmap);
    invalidate();
  }

  public void setstyle(float strokeWidth) {
    this.strokeWidth = strokeWidth;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 手写板功能
    canvas.drawBitmap(handWriting(), 0, 0, null);
  }

  private Bitmap handWriting() {
    Canvas canvas = null;
    if (isClear) {
      canvas = new Canvas(new2Bitmap);
    } else {
      canvas = new Canvas(new1Bitmap);
    }
    paint = new Paint();
    paint.setStyle(Style.STROKE);
    paint.setAntiAlias(true);
    paint.setColor(color);
    paint.setStrokeWidth(strokeWidth);
    if (isMove) {
//      canvas.drawLine(startX, startY, clickX, clickY, paint);
    }

    startX = clickX;
    startY = clickY;

    if (isClear) {
      return new2Bitmap;
    }
    return new1Bitmap;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    clickX = event.getX();
    clickY = event.getY();
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      isMove = false;
      invalidate();
      return true;
    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
      isMove = true;
      invalidate();
      return true;
    }
    return super.onTouchEvent(event);
  }

}
