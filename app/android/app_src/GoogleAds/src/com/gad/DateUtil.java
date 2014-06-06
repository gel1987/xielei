package com.gad;

import java.net.URL;
import java.net.URLConnection;

public class DateUtil {

	/**
	 * 获取网络时间
	 * 
	 * @param callback
	 */
	public static void getNetDate(final Callback callback) {
		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://www.baidu.com");// 取得资源对象
					URLConnection uc = url.openConnection();// 生成连接对象
					uc.connect(); // 发出连接
					long date = uc.getDate(); // 取得网站日期时间
					if (date > 0)
						callback.callback(date);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
