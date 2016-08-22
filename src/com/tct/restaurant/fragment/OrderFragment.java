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

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.entity.OrderItem;
import com.tct.restaurant.util.Constants;
import com.tct.restaurant.util.RequestUtils;

@SuppressLint("NewApi")
public class OrderFragment extends Fragment implements AdapterView.OnItemClickListener, OnClickListener{
	private View currentView;
	private ListView listView;
	private OrderAdapter adapter;
	private Context mContext;
	private TextView orderedTV, unorderedTV;
	/** 0:已下单列表。 1:待下单列表。 **/
	int current_tag = 1;
	private LinearLayout bottom1Layout;
	private LinearLayout bottom2Layout;

	ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();
	ArrayList<OrderItem> unorderList = new ArrayList<OrderItem>();
	
	Handler mHandler = new Handler(){
	    public void handleMessage(android.os.Message msg) {
	        if (msg.what == RequestUtils.REQUEST_USERORDER_OK) {
	            orderList.clear();
	            orderList.addAll(RequestUtils.userOrderList);
	            adapter.notifyDataSetChanged();
            } else if (msg.what == RequestUtils.REQUEST_USERUNORDER_OK) {
                unorderList.clear();
                unorderList.addAll(RequestUtils.userUnOrderList);
                adapter.notifyDataSetChanged();
            }
	    };
	};
	
	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    currentView = inflater.inflate(R.layout.order_page,
              container, false);
	    mContext = getActivity();
	    
	    bottom1Layout = (LinearLayout) currentView.findViewById(R.id.bottom1);
	    bottom2Layout = (LinearLayout) currentView.findViewById(R.id.bottom2);
	    orderedTV = (TextView) currentView.findViewById(R.id.ordered_page);
	    unorderedTV = (TextView) currentView.findViewById(R.id.unorder_page);
	    orderedTV.setOnClickListener(this);
	    unorderedTV.setOnClickListener(this);
	    
	    listView = (ListView) currentView.findViewById(R.id.order_list);
	    adapter = new OrderAdapter();
	    listView.setAdapter(adapter);

	    if (current_tag == 0) {
	        bottom1Layout.setVisibility(View.GONE);
	        bottom2Layout.setVisibility(View.VISIBLE);
	        RequestUtils.getUserOrderList(Constants.USER_ID, mHandler);
        } else {
            bottom1Layout.setVisibility(View.VISIBLE);
            bottom2Layout.setVisibility(View.GONE);
            RequestUtils.getUserUnOrderList(Constants.USER_ID, mHandler);
        }

	    IntentFilter filter = new IntentFilter("tct.restaurant.updateorder");
	    mContext.registerReceiver(receiver, filter);
		return currentView;
	}
	
	public void onDestroyView() {
	    super.onDestroyView();
	    mContext.unregisterReceiver(receiver);
	};
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RequestUtils.userOrderList.clear();
            RequestUtils.getUserOrderList(Constants.USER_ID, mHandler);
        }
    };

    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
    }

    class OrderAdapter extends BaseAdapter{
        ViewHodler vHodler;

        @Override
        public int getCount() {
            return current_tag==0?orderList.size():unorderList.size();
        }

        @Override
        public Object getItem(int position) {
            return current_tag==0?orderList.get(position):unorderList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                vHodler = new ViewHodler();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, null);
                vHodler.imageV = (ImageView) convertView.findViewById(R.id.image);
                vHodler.title = (TextView) convertView.findViewById(R.id.title);
                vHodler.price = (TextView) convertView.findViewById(R.id.price);
                vHodler.delButton = (Button) convertView.findViewById(R.id.delete_order);
                vHodler.hurryButton = (Button) convertView.findViewById(R.id.hurryup_order);
                vHodler.plus_reduce_layout = (LinearLayout) convertView.findViewById(R.id.plus_reduce_layout);
                vHodler.timeleft_layout = (LinearLayout) convertView.findViewById(R.id.timeleft_layout);
                vHodler.lineview = convertView.findViewById(R.id.time_line1);
                convertView.setTag(vHodler);
            } else {
                vHodler = (ViewHodler) convertView.getTag();
            }
            FoodEntity fEntity;
            if (current_tag == 0) {
                fEntity = orderList.get(position).getFoodEntity();
                vHodler.delButton.setVisibility(View.VISIBLE);
                vHodler.hurryButton.setVisibility(View.VISIBLE);
                vHodler.plus_reduce_layout.setVisibility(View.GONE);
                vHodler.timeleft_layout.setVisibility(View.VISIBLE);
            } else {
                fEntity = unorderList.get(position).getFoodEntity();
                vHodler.delButton.setVisibility(View.GONE);
                vHodler.hurryButton.setVisibility(View.GONE);
                vHodler.plus_reduce_layout.setVisibility(View.VISIBLE);
                vHodler.timeleft_layout.setVisibility(View.GONE);
            }
            vHodler.title.setText(fEntity.getName());
            vHodler.price.setText("¥" + fEntity.getPrice());

            ImageLoader.getInstance().displayImage(fEntity.getImage(), vHodler.imageV);
            return convertView;
        }

        class ViewHodler {
            ImageView imageV;
            TextView title;
            TextView price;
            Button delButton;
            Button hurryButton;
            LinearLayout plus_reduce_layout;
            LinearLayout timeleft_layout;
            View lineview;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ordered_page:
            current_tag = 0;
            bottom1Layout.setVisibility(View.GONE);
            bottom2Layout.setVisibility(View.VISIBLE);
            orderedTV.setBackgroundColor(getResources().getColor(R.color.tct_gray));
            unorderedTV.setBackgroundColor(getResources().getColor(R.color.tct_lightgray_text));
            RequestUtils.getUserOrderList(Constants.USER_ID, mHandler);
            break;
        case R.id.unorder_page:
            current_tag = 1;
            bottom1Layout.setVisibility(View.VISIBLE);
            bottom2Layout.setVisibility(View.GONE);
            orderedTV.setBackgroundColor(getResources().getColor(R.color.tct_lightgray_text));
            unorderedTV.setBackgroundColor(getResources().getColor(R.color.tct_gray));
            RequestUtils.getUserUnOrderList(Constants.USER_ID, mHandler);
            break;

        default:
            break;
        }
        
    }

}
