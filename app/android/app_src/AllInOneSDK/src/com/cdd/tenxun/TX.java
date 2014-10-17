package com.cdd.tenxun;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class TX {
  public static final String TAG = "TX";

  private static Activity activity;

  public static void init(Activity act) {
    activity = act;
  }

  /**
   * 加入开心消消乐群
   * 
   * @return
   */
  public static boolean joinKX() {
    String key = "MPrVcVyfklLn9xBehkw5_76IEP7hjnrL";
    return joinQQGroup(key);
  }

  public static boolean joinKX(Activity act) {
    String key = "MPrVcVyfklLn9xBehkw5_76IEP7hjnrL";
    return joinQQGroup(act, key);
  }

  /****************
   * 
   * 发起添加群流程。群号：开心消消乐 无限币(259489675) 的 key 为： MPrVcVyfklLn9xBehkw5_76IEP7hjnrL
   * 调用 joinQQGroup(MPrVcVyfklLn9xBehkw5_76IEP7hjnrL) 即可发起手Q客户端申请加群
   * 开心消消乐 无限币(259489675)
   * 
   * @param key
   *          由官网生成的key
   * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
   ******************/
  public static boolean joinQQGroup(String key) {
    return joinQQGroup(activity, key);
  }

  public static boolean joinQQGroup(Activity act, String key) {
    Intent intent = new Intent();
    intent
        .setData(Uri
            .parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D"
                + key));
    // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
    // //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
      act.startActivity(intent);
      return true;
    } catch (Exception e) {
      // 未安装手Q或安装的版本不支持
      return false;
    }
  }

}
