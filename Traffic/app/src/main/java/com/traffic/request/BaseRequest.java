package com.traffic.request;

/**
 * 协议请求基类
 * 
 * @author asus
 */
public abstract class BaseRequest {
	/**
	 * 枚举类型的结果
	 * 
	 * @author asus
	 */

	public enum RequestResult {
		RESULT_FAIL, RESULT_SUCCESS, RESULT_NO_NET;
	}

	/**
	 * 回调接口，对象的传递
	 * 
	 * @author asus
	 */

	public interface OnResponseEventListener {
		void onResponse(BaseRequest request, RequestResult result);
	}

	// 存放服务器端的回应报文
	private String responseStr;
	// 请求结果的回调函数
	private OnResponseEventListener responseEventListener;

	/**
	 * 构造函数
	 * 
	 */
	public BaseRequest() {
	}

	/**
	 * 设置监听器
	 * 
	 * @param responseEventListener
	 *            监听接口
	 */
	public void setOnResponseEventListener(
			OnResponseEventListener responseEventListener) {
		this.responseEventListener = responseEventListener;
	}

	/**
	 * 获取请求body
	 * 
	 * @return 发送数据包
	 */
	public String getBody() {
		return onGetJasonBody();
	}

	/**
	 * 执行解析方法和请求结果的传递
	 * 
	 * @param result
	 *            请求结果
	 */
	public void parseResult(RequestResult result) {
		// 调用方法解析数据
		if (responseStr != null) {
			onJasonParese(responseStr);
		}
		// 将请求结果通知给调用者
		if (responseEventListener != null) {
			responseEventListener.onResponse(this, result);
		}
	}

	/**
	 * 设置服务器返回的数据
	 * 
	 * @param responseStr
	 *            服务器返回的数据
	 */
	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	/**
	 * 获取Action名称
	 * 
	 * @return Action名称
	 */
	protected abstract String getActionName();

	/**
	 * 获取向服务器发送的数据
	 * 
	 * @return 发送数据
	 */
	protected String onGetJasonBody() {
		return null;
	}

	/**
	 * 解析服务器返回数据
	 * 
	 * @param responseStr
	 *            服务器返回数据
	 */
	protected void onJasonParese(String responseStr) {
	}
}
