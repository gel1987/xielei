package com.cdd.allpay.callback;

import android.os.Message;

import com.cdd.allpay.PayMent;

public abstract class HandlerCallback implements PayMent {

  protected Message msg = null;

  @Override
  public final Message getMessage() {
    return msg;
  }
}
