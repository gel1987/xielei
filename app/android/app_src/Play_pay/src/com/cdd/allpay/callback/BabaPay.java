package com.cdd.allpay.callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爸爸去哪1.8.0
 * 
 * @author lei
 * 
 */
public class BabaPay extends NoHandlerCallback {

  private HashMap<String, String> itemKeys = new HashMap<String, String>();

  private String DEFAULT_PAYCODE = "100013";

  @Override
  public void onSucess(String item) {
    onPaySucess(getPayCode(item));
  }

  private String getPayCode(String item) {
    String payCode = null;
    for (Map.Entry<String, String> entry : itemKeys.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (item.equals(value)) {
        payCode = key;
      }
    }
    if (payCode == null) {
      payCode = DEFAULT_PAYCODE;
    }
    return payCode;
  }

  private void onPaySucess(String payCode) {

  }

  public BabaPay() {
    itemKeys.put("100001", "100001");
    itemKeys.put("100002", "100002");
    itemKeys.put("100003", "100003");
    itemKeys.put("100004", "100004");
    itemKeys.put("100005", "item100005");
    itemKeys.put("100006", "item100006");
    itemKeys.put("100007", "item100007");
    itemKeys.put("100008", "item100008");
    itemKeys.put("100009", "item100009");
    itemKeys.put("100010", "item100010");
    itemKeys.put("100011", "100011");
    itemKeys.put("100012", "100012");
    itemKeys.put("100013", "item1");
    itemKeys.put("100014", "item2");
    itemKeys.put("100015", "item3");
    itemKeys.put("100016", "item5");
    itemKeys.put("100017", "item10");
    itemKeys.put("100018", "item50");
    itemKeys.put("100019", "item100019");
    itemKeys.put("100020", "item100020");
    itemKeys.put("100021", "item100021");
    itemKeys.put("100022", "item100022");
    itemKeys.put("100023", "item100023");
    itemKeys.put("100024", "item100024");
    itemKeys.put("100025", "100025");
    itemKeys.put("100037", "100037");
    itemKeys.put("100038", "100038");
    itemKeys.put("100039", "100039");
  }

  @Override
  public String convertItem(String payCode) {
    String item = itemKeys.get(payCode);
    if (item == null) {
      item = "item1";
    }
    return item;
  }

  public List<String> getItems() {
    List<String> items = new ArrayList<String>();
    items.add("item1");
    items.add("item2");
    items.add("item3");
    items.add("item5");
    items.add("item10");
    items.add("item50");
    items.add("item100005");
    items.add("item100006");
    items.add("item100019");
    items.add("item100020");
    items.add("item100023");
    items.add("item100007");
    items.add("item100008");
    items.add("item100010");
    items.add("item100009");
    items.add("item100021");
    items.add("item100022");
    items.add("item100024");
    return items;
  }

  @Override
  public void onFailed(String item) {
    onPayFailed(getPayCode(item));
  }

  private void onPayFailed(String payCode) {

  }

}
