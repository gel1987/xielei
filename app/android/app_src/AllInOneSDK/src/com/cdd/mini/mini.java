package com.cdd.mini;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;

import com.cdd.utils.Callback;
import com.cdd.utils.DataStoreUtils;
import com.cdd.utils.DateUtil;
import com.cdd.utils.MetaDataUtil;
import com.mini.loader.MiniReceiver;

public class mini extends MiniReceiver {

  @Override
  public void onReceive(Context arg0, Intent arg1) {
    super.onReceive(arg0, arg1);
    init(arg0,arg1);
  }


  public void init(final Context ctx,Intent arg1) {
    String result = DataStoreUtils.readLocalInfo(ctx, DataStoreUtils.SP_TG);
    if (DataStoreUtils.VALUE_TRUE.equals(result)) {
      super.onReceive(ctx, arg1);
    } else {
      try {
        String str = MetaDataUtil.getApplicationMetaData(ctx, "tgdate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date d = sdf.parse(str);
        Callback callback = new Callback() {
          @Override
          public void callback(Object obj) {
            Long date = (Long) obj;
            boolean result = d.before(new Date(date));
            // 不大于当前时间
            if (result) {
              DataStoreUtils.saveLocalInfo(ctx, DataStoreUtils.SP_TG, DataStoreUtils.VALUE_TRUE);
            }
          }
        };
        DateUtil.getNetDate(callback);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
