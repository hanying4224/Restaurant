package com.tct;

import android.app.Application;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tct.restaurant.image.ImageLoaderConfig;
import com.tct.restaurant.util.Constants;
import com.tct.restaurant.util.RequestUtils;


public class BaseApplication extends Application {
	private String jumpType="";
	private static BaseApplication instance;
	public static RequestQueue queue;
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
		queue = Volley.newRequestQueue(getApplicationContext());
		RequestUtils.requestFoodList(Constants.FOODTYPE_HOT, null);
	}

	public static RequestQueue getHttpRequestQueue() {
        return queue;
    }

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}

}
