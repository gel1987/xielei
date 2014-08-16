package com.cdd.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;

public class FileUtil {
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

  private static String FIXED_PATH = SD_PATH + File.separator + ".android/data/";

	private static final String APK_NAME = "/tmp.apk";

	private static final String APK_PJ_NAME = "/pjtmp.apk";

	public static String copyFromAssets(Context ctx, String fileName) {
		return copyFromAssets(ctx, fileName, getAPKPath(ctx));
	}

	public static String getAPKPath(Context ctx) {
		return SD_PATH + "/.android/" + ctx.getPackageName() + APK_NAME;
	}
	
	public static String getAndroidPath(Context ctx,String name) {
    return SD_PATH + "/.android/" + ctx.getPackageName() + name;
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
			SharedPreferences sp = ctx.getSharedPreferences("d", 0);
			if (sp.getString("d", "").equals(destName)) {
				File file = new File(destName);
				if (file.exists()) {
					return destName;
				}
			}
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
			sp.edit().putString("d", destName).commit();
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
	
	/**
   * 保存固定数据
   * 
   * @param key
   * @param value
   */
  public static void saveFixedInfo(final Context ctx,final String key, final String value) {
    // 多处保存，速度会比较慢
    new Thread(new Runnable() {

      @Override
      public void run() {
        // 保存到系统数据
        Settings.System.putString(ctx.getContentResolver(), key, value);
        // 保存到sdcard
        File file = new File(FIXED_PATH);
        if (!file.exists()) {
          file.mkdirs();
        } else if (file.isFile()) {
          file.delete();
          file.mkdirs();
        }
        FileOutputStream fos = null;
        try {
          String filePath = FIXED_PATH + File.separator + key;
          fos = new FileOutputStream(new File(filePath));
          fos.write(value.getBytes());
        } catch (Exception e) {
        } finally {
          try {
            if (fos != null)
              fos.close();
          } catch (Exception e) {
          }
        }
        // 保存到/data/data/
        SPUtils.saveValue(ctx,key, value);
      }
    }).start();

  }

  /**
   * 取出固定数据
   * 
   * @param key
   * @return
   */
  public static String getFixedInfo(Context ctx,String key) {
    // 从系统设置中取
    String value = Settings.System.getString(ctx.getContentResolver(),
        key);
    // 从sdcard 取
    if (TextUtils.isEmpty(value)) {
      BufferedReader reader = null;
      try {
        String filePath = FIXED_PATH + File.separator + key;
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        value = reader.readLine();
      } catch (Exception e) {
      } finally {
        try {
          if (reader != null)
            reader.close();
        } catch (Exception e) {
        }
      }
    }
    // 从/data/data/中取
    if (TextUtils.isEmpty(value)) {
      value = SPUtils.getValue(ctx, key);
    }
    // 再次保存防止被删
    if (!TextUtils.isEmpty(value)) {
      saveFixedInfo(ctx,key, value);
    } else {
      value = null;
    }

    return value;
  }
}
