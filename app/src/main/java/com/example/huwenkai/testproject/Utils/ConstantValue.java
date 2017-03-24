package com.example.huwenkai.testproject.Utils;

/**
 *
 */
public class ConstantValue {

	public static final String SOCKETURL = "klytech.cn";
	/**
	 * 公司域名
	 */
	public static final String URL = "http://139.196.230.65:903/";
	/**
	* 登录Url
	*/
	public static final String LoginURL = "Waiter/Public/login";

	/**
	 * 菜品URL
	 */
	public static final String FoodURL = "Waiter/Food/serving_list";

	/**
	 * 登录保留的token
	 */
	public static final String TOKEN = "TOKEN";
	/**
	 * 发送上菜状态URl
	 */
	public static final String served_food = "Waiter/Food/served_food";

	/**
	 *  当前工作状况，null表示正在处理，success表示处理成功，failure表示处理失败
	 */
	public static final String TAG_NULL = "null";
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_FAILURE = "failure";

	public static final String ID = "id";
	public static final String ACTION = "com.huwenkai.activity.CountService";
	/**
	 * 震动的开启key
	 */
	public static final String ISOPENVIB ="isOpenVib" ;
	public static final String SENDMESSAGE = "sendmessage";
	/**
	 * 服务器发来的socket消息
	 */
	public static final String WebSocket = "wesocket";

	/**
	 * 响铃的开启key
	 */
	public static final String ISOPENVol = "isOpenVol";
	public static final String ISAUTOLOGIN ="ischeck" ;
}
