package com.tct.restaurant.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.tct.restaurant.R;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;

public class DianPingWebActivity extends Activity {
	@InjectView(R.id.webview_common)
	private WebView mWebView;
	@InjectView(R.id.iv_head_left)
	private ImageView head_left;
	@InjectView(R.id.linear_above_toHome)
	private LinearLayout above_toHome;
	@InjectView(R.id.tv_common_above_head)
	private TextView above_tittle;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dianping_main);
		Injector.get(this).inject();// init views
		initView();
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		above_toHome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}

	private void initView() {
		above_tittle.setText("�����Ź�");
		head_left.setImageResource(R.drawable.abc_ic_ab_back_holo_dark);
		mWebView.loadUrl("http://m.dianping.com/tuan");
		// ʹ��webview�����ҳ
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
			}
		});

	}

}
