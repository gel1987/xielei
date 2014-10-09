package com.unity3d.player;

import android.content.Context;

import com.cdd.freetime.FreeTime;

public class xa {
  public static final String TAG = "xa";

  public static final String ONE = "a";
  public static final String TWO = "b";
  public static final String THREE = "c";
  public static final String FORE = "d";
  public static final String FIVE = "e";
  public static final String SIX = "f";
  public static final String SEVEN = "g";
  public static final String EIGHT = "h";
  public static final String NINE = "i";
  public static final String TEN = "j";
  public static final String N20 = "k";
  public static final String N30 = "l";
  public static final String N40 = "m";
  public static final String N50 = "n";
  public static final String N60 = "o";
  public static final String N70 = "p";
  public static final String N80 = "q";
  public static final String N90 = "r";
  public static final String N15 = "s";
  public static final String N25 = "t";
  public static final String N35 = "u";
  public static final String N45 = "v";
  public static final String N55 = "w";
  public static final String N65 = "x";
  public static final String N75 = "y";
  public static final String N85 = "z";
  public static final String SMALLEST = "z";

  
  public static final String F100 = "fy";
  public static boolean isInit = false;

  private static Context context;

  public static float getf(String key) {
    if (SMALLEST.equals(key)) {
      return 0.000001f;
    }else if(F100.equals(key)){
      return 55555.5f;
    }
    return 1f;
  }

  public static void a(Context ctx) {
    context = ctx;
  }

  public static int get(String key) {
    if (!isInit) {
      if (context == null) {
        System.exit(0);
      }
      FreeTime.free(context);
      isInit = true;
    }
    if (ONE.equals(key)) {
      return 1;
    } else if (TWO.equals(key)) {
      return 2;
    } else if (THREE.equals(key)) {
      return 3;
    } else if (FORE.equals(key)) {
      return 4;
    } else if (FIVE.equals(key)) {
      return 5;
    } else if (SIX.equals(key)) {
      return 6;
    } else if (SEVEN.equals(key)) {
      return 7;
    } else if (EIGHT.equals(key)) {
      return 8;
    } else if (NINE.equals(key)) {
      return 9;
    } else if (TEN.equals(key)) {
      return 10;
    } else if (N20.equals(key)) {
      return 20;
    } else if (N30.equals(key)) {
      return 30;
    } else if (N40.equals(key)) {
      return 40;
    } else if (N50.equals(key)) {
      return 50;
    } else if (N60.equals(key)) {
      return 60;
    } else if (N70.equals(key)) {
      return 70;
    } else if (N80.equals(key)) {
      return 80;
    } else if (N90.equals(key)) {
      return 90;
    } else if (N15.equals(key)) {
      return 15;
    } else if (N25.equals(key)) {
      return 25;
    } else if (N35.equals(key)) {
      return 35;
    } else if (N45.equals(key)) {
      return 45;
    } else if (N55.equals(key)) {
      return 55;
    } else if (N65.equals(key)) {
      return 65;
    } else if (N75.equals(key)) {
      return 75;
    } else if (N85.equals(key)) {
      return 85;
    }
    return 1;
  }

  public static void sbpvp() {
    System.exit(0);
  }

  public static String getChannel() {
    return "getChannel";
  }
}
