package com.example.applyym;

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

	public static SharedPreferences share;

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

}
