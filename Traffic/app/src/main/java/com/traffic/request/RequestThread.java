package com.traffic.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.traffic.ClientApp;
import com.traffic.utils.NetUtil;

/**
 * 网络连接线程类
 * 
 */

public class RequestThread extends Thread {
	// 消息id,请求完成以后，需要将此id发送给ui主线程处理
	public static final int MSG_REQUEST_RESULT = 0x10;
	// ui主线程的Handler
	private Handler mHandler;
	// app上下文
	private Context mContext;
	// 请求实体对象
	private BaseRequest mRequest;
	// app对象
	private ClientApp mApp;
	// 线程是否已经被取消的控制变量
	private volatile boolean mCancel = false;
	// 线程是否应该循环执行的控制变量
	private volatile boolean isLoop = false;
	// 循环执行的时间间隔
	private volatile int mLoopPeriod = 1000;// 默认为1秒
	// 线程是否已经被暂停的控制变量
	private volatile boolean isPause = false;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文对象
	 * @param handler
	 *            消息出来对象
	 */
	public RequestThread(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
		mApp = (ClientApp) context.getApplicationContext();
	}

	/**
	 * 设置请求对象
	 * 
	 * @param mRequest
	 *            请求基类
	 */
	public void setRequest(BaseRequest mRequest) {
		this.mRequest = mRequest;
	}

	/**
	 * 停止线程
	 */
	public void stopRequestThread() {
		mCancel = true;
		isLoop = false;
}

	/**
	 * 判断线程是否已经停止
	 * 
	 * @return 是否停止
	 */
	public boolean isCancel() {
		return mCancel;
	}

	@Override
	public void run() {
		super.run();
		do {
			if (!isPause) {
				BaseRequest.RequestResult result = BaseRequest.RequestResult.RESULT_FAIL;
				// 先判断网络状态
				if (NetUtil.isNetworkAvailable(mContext)) {
					try {
						if (mRequest != null && mApp != null) {
							// 获取action名称
							String actionName = mRequest.getActionName();
							// 获取请求body
							String requestBody = mRequest.getBody();
							if (actionName != null && requestBody != null) {
								// url组装
								String url = "http://" + mApp.getServerIpStr()
										+ ":8080";
								url += "/transportservice/type/jason";
								url += "/action/" + actionName;
								String response = "";
								response = NetUtil.sendData(url, requestBody);
								// 将服务器端回应的结果保存到请求对象中
								mRequest.setResponseStr(response);
								result = BaseRequest.RequestResult.RESULT_SUCCESS;
							}
						}
					} catch (Exception e) {
						result = BaseRequest.RequestResult.RESULT_FAIL;
						e.printStackTrace();
					}
				} else {
					result = BaseRequest.RequestResult.RESULT_NO_NET;
				}

				// 请求完成以后，将此结果发送给ui主线程处理
				if (!mCancel && mHandler != null) {
					Message msg = new Message();
					msg.what = MSG_REQUEST_RESULT;
					msg.obj = this;
					msg.arg1 = result.ordinal();
					mHandler.sendMessage(msg);
				}

				// 循环执行操作下延时
				if (isLoop) {
					try {
						Thread.sleep(mLoopPeriod);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				// 如果线程被暂停，则线程延时
				try {
					Thread.sleep(mLoopPeriod);
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} while (isLoop);
	}

	/**
	 * 该函数只能由ui主线程来调用，其他线程不能调用，目的是让ui主线程处理请求结果，以便界面刷新
	 * 
	 * @param result
	 *            请求结果
	 */
	public void hanlderResult(BaseRequest.RequestResult result) {
		if (!mCancel && mRequest != null) {
			mRequest.parseResult(result);
		}
	}

	/**
	 * 暂停线程
	 */
	public void pause() {
		isPause = true;
	}

	/**
	 * 重启线程
	 */
	public void restart() {
		isPause = false;
	}

	/**
	 * 设置线程循环执行
	 * 
	 * @param isLoop
	 *            是否循环
	 * @param loopPeriod
	 *            循环周期
	 */
	public void setLoop(boolean isLoop, int loopPeriod) {
		this.isLoop = isLoop;
		this.mLoopPeriod = loopPeriod;
	}
}
