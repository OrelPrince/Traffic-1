package com.traffic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.traffic.request.BaseRequest;
import com.traffic.request.RequestThread;
import com.traffic.utils.Util;
import com.yuan.traffic.R;

/**
 * 程序启动的入口 提供全局的数据存储
 * 
 * @author asus
 */

public class ClientApp extends Application {
	private SharedPreferences mSharedPreferences;
	private String serverIpStr = "";
	private Handler mHandler;
	private AlertDialog mDialog;

	@Override
	public void onCreate() {
		super.onCreate();
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		handleMessage();
	}

	/**
	 * 接收消息进行处理
	 * 
	 */
	private void handleMessage() {
		mHandler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case RequestThread.MSG_REQUEST_RESULT:
					BaseRequest.RequestResult result = BaseRequest.RequestResult.values()[msg.arg1];
					RequestThread requestThread = (RequestThread) msg.obj;
					if (requestThread != null) {
						requestThread.hanlderResult(result);
					}
					if (result.equals(BaseRequest.RequestResult.RESULT_NO_NET)) {
						if (mDialog != null) {
							if (!mDialog.isShowing()) {
								mDialog.show();
							}
						}
					}
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 获取Ip地址
	 * 
	 * @return Ip地址
	 */
	public String getServerIpStr() {
		serverIpStr = readIpStr();
		if (serverIpStr.equals("")) {
			try {
				serverIpStr = Util.getLocalIpAddress(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serverIpStr;
	}

	/**
	 * 保存用户输入的Ip
	 * 
	 * @param serverIpStr
	 *            用户输入Ip
	 */
	public void setServerIpStr(String serverIpStr) {
		this.serverIpStr = serverIpStr;
		saveIpStr(this.serverIpStr);
	}

	/**
	 * 从配置文件中获取存入的Ip
	 * 
	 * @return Ip地址
	 */
	private String readIpStr() {
		return mSharedPreferences.getString("IpStr", "");
	}

	/**
	 * 将Ip地址存入配置文件中
	 * 
	 * @param ipStr
	 *            Ip地址
	 */
	private void saveIpStr(String ipStr) {
		Editor editor = mSharedPreferences.edit();
		editor.putString("IpStr", ipStr);
		editor.commit();
	}

	/**
	 * 获得Handler对象
	 * 
	 * @return Handler对象
	 */
	public Handler getHandler() {
		return mHandler;
	}

	/**
	 * 显示对话框
	 */
	private void showAlertDialog(Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(getString(R.string.prompt));
		builder.setMessage(getString(R.string.no_net_work));
		builder.setPositiveButton(activity.getString(android.R.string.ok), null);
		mDialog = builder.create();
		mDialog.show();
	}

	/**
	 * 给Client设置Activity
	 * 
	 * @param activity
	 *            Activity
	 */
	public void setActivity(Activity activity) {
		showAlertDialog(activity);
	}


}
