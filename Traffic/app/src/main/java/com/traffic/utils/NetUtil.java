package com.traffic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * 网络连接工具类
 * 
 * @author asus
 */

public class NetUtil {
	private static URL mUrl;
	private static HttpURLConnection mConnection;
	private static BufferedReader mReader = null;

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 *            上下文对象
	 * @return 网络是否连接
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean isNetworkOK = false;
		try {
			ConnectivityManager conn = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null == conn || null == conn.getActiveNetworkInfo()) {
				isNetworkOK = false;
			} else {
				isNetworkOK = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNetworkOK;
	}

	/**
	 * 发送数据
	 * 
	 * @param urlString
	 *            网络连接Url
	 * @param params
	 *            发送的数据
	 * @return 服务器返回的数据
	 */
	public static String sendData(String urlString, String params) {
		String result = "";
		try {
			createConnection(urlString);
			setParams();
			writeData(params);
			result = readData(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mReader != null) {
				try {
					mReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 读取数据
	 * 
	 * @param result
	 *            数据包
	 * @throws IOException
	 */
	private static String readData(String result) throws IOException {
		return  "";
	}

	/**
	 * 写出数据
	 * 
	 * @param params
	 *            将要写出的数据
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void writeData(String params) throws IOException,
			UnsupportedEncodingException {

	}

	/**
	 * 设置网络连接的参数
	 */
	private static void setParams() {

	}

	/**
	 * 创建网络连接
	 * 
	 * @param urlString
	 *            网络连接的Url
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void createConnection(String urlString)
			throws MalformedURLException, IOException {
	}
}
