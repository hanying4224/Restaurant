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

import com.tct.restaurant.R;
import com.tct.restaurant.adapter.RestaurantDetailAdapter;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;
import com.tct.restaurant.widget.stickylistheaders.StickyListHeadersListView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

@SuppressLint("NewApi")
public class OrderFragment extends Fragment implements AdapterView.OnItemClickListener,
        StickyListHeadersListView.OnHeaderClickListener,
        StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
        StickyListHeadersListView.OnStickyHeaderChangedListener {
	private View currentView;
	

	private RestaurantDetailAdapter mAdapter;
    private boolean fadeHeader = true;
    @InjectView(R.id.food_list_shipping_fee)
    private TextView order_cart;
    private StickyListHeadersListView stickyList;
    


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
//		currentView = inflater.inflate(R.layout.slidingpane_order_layout,
//				container, false);
	    currentView = inflater.inflate(R.layout.restaurant_detail_main,
              container, false);
	    Injector.get(getActivity()).inject();//init views
	    initView();
        setListener();
		return currentView;
	}
	
	private void initView() {
//      above_tittle.setText(restaurant_name);
//      head_left.setImageResource(R.drawable.abc_ic_ab_back_holo_dark);
        
    }
    
    private void setListener() {
        // TODO Auto-generated method stub
//      above_toHome.setOnClickListener(new OnClickListener() {
//          public void onClick(View v) {
//              finish();
//              
//          }
//      });
        
        mAdapter = new RestaurantDetailAdapter(getActivity(),order_cart);

        stickyList = (StickyListHeadersListView) currentView.findViewById(R.id.list_restaurant_detail);
        stickyList.setOnItemClickListener(this);
        stickyList.setOnHeaderClickListener(this);
        stickyList.setOnStickyHeaderChangedListener(this);
        stickyList.setOnStickyHeaderOffsetChangedListener(this);
//        stickyList.addHeaderView(getActivity().getLayoutInflater().inflate(
//                R.layout.restaurant_list_header, null));
//      stickyList.addFooterView(getLayoutInflater().inflate(
//              R.layout.restaurant_list_footer, null));
        stickyList.setDrawingListUnderStickyHeader(true);
        stickyList.setAreHeadersSticky(true);
        stickyList.setAdapter(mAdapter);

//      stickyList.setStickyHeaderTopOffset(-20);
        
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
//        Toast.makeText(this, "Item " + position + " clicked!",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header,
            int itemPosition, long headerId, boolean currentlySticky) {
//        Toast.makeText(this,
//                "Header " + headerId + " currentlySticky ? " + currentlySticky,
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l,
            View header, int offset) {
        if (fadeHeader
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onStickyHeaderChanged(StickyListHeadersListView l, View header,
            int itemPosition, long headerId) {
        header.setAlpha(1);
    }

}
