package com.tapjoy.easyapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

	private static final String SP_NAME = "ad_sp_j";

	public static String getValue(Context ctx, String key) {
		SharedPreferences sp = ctx.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		String result = sp.getString(key, "");
		return result;
	}

	public static int getIntValue(Context ctx, String key) {
		SharedPreferences sp = ctx.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		int result = sp.getInt(key, 0);
		return result;
	}

	public static void saveValue(Context ctx, String key, String value) {
		SharedPreferences sp = ctx.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static void saveValue(Context ctx, String key, int value) {
		SharedPreferences sp = ctx.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
}
