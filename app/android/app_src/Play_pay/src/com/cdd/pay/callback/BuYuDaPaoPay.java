package com.cdd.pay.callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 捕鱼爱打炮
 * 
 * @author lei
 * 
 */
public class BuYuDaPaoPay extends NoHandlerCallback {

  @Override
  public void onSucess(String item) {
    onPaySucess(item.replace("item", ""));
  }

  private void onPaySucess(String replace) {

  }

  @Override
  public String convertItem(String payCode) {
    String item = "item1";
    if ("1".equals(payCode)) {
      item = "item1";
    } else if ("2".equals(payCode)) {
      item = "item2";
    } else if ("3".equals(payCode)) {
      item = "item3";
    } else if ("4".equals(payCode)) {
      item = "item4";
    } else if ("5".equals(payCode)) {
      item = "item5";
    } else if ("6".equals(payCode)) {
      item = "item6";
    } else if ("7".equals(payCode)) {
      item = "item7";
    } else if ("8".equals(payCode)) {
      item = "item8";
    } else if ("9".equals(payCode)) {
      item = "item9";
    } else if ("10".equals(payCode)) {
      item = "item10";
    } else if ("11".equals(payCode)) {
      item = "item11";
    } else if ("12".equals(payCode)) {
      item = "item12";
    } else if ("13".equals(payCode)) {
      item = "item13";
    } else if ("14".equals(payCode)) {
      item = "item14";
    }
    return item;
  }

  public List<String> getItems() {
    List<String> items = new ArrayList<String>();
    items.add("item1");
    items.add("item2");
    items.add("item3");
    items.add("item4");
    items.add("item5");
    items.add("item6");
    items.add("item7");
    items.add("item8");
    items.add("item9");
    items.add("item10");
    items.add("item11");
    items.add("item12");
    items.add("item13");
    items.add("item14");
    return items;
  }

  @Override
  public void onFailed(String item) {

  }

}
