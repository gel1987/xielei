package com.cdd.allpay.callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 捕鱼达人2
 * 
 * @author lei
 * 
 */
public class FishJoy2Pay extends HandlerCallback {

  private String item1 = "coin_product1";
  private String item2 = "coin_product2";
  private String item3 = "coin_product3";
  private String item4 = "coin_product4";
  private String item5 = "coin_product5";
  private String item6 = "coin_product6";
  private String item7 = "gem_product1";
  private String item8 = "gem_product2";
  private String item9 = "gem_product3";
  private String item10 = "gem_product4";
  private String item11 = "gem_product5";
  private String item12 = "gem_product6";

  @Override
  public void onSucess(String item) {
    onPaySucess(item.toUpperCase());
  }

  private void onPaySucess(String payCode) {

  }

  @Override
  public String convertItem(String payCode) {
    String item = item1;
    if (item1.equalsIgnoreCase(payCode)) {
      item = item1;
    } else if (item2.equalsIgnoreCase(payCode)) {
      item = item2;
    } else if (item3.equalsIgnoreCase(payCode)) {
      item = item3;
    } else if (item4.equalsIgnoreCase(payCode)) {
      item = item4;
    } else if (item5.equalsIgnoreCase(payCode)) {
      item = item5;
    } else if (item6.equalsIgnoreCase(payCode)) {
      item = item6;
    } else if (item7.equalsIgnoreCase(payCode)) {
      item = item7;
    } else if (item8.equalsIgnoreCase(payCode)) {
      item = item8;
    } else if (item9.equalsIgnoreCase(payCode)) {
      item = item9;
    } else if (item10.equalsIgnoreCase(payCode)) {
      item = item10;
    } else if (item11.equalsIgnoreCase(payCode)) {
      item = item11;
    } else if (item12.equalsIgnoreCase(payCode)) {
      item = item12;
    }
    return item;
  }

  public List<String> getItems() {
    List<String> items = new ArrayList<String>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    items.add(item4);
    items.add(item5);
    items.add(item6);
    items.add(item7);
    items.add(item8);
    items.add(item9);
    items.add(item10);
    items.add(item11);
    items.add(item12);
    return items;
  }

  @Override
  public void onFailed(String item) {
    onPayFailed(item.toUpperCase());
  }

  private void onPayFailed(String payCode) {

  }

}
