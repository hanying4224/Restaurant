package com.tct.restaurant.util;

import android.os.Environment;


public class Constants {
	/**
	 ******************************************* 参数设置信息******************************************
	 */

	// 应用名称
	public static String APP_NAME = "";

	// 保存参数文件夹名�?
	public static final String SHARED_PREFERENCE_NAME = "ele_prefs";

	// SDCard路径
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	// 图片存储路径
	public static final String BASE_PATH = SD_PATH + "/ele/";

	// 缓存图片路径
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";

	//HTTP 相关
	private static String IP = "http://192.168.1.202";//10.128.210.67
	public static final String SERVER_IP = IP + "/restaurant/Service1.asmx";
	public static final String PIC_IP = IP + "/restaurant/pic/";
	public static final String TABLE_QUERY = "/tableQuery";
	public static final String NOEN_QUERY = "/noneQuery";

	//Food Type
	public static final String FOODTYPE_SPECIAL = "招牌类";
	public static final String FOODTYPE_HOT = "热菜类";
	public static final String FOODTYPE_COLD = "凉菜类";
	public static final String FOODTYPE_SWEET = "甜点类";
	public static final String FOODTYPE_SOUP = "汤类";
	public static final String FOODTYPE_DRINKS = "饮料类";
	public static final String FOODTYPE_STAPLE = "主食类";

	//User info
	public static final String USER_ID = "1";

	//Order status
	public static final String STATUS_HANDING = "0";
	public static final String STATUS_NORMAL = "1";
	public static final String STATUS_COMPLETE = "2";
	public static final String STATUS_UNORDER = "3";

	/**
	 ******************************************* 参数设置信息结束 ******************************************
	 */
}
