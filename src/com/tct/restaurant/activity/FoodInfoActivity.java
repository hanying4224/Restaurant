package com.tct.restaurant.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.tct.restaurant.R;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;
import com.tct.restaurant.util.RequestUtils;

public class FoodInfoActivity extends Activity {
	@InjectView(R.id.welcome_start_order)
	private TextView start;
	View v1;
	View v2;
	TextView mCommentNum;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.food_detail_layout);
		v1 = findViewById(R.id.main_right_comments);
		v2 = findViewById(R.id.main_right);
		v2.setVisibility(View.VISIBLE);
        v1.setVisibility(View.GONE);
        mCommentNum = (TextView) findViewById(R.id.food_comment_num);
        mCommentNum.setText(RequestUtils.foodList.get(2).getEvaluation());
//		TextView t = (TextView) findViewById(R.id.food_comment);
//		t.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                v1.setVisibility(View.VISIBLE);
//                v2.setVisibility(View.GONE);
//            }
//        });
	}


}
