package com.cdd.allpay.callback;

import android.os.Message;

import com.cdd.allpay.PayMent;

public abstract class NoHandlerCallback implements PayMent {

  @Override
  public Message getMessage() {
    return null;
  }

}
