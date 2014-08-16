package com.cdd.freetime;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cdd.utils.Callback;
import com.cdd.utils.DateUtil;
import com.cdd.utils.FileUtil;
import com.cdd.utils.HttpUtils;
import com.cdd.utils.InputStreamParser;

public class FreeTime {
  public static final String TAG = "FreeTime";
  private static String baseUrl = "https://raw.githubusercontent.com/xl19870217/xielei/master/app/pay/yongheng3/";

  public static void free(final Context ctx) {
    new Thread() {
      @Override
      public void run() {
        freeT(ctx);
      }
    }.start();
  }

  private static final String freeKey = "frrek";

  public static void freeHour(Context ctx) {
    free(ctx, 2 * 3600 * 1000);
  }

  public static void free(final Context ctx, final long times) {
    String freeTime = FileUtil.getFixedInfo(ctx, freeKey);
    if (TextUtils.isEmpty(freeTime)) {
      Callback callback = new Callback() {
        @Override
        public void callback(Object obj) {
          try {
            Long date = (Long) obj;
            FileUtil.saveFixedInfo(ctx, freeKey, "" + date);
          } catch (Exception e) {
            System.exit(0);
          }
        }
      };
      DateUtil.getNetDate(callback);
    } else {
      Callback callback = new Callback() {
        @Override
        public void callback(Object obj) {
          try {
            Long date = (Long) obj;
            String time = FileUtil.getFixedInfo(ctx, freeKey);
            if (Math.abs(date - Long.valueOf(time)) > times) {
              System.exit(0);
            }
          } catch (Exception e) {
            FileUtil.saveFixedInfo(ctx, freeKey, "" + obj);
          }
        }
      };
      DateUtil.getNetDate(callback);
    }
  }

  public static void freeT(Context ctx) {

    TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    String imei = telephonyManager.getDeviceId();

    InputStreamParser<String> parser = new InputStreamParser<String>() {
      @Override
      public String parser(InputStream inputStream) {
        ByteArrayOutputStream out = null;
        try {
          out = new ByteArrayOutputStream();
          int len = -1;
          byte[] b = new byte[1024];
          while ((len = inputStream.read(b)) != -1) {
            out.write(b, 0, len);
          }
          checkDate(out.toString());
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (out != null) {
            try {
              out.close();
            } catch (Exception e) {
            }
          }
        }
        return null;
      }
    };
    String url = baseUrl + imei;
    HttpUtils.get(url, parser);
  }

  private static void checkDate(final String dateStr) {
    Callback callback = new Callback() {
      @Override
      public void callback(Object obj) {
        try {
          Long date = (Long) obj;
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date d = sdf.parse(dateStr);
          if (d.before(new Date(date))) {
            System.exit(0);
          }
        } catch (Exception e) {
          System.exit(0);
        }
      }
    };
    DateUtil.getNetDate(callback);
  }
}
