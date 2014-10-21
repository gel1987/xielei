package com.cdd.pay.callback;

import android.os.Message;

import com.cdd.pay.PayMent;

public abstract class NoHandlerCallback implements PayMent {

  @Override
  public Message getMessage() {
    return null;
  }

}
