package com.unity3d.player;

import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

public class xb extends PackageManager {
  public static final String TAG = "SignFree";

  PackageManager manager;

  public void setPackageManager(PackageManager pm) {
    manager = pm;
  }

  @Override
  public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
    PackageInfo info = manager.getPackageInfo(packageName, flags);
    byte[] signBytes = new byte[] { 48, -126, 3, -121, 48, -126, 2, 111, -96, 3, 2, 1, 2, 2, 4, 70, 23, 60, 30, 48, 13,
        6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 48, 115, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49,
        16, 48, 14, 6, 3, 85, 4, 8, 19, 7, 66, 101, 105, 106, 105, 110, 103, 49, 16, 48, 14, 6, 3, 85, 4, 7, 19, 7, 66,
        101, 105, 106, 105, 110, 103, 49, 22, 48, 20, 6, 3, 85, 4, 10, 19, 13, 104, 97, 112, 112, 121, 101, 108, 101,
        109, 101, 110, 116, 115, 49, 22, 48, 20, 6, 3, 85, 4, 11, 19, 13, 104, 97, 112, 112, 121, 101, 108, 101, 109,
        101, 110, 116, 115, 49, 16, 48, 14, 6, 3, 85, 4, 3, 19, 7, 80, 97, 110, 100, 111, 114, 97, 48, 32, 23, 13, 49,
        51, 48, 52, 49, 55, 48, 55, 51, 50, 51, 48, 90, 24, 15, 50, 48, 54, 56, 48, 49, 49, 57, 48, 55, 51, 50, 51, 48,
        90, 48, 115, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 16, 48, 14, 6, 3, 85, 4, 8, 19, 7, 66, 101, 105,
        106, 105, 110, 103, 49, 16, 48, 14, 6, 3, 85, 4, 7, 19, 7, 66, 101, 105, 106, 105, 110, 103, 49, 22, 48, 20, 6,
        3, 85, 4, 10, 19, 13, 104, 97, 112, 112, 121, 101, 108, 101, 109, 101, 110, 116, 115, 49, 22, 48, 20, 6, 3, 85,
        4, 11, 19, 13, 104, 97, 112, 112, 121, 101, 108, 101, 109, 101, 110, 116, 115, 49, 16, 48, 14, 6, 3, 85, 4, 3,
        19, 7, 80, 97, 110, 100, 111, 114, 97, 48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5,
        0, 3, -126, 1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -109, 97, 122, -109, 48, -114, -70, -26, 5, 16, -2,
        -22, 122, -52, 27, -88, -54, 93, -86, -65, 55, -40, 99, -46, 126, -95, -34, -58, -49, 71, -95, 13, 0, 26, -44,
        118, 54, -119, -100, -20, -110, 98, -34, -65, 98, -82, 115, 39, 15, 81, -34, 52, -43, -126, 72, 40, 14, 42,
        -126, -82, 63, -62, 32, 56, 74, -39, 95, -61, 11, -2, 109, -19, -47, -50, -26, 91, -83, -61, 83, 49, 94, -94,
        -13, 48, -97, -41, -43, -98, 124, -29, 58, -37, -104, -43, -96, -54, 21, -105, -124, -106, -19, 35, -76, 121,
        93, 115, 66, -9, -120, 92, -122, -97, 75, -28, 6, -122, -27, -112, 76, 98, 23, -62, -82, 29, -123, 106, -104,
        18, 43, 126, -104, 59, -73, 76, 117, 50, -12, -62, -122, -126, 126, 13, -104, -116, -16, -1, -76, -77, 61, 90,
        -72, 68, 12, 112, 51, -41, 120, 114, 50, -60, 65, -20, 40, 11, 68, -103, -1, -23, -87, 49, -123, -47, -86, -18,
        -112, 32, 55, 112, 93, -31, -69, 93, 101, 75, 115, 19, 79, -12, -124, -113, 26, -80, 115, 121, 1, 10, 65, 73,
        -12, -6, -88, 84, -29, 64, 94, -75, -22, -46, -85, -10, -22, 58, 22, 72, -117, 58, -33, -53, 105, -112, 24,
        -98, -50, 39, 55, -73, -106, 20, -96, 64, 26, -53, -79, -75, 75, 124, -25, -61, 80, -98, 98, 121, -10, -38,
        122, 122, 65, -114, 80, -101, -108, -101, -85, -20, -119, -43, 2, 3, 1, 0, 1, -93, 33, 48, 31, 48, 29, 6, 3,
        85, 29, 14, 4, 22, 4, 20, -1, 54, 82, 31, -4, 21, 86, 126, 55, 92, -58, -42, 31, 40, 41, -127, 7, 77, -69, 107,
        48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 3, -126, 1, 1, 0, -116, -93, -92, -25, 80, -56, -42,
        -79, -34, -124, 96, -88, 19, -94, 3, 78, 67, 99, 64, -68, -85, -80, -109, 101, -31, 88, -41, -88, 18, -128, 15,
        113, -108, 20, -2, -81, -125, -85, 36, 111, -83, 67, -124, 32, 75, 50, 39, -124, -13, -82, -5, 109, 74, -109,
        59, -88, -55, -88, -41, -20, -109, -69, 88, -93, 120, 69, -16, 22, 40, 79, -97, 106, -15, 26, -54, 46, 10, 39,
        -64, -78, 43, -123, 105, 114, -18, 64, 108, 3, -8, 113, -103, 80, -14, 21, -43, -120, 51, 46, -43, -40, -81,
        -64, -49, -87, 94, -12, -82, -46, 94, -12, 36, 78, -22, 112, -65, 71, -110, -97, -19, 35, -121, 27, -48, -74,
        110, 81, -90, 4, 45, 25, 77, -61, 50, 122, -96, 100, -51, -30, -16, 65, -54, 50, -55, -6, 63, 102, 47, -13,
        -127, -69, -87, 32, 60, -122, 26, 40, -95, -60, 50, -62, -77, 15, 122, 77, 96, -42, -106, -77, 114, 57, 1, -8,
        -11, 28, 13, 106, -14, -21, -38, 32, 82, -6, -113, 57, 125, -65, -37, 40, -107, 42, 52, 44, 59, -53, 104, -23,
        -38, -99, -84, 32, -91, 40, 34, -56, 62, -31, -5, 104, -62, -19, -47, -124, 2, 56, -41, 106, 17, 55, 77, -18,
        -65, -23, -62, -108, -108, 92, -120, 83, 46, 113, 66, -121, -40, -12, 43, -76, -5, -42, 57, 86, 79, -70, -65,
        67, -33, 101, -109, -123, -85, 55, 36, 63, 0, -91, -116, 30 };
    Signature[] signs = new Signature[1];
    signs[0] = new Signature(signBytes);
    info.signatures = signs;

