package com.redeem;

import android.text.TextUtils;

/**
 * 兑换码
 * 
 * @author lei
 * 
 */
public class RedeemCode {

  /**
   * 使用兑换码
   * 
   * @param src
   */
  public static void redeem(String src) {
    String v = DES.aes(src.replaceAll("\\-", "") + "=");
    if (!TextUtils.isEmpty(v)) {
      if (v.startsWith("c")) {
        int coin = Integer.valueOf(v.substring(1));
        doAddCoin(coin);
      } else if (v.startsWith("gc")) {
        int coin = Integer.valueOf(v.substring(2));
        doAddCoin(coin);
        doAddGold(coin);
      } else if (v.startsWith("g")) {
        int coin = Integer.valueOf(v.substring(1));
        doAddGold(coin);
      } else {
        doFail();
      }
    } else {
      doFail();
    }
  }

  private static void doFail() {

  }

  private static void doAddGold(int coin) {

  }

  private static void doAddCoin(int coin) {

  }
}
