/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneContentFragment.java 
 * @Prject: SlidingPane
 * @Package: com.tct.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:44:01 
 * @version: V1.0   
 */
package com.tct.restaurant.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.tct.restaurant.R;

@SuppressLint("NewApi")
public class PayFragment extends Fragment implements OnClickListener{
	private View currentView;
	private View payErweimaLayout;
	private View payTuanLayout;
	private TextView payErweimaTextView;
	private TextView payTuanTextView;
	

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.slidingpane_pay_layout,
				container, false);
		payErweimaLayout = currentView.findViewById(R.id.pay_erweima_layout);
		payTuanLayout = currentView.findViewById(R.id.pay_tuan_layout);
		payErweimaTextView = (TextView) currentView.findViewById(R.id.pay_erweima);
		payErweimaTextView.setOnClickListener(this);
		payTuanTextView = (TextView) currentView.findViewById(R.id.pay_tuan);
		payTuanTextView.setOnClickListener(this);
		
		return currentView;
	}

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.pay_erweima:
            payErweimaLayout.setVisibility(View.VISIBLE);
            payTuanLayout.setVisibility(View.GONE);
            break;
        case R.id.pay_tuan:
            payErweimaLayout.setVisibility(View.GONE);
            payTuanLayout.setVisibility(View.VISIBLE);
            break;
        default:
            break;
        }
    }

}