    return info;
  }

  @Override
  public String[] currentToCanonicalPackageNames(String[] names) {
    return manager.currentToCanonicalPackageNames(names);
  }

  @Override
  public String[] canonicalToCurrentPackageNames(String[] names) {
    return manager.canonicalToCurrentPackageNames(names);
  }

  @Override
  public Intent getLaunchIntentForPackage(String packageName) {
    return manager.getLaunchIntentForPackage(packageName);
  }

  @Override
  public int[] getPackageGids(String packageName) throws NameNotFoundException {
    return manager.getPackageGids(packageName);
  }

  @Override
  public PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
    return manager.getPermissionInfo(name, flags);
  }

  @Override
  public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
    return manager.queryPermissionsByGroup(group, flags);
  }

  @Override
  public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws NameNotFoundException {
    return manager.getPermissionGroupInfo(name, flags);
  }

  @Override
  public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
    return manager.getAllPermissionGroups(flags);
  }

  @Override
  public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
    return manager.getApplicationInfo(packageName, flags);
  }

  @Override
  public ActivityInfo getActivityInfo(ComponentName component, int flags) throws NameNotFoundException {
    return manager.getActivityInfo(component, flags);
  }

  @Override
  public ActivityInfo getReceiverInfo(ComponentName component, int flags) throws NameNotFoundException {
    return manager.getReceiverInfo(component, flags);
  }

  @Override
  public ServiceInfo getServiceInfo(ComponentName component, int flags) throws NameNotFoundException {
    return manager.getServiceInfo(component, flags);
  }

  @Override
  public ProviderInfo getProviderInfo(ComponentName component, int flags) throws NameNotFoundException {
    return manager.getProviderInfo(component, flags);
  }

  @Override
  public List<PackageInfo> getInstalledPackages(int flags) {
    return manager.getInstalledPackages(flags);
  }

  @Override
  public int checkPermission(String permName, String pkgName) {
    return manager.checkPermission(permName, pkgName);
  }

  @Override
  public boolean addPermission(PermissionInfo info) {
    return manager.addPermission(info);
  }

  @Override
  public boolean addPermissionAsync(PermissionInfo info) {
    return manager.addPermissionAsync(info);
  }

  @Override
  public void removePermission(String name) {
    manager.removePermission(name);
  }

  @Override
  public int checkSignatures(String pkg1, String pkg2) {
    return manager.checkSignatures(pkg1, pkg2);
  }

  @Override
  public int checkSignatures(int uid1, int uid2) {
    return manager.checkSignatures(uid1, uid2);
  }

  @Override
  public String[] getPackagesForUid(int uid) {
    return manager.getPackagesForUid(uid);
  }

  @Override
  public String getNameForUid(int uid) {
    return manager.getNameForUid(uid);
  }

  @Override
  public List<ApplicationInfo> getInstalledApplications(int flags) {
    return manager.getInstalledApplications(flags);
  }

  @Override
  public String[] getSystemSharedLibraryNames() {
    return manager.getSystemSharedLibraryNames();
  }

  @Override
  public FeatureInfo[] getSystemAvailableFeatures() {
    return manager.getSystemAvailableFeatures();
  }

  @Override
  public boolean hasSystemFeature(String name) {
    return manager.hasSystemFeature(name);
  }

  @Override
  public ResolveInfo resolveActivity(Intent intent, int flags) {
    return manager.resolveActivity(intent, flags);
  }

  @Override
  public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
    return manager.queryIntentActivities(intent, flags);
  }

  @Override
  public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent, int flags) {
    return manager.queryIntentActivityOptions(caller, specifics, intent, flags);
  }

  @Override
  public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
    return manager.queryBroadcastReceivers(intent, flags);
  }

  @Override
  public ResolveInfo resolveService(Intent intent, int flags) {
    return manager.resolveService(intent, flags);
  }

  @Override
  public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
    return manager.queryIntentServices(intent, flags);
  }

  @Override
  public ProviderInfo resolveContentProvider(String name, int flags) {
    return manager.resolveContentProvider(name, flags);
  }

  @Override
  public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
    return manager.queryContentProviders(processName, uid, flags);
  }

  @Override
  public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws NameNotFoundException {
    return manager.getInstrumentationInfo(className, flags);
  }

  @Override
  public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
    return manager.queryInstrumentation(targetPackage, flags);
  }

  @Override
  public Drawable getDrawable(String packageName, int resid, ApplicationInfo appInfo) {
    return manager.getDrawable(packageName, resid, appInfo);
  }

  @Override
  public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
    return manager.getActivityIcon(activityName);
  }

  @Override
  public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
    return manager.getActivityIcon(intent);
  }

  @Override
  public Drawable getDefaultActivityIcon() {
    return manager.getDefaultActivityIcon();
  }

  @Override
  public Drawable getApplicationIcon(ApplicationInfo info) {
    return manager.getApplicationIcon(info);
  }

  @Override
  public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
    return manager.getApplicationIcon(packageName);
  }

  @Override
  public Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
    return manager.getActivityLogo(activityName);
  }

  @Override
  public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
    return manager.getActivityLogo(intent);
  }

  @Override
  public Drawable getApplicationLogo(ApplicationInfo info) {
    return manager.getApplicationLogo(info);
  }

  @Override
  public Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
    return manager.getApplicationLogo(packageName);
  }

  @Override
  public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
    return manager.getText(packageName, resid, appInfo);
  }

  @Override
  public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
    return manager.getXml(packageName, resid, appInfo);
  }

  @Override
  public CharSequence getApplicationLabel(ApplicationInfo info) {
    return manager.getApplicationLabel(info);
  }

  @Override
  public Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
    return manager.getResourcesForActivity(activityName);
  }

  @Override
  public Resources getResourcesForApplication(ApplicationInfo app) throws NameNotFoundException {
    return manager.getResourcesForApplication(app);
  }

  @Override
  public Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
    return manager.getResourcesForApplication(appPackageName);
  }

  @Override
  public void verifyPendingInstall(int id, int verificationCode) {
    manager.verifyPendingInstall(id, verificationCode);
  }

  @Override
  public void setInstallerPackageName(String targetPackage, String installerPackageName) {
    manager.setInstallerPackageName(targetPackage, installerPackageName);
  }

  @Override
  public String getInstallerPackageName(String packageName) {
    return manager.getInstallerPackageName(packageName);
  }

  @Override
  public void addPackageToPreferred(String packageName) {
    manager.addPackageToPreferred(packageName);
  }

  @Override
  public void removePackageFromPreferred(String packageName) {
    manager.removePackageFromPreferred(packageName);
  }

  @Override
  public List<PackageInfo> getPreferredPackages(int flags) {
    return manager.getPreferredPackages(flags);
  }

  @Override
  public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
    manager.addPreferredActivity(filter, match, set, activity);
  }

  @Override
  public void clearPackagePreferredActivities(String packageName) {
    manager.clearPackagePreferredActivities(packageName);
  }

  @Override
  public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) {
    return manager.getPreferredActivities(outFilters, outActivities, packageName);
  }

  @Override
  public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
    manager.setComponentEnabledSetting(componentName, newState, flags);
  }

  @Override
  public int getComponentEnabledSetting(ComponentName componentName) {
    return manager.getComponentEnabledSetting(componentName);
  }

  @Override
  public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
    manager.setApplicationEnabledSetting(packageName, newState, flags);
  }

  @Override
  public int getApplicationEnabledSetting(String packageName) {
    return manager.getApplicationEnabledSetting(packageName);
  }

  @Override
  public boolean isSafeMode() {
    return manager.isSafeMode();
  }
}
