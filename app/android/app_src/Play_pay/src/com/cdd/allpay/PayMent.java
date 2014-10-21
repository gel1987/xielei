package com.cdd.allpay;

import android.os.Message;

public interface PayMent {

  public void onFailed(String item);

  public void onSucess(String item);

  public String convertItem(String payCode);

  public Message getMessage();
}
