package com.cdd.utils;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 * Http 工具类
 * 
 * @author Xielei
 * 
 */
public final class HttpUtils {

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static Object get(String url,
			InputStreamParser<? extends Object> parser) {
		if (url == null) {
			return null;
		}
		Object reslut = null;
		InputStream inputStream = null;
		HttpClient httpclient = null;
		try {
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Connection", "Keep-Alive");
			httpclient = HttpClientUtils.getHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			reslut = parser.parser(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}
		return reslut;
	}

}
