package com.cdd.pay.callback;

import android.os.Message;

import com.cdd.pay.PayMent;

public abstract class HandlerCallback implements PayMent {

  protected Message msg = null;

  @Override
  public final Message getMessage() {
    return msg;
  }
}
