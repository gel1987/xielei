package com.dex;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import dalvik.system.DexClassLoader;

@SuppressWarnings("rawtypes")
public class LockApplication extends Application {
  public static final String TAG = "LockApplication";

  private static final String APPLICATION_NAME = "APPLICATION_CLASS_NAME";
  private static final String ODEX_NAME = "odex";
  private static final String CACHE = "cache";
  private String dexPath;
  private String odexPath;
  private String libPath;

  private static boolean isRecopyDex = false;

  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    try {
      File odex = this.getDir(ODEX_NAME, MODE_PRIVATE);
      File libs = new File(odex.getParentFile().getAbsolutePath() + "/lib");
      File cache = this.getDir(CACHE, MODE_PRIVATE);
      odexPath = odex.getAbsolutePath();
      libPath = libs.getAbsolutePath();
      dexPath = cache.getAbsolutePath() + "/new.dex";
      File dexFile = new File(dexPath);
      if (!dexFile.exists() || isRecopyDex) {
        dexFile.createNewFile();
        DexUtil.lockDex2File(getAssets().open("sys.so"), new FileOutputStream(dexFile));
      }
      // 配置动态加载环境
      Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread",
          "currentActivityThread", new Class[] {}, new Object[] {});
      String packageName = this.getPackageName();
      HashMap mPackages = (HashMap) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread,
          "mPackages");
      WeakReference wr = (WeakReference) mPackages.get(packageName);
      DexClassLoader dLoader = new DexClassLoader(dexPath, odexPath, libPath, (ClassLoader) RefInvoke.getFieldOjbect(
          "android.app.LoadedApk", wr.get(), "mClassLoader"));
      RefInvoke.setFieldOjbect("android.app.LoadedApk", "mClassLoader", wr.get(), dLoader);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onCreate() {
    // 如果源应用配置有Appliction对象，则替换为源应用Applicaiton，以便不影响源程序逻辑。
    String applicationName = null;
    try {
      ApplicationInfo ai = this.getPackageManager().getApplicationInfo(this.getPackageName(),
          PackageManager.GET_META_DATA);
      Bundle bundle = ai.metaData;
      if (bundle != null && bundle.containsKey(APPLICATION_NAME)) {
        applicationName = bundle.getString(APPLICATION_NAME);
      } else {
        return;
      }
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }

    Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread",
        new Class[] {}, new Object[] {});
    Object mBoundApplication = RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread,
        "mBoundApplication");
    Object loadedApkInfo = RefInvoke
        .getFieldOjbect("android.app.ActivityThread$AppBindData", mBoundApplication, "info");
    RefInvoke.setFieldOjbect("android.app.LoadedApk", "mApplication", loadedApkInfo, null);
    Object oldApplication = RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread,
        "mInitialApplication");
    @SuppressWarnings("unchecked")
    ArrayList<Application> mAllApplications = (ArrayList<Application>) RefInvoke.getFieldOjbect(
        "android.app.ActivityThread", currentActivityThread, "mAllApplications");
    mAllApplications.remove(oldApplication);
    ApplicationInfo appinfo_In_LoadedApk = (ApplicationInfo) RefInvoke.getFieldOjbect("android.app.LoadedApk",
        loadedApkInfo, "mApplicationInfo");
    ApplicationInfo appinfo_In_AppBindData = (ApplicationInfo) RefInvoke.getFieldOjbect(
        "android.app.ActivityThread$AppBindData", mBoundApplication, "appInfo");
    appinfo_In_LoadedApk.className = applicationName;
    appinfo_In_AppBindData.className = applicationName;
    Application app = (Application) RefInvoke.invokeMethod("android.app.LoadedApk", "makeApplication", loadedApkInfo,
        new Class[] { boolean.class, Instrumentation.class }, new Object[] { false, null });
    RefInvoke.setFieldOjbect("android.app.ActivityThread", "mInitialApplication", currentActivityThread, app);

    HashMap mProviderMap = (HashMap) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread,
        "mProviderMap");
    Iterator it = mProviderMap.values().iterator();
    while (it.hasNext()) {
      Object providerClientRecord = it.next();
      Object localProvider = RefInvoke.getFieldOjbect("android.app.ActivityThread$ProviderClientRecord",
          providerClientRecord, "mLocalProvider");
      RefInvoke.setFieldOjbect("android.content.ContentProvider", "mContext", localProvider, app);
    }
    app.onCreate();
  }
}
