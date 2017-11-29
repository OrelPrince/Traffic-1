package com.traffic.utils;

import java.net.UnknownHostException;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 本机Ip获取工具类
 * 
 * @author asus
 */

public class Util {
	/**
	 * 获取本地Ip
	 * 
	 * @param context
	 *            上下问对象
	 * @return Ip地址
	 * @throws UnknownHostException
	 */
	public static String getLocalIpAddress(Context context)
			throws UnknownHostException {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String IP;
		int ipAddress = wifiInfo.getIpAddress();
		IP = intToIp(ipAddress);
		return IP;
	}

	/**
	 * 转化Ip
	 * 
	 * @param i
	 *            Ip二进制串
	 * @return 转化后的Ip字符串
	 */
	public static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + ((i >> 24) & 0xFF);
	}
}
