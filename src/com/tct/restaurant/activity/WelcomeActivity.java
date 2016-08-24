package com.tct.restaurant.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.tct.restaurant.R;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;

public class WelcomeActivity extends Activity {
	@InjectView(R.id.start)
	private TextView start;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//full screen
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(params);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		Injector.get(this).inject();//init views
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (WelcomeActivity.this,HomePageActivity.class);			
				startActivity(intent);			
				finish();
			}
		});

	}


}
