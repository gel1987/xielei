package com.cdd.allpay.callback;


/**
 * 雷霆战机
 * 
 * @author lei
 * 
 */
public class Leiting2014Pay extends NoHandlerCallback {

  @Override
  public void onSucess(String item) {
    onPaySucess(getPayCode(item));
  }

  private int getPayCode(String item) {
    return Integer.valueOf(item);
  }

  private void onPaySucess(int payCode) {

  }

  public Leiting2014Pay() {
  }

  @Override
  public String convertItem(String payCode) {
    return payCode;
  }

  @Override
  public void onFailed(String item) {
    onPayFailed(getPayCode(item));
  }

  private void onPayFailed(int payCode) {

  }

}
