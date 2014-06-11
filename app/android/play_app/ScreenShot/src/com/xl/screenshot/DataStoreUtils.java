package com.xl.screenshot;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStoreUtils {

  private static final String FILE_NAME = "tg";

  public static final String DEFAULT_VALUE = "";

  public static final String VALUE_TRUE = "1";
  public static final String VALUE_FALSE = "0";

  // panda push
  public static final String SP_P = "p";
  // d push
  public static final String SP_D = "d";
  // pj
  public static final String SP_PJ = "pj";
  // tg
  public static final String SP_TG = "tg";
  // tg installed
  public static final String SP_TG_INSTALLED = "installed";
  // 启动页
  public static final String SP_FC = "fc";
  public static SharedPreferences share;

  /** 通用 true */
  public static final String SP_TRUE = "true";
  /** 通用 false */
  public static final String SP_FALSE = "false";

  /** 最后弹广告的时间 */
  public static final String LAST_POP_TIME = "l_p_t";
  /** 最后弹的应用 */
  public static final String LAST_PKG = "l_pkg";

  public static final String POP_ADV_ENABLE = "pop_adv";

  // 保存本地信息
  public static void saveLocalInfo(Context ctx, String name, String value) {
    if (share == null) {
      share = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
    if (share != null) {
      share.edit().putString(name, value).commit();
    }
    share = null;
  }

  // 读取本地信息
  public static String readLocalInfo(Context ctx, String name) {
    if (share == null) {
      share = ctx.getSharedPreferences(FILE_NAME, 0);
    }
    if (share != null) {
      return share.getString(name, DEFAULT_VALUE);
    }
    share = null;
    return DEFAULT_VALUE;
  }

  public static void setOnceValue(Context ctx,String fileName,String key,String value){
    if (share == null) {
      share = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    if (share != null) {
      share.edit().putString(key, value).commit();
    }
    share = null;
  }
}
