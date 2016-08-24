package com.tct.restaurant.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tct.restaurant.R;
import com.tct.restaurant.fragment.HomeFragment;
import com.tct.restaurant.fragment.MenuFragment;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;

public class HomePageActivity extends Activity implements OnClickListener{
	@InjectView(R.id.slidingpanellayout)
	private SlidingPaneLayout slidingPaneLayout;
	private MenuFragment menuFragment;
	private HomeFragment contentFragment;
	private TextView timeView;
	private View backView;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private int maxMargin = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(params);
//		View main = getLayoutInflater().from(this).inflate(R.layout.slidingpane_main_layout, null);
//		main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//		setContentView(main);
		setContentView(R.layout.slidingpane_main_layout);

		Injector.get(this).inject();//init views
		timeView = (TextView) findViewById(R.id.top_panel_time);
		new TimeThread().start();
		backView = findViewById(R.id.top_panel_back);
		backView.setOnClickListener(this);
		menuFragment = new MenuFragment();
		contentFragment = new HomeFragment();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingpane_menu, menuFragment);
		transaction.replace(R.id.slidingpane_content, contentFragment);
		transaction.commit();
		maxMargin = displayMetrics.heightPixels / 10;
		slidingPaneLayout.openPane();
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				// TODO Auto-generated method stub
//				int contentMargin = (int) (slideOffset * maxMargin);
//				FrameLayout.LayoutParams contentParams = contentFragment
//						.getCurrentViewParams();
//				contentParams.setMargins(0, contentMargin, 0, contentMargin);
//				contentFragment.setCurrentViewPararms(contentParams);
//
//				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
//						/ (float) displayMetrics.heightPixels;
//				Log.e("scale", scale + "sss" + slideOffset + "");
//				menuFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
//				menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
//				menuFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
//				menuFragment.getCurrentView().setPivotY(
//						displayMetrics.heightPixels / 2);
//				menuFragment.getCurrentView().setAlpha(slideOffset);
			}

			@Override
			public void onPanelOpened(View panel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelClosed(View panel) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * @return the slidingPaneLayout
	 */
	public SlidingPaneLayout getSlidingPaneLayout() {
		return slidingPaneLayout;
	}

	@Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.top_panel_back:
            Log.e("hao", "back key press");
            new Thread(){
                public void run() {
                    try{
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    }
                    catch (Exception e) {
                        Log.e("Exception when press home", e.toString());
                    }
                }
            }.start();
            break;

        default:
            break;
        }
    }

	private int TIMEUPDATE = 100;
	class TimeThread extends Thread{
	    @Override
	    public void run() {
	        // TODO Auto-generated method stub
	        do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = TIMEUPDATE;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
	}
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            if (msg.what == TIMEUPDATE) {
                long sysTime = System.currentTimeMillis();
                CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);
                timeView.setText(sysTimeStr);
            }
        }
    };

}
