package com.tapjoy;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class MetaDataUtil {

  public static String getApplicationMetaData(Context ctx, String key, String defaulValue) {
    String value = getApplicationMetaData(ctx, key);
    if (TextUtils.isEmpty(value)) {
      return defaulValue;
    } else {
      return value;
    }
  }

  /**
   * 获取Application MetaData
   * 
   * @param ctx
   * @param key
   * @return
   */
  public static String getApplicationMetaData(Context ctx, String key) {
    try {
      PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
          PackageManager.GET_META_DATA);
      return packageInfo.applicationInfo.metaData.get(key).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
