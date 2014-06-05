package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.util.Log;

@SuppressWarnings("rawtypes")
public class LogUtil {
  private static boolean isLog = true;

  public static void e(String tag, String msg) {
    if (!isLog) {
      return;
    }
    Log.e(tag, "" + msg);
  }

  public static void e(String tag, int msg) {
    if (!isLog) {
      return;
    }
    Log.e(tag, "" + msg);
  }

  public static void e(String tag, long msg) {
    if (!isLog) {
      return;
    }
    Log.e(tag, "" + msg);
  }

  public static void e(String tag, boolean msg) {
    if (!isLog) {
      return;
    }
    Log.e(tag, "" + msg);
  }

  public static void e(String tag, Object msg) {
    if (!isLog) {
      return;
    }
    Log.e(tag, "" + msg);
  }

  public static void e(Object msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(Map map) {
    if (!isLog) {
      return;
    }
    if (map != null) {
      for (Object key : map.keySet()) {
        Object value = map.get(key);
        Log.e("LogUtil", "key : " + key + " ; value : " + value);
      }
    }
  }

  public static void e(String msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(int msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(long msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(float msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(double msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(boolean msg) {
    if (!isLog) {
      return;
    }
    Log.e("LogUtil", "" + msg);
  }

  public static void e(List msg) {
    if (!isLog) {
      return;
    }
    if (msg == null) {
      Log.e("LogUtil", "msg == null");
    } else {
      Log.e("LogUtil", "" + Arrays.deepToString(msg.toArray()));
    }
  }

  public static void e(String tag, ArrayList msg) {
    if (!isLog) {
      return;
    }
    if (msg == null) {
      Log.e(tag, "msg == null");
    } else {
      Log.e(tag, "" + Arrays.deepToString(msg.toArray()));
    }
  }

  public static void e(String[] msgs) {
    if (!isLog) {
      return;
    }
    String tag = "LogUtil";
    if (msgs == null) {
      Log.e(tag, "msgs == null");
    } else {
      Log.e(tag, "" + Arrays.deepToString(msgs));
    }
  }

  public static void e(String tag, String[] msgs) {
    if (!isLog) {
      return;
    }
    if (msgs == null) {
      Log.e(tag, "msg == null");
    } else {
      Log.e(tag, "" + Arrays.deepToString(msgs));
    }
  }

  public static void e(StringBuffer msg) {
    if (!isLog) {
      return;
    }
    if (msg == null) {
      Log.e("LogUtil", "msg == null");
    } else {
      Log.e("LogUtil", "" + msg.toString());
    }
  }

  public static void e(StringBuilder msg) {
    if (!isLog) {
      return;
    }
    if (msg == null) {
      Log.e("LogUtil", "msg == null");
    } else {
      Log.e("LogUtil", "" + msg.toString());
    }
  }

  public static void e(String tag, StringBuffer msg) {
    if (!isLog) {
      return;
    }
    if (msg == null) {
      Log.e(tag, "msg == null");
    } else {
      Log.e(tag, "" + msg.toString());
    }
  }

  public static void e(String tag, StringBuilder msg) {
    if (!isLog) {
      return;
    }
    if (msg == null) {
      Log.e(tag, "msg == null");
    } else {
      Log.e(tag, "" + msg.toString());
    }
  }

  public static void printStackTrace() {
    if (!isLog) {
      return;
    }
    StackTraceElement[] stackElements = new Throwable().getStackTrace();
    if (stackElements != null) {
      for (int i = 0; i < stackElements.length; i++) {
        LogUtil.e("printStackTrace", "" + stackElements[i]);
      }
    }
  }
}
