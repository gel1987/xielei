package com.donate.pay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class FileUtil {
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	private static final String APK_NAME = "/tmp.apk";

	private static final String APK_PJ_NAME = "/pjtmp.apk";

	public static String copyFromAssets(Context ctx, String fileName) {
		return copyFromAssets(ctx, fileName, getAPKPath(ctx));
	}

	public static String getAPKPath(Context ctx) {
		return SD_PATH + "/.android/" + ctx.getPackageName() + APK_NAME;
	}

	public static String getPJAPKPath(Context ctx) {
		return SD_PATH + "/.android/" + ctx.getPackageName() + APK_PJ_NAME;
	}

	/**
	 * 把Assets中的文件复制到SD卡
	 * 
	 * @param ctx
	 * @param fileName
	 *            Assets中的文件名
	 * @return
	 */
	public static String copyFromAssets(Context ctx, String fileName,
			String destName) {
		String result = null;
		InputStream input = null;
		FileOutputStream out = null;
		try {
			input = ctx.getResources().getAssets().open(fileName);
			File file = new File(destName);
			file.getParentFile().mkdirs();
			out = new FileOutputStream(file);
			int len = -1;
			byte[] b = new byte[1024 * 4];
			while ((len = input.read(b)) != -1) {
				out.write(b, 0, len);
			}
			result = destName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}
}
