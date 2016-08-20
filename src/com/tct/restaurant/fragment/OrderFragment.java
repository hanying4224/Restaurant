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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.OrderItem;
import com.tct.restaurant.util.Constants;
import com.tct.restaurant.util.RequestUtils;

@SuppressLint("NewApi")
public class OrderFragment extends Fragment implements AdapterView.OnItemClickListener{
	private View currentView;
	private ListView listView;
	private OrderAdapter adapter;
	private Context mContext;

	ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();
	
	Handler mHandler = new Handler(){
	    public void handleMessage(android.os.Message msg) {
	        if (msg.what == RequestUtils.REQUEST_USERORDER_OK) {
	            orderList.clear();
	            orderList.addAll(RequestUtils.userOrderList);
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
	    listView = (ListView) currentView.findViewById(R.id.order_list);
	    adapter = new OrderAdapter();
	    listView.setAdapter(adapter);

	    RequestUtils.getUserOrderList(Constants.USER_ID, mHandler);

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
            return orderList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderList.get(position);
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
//                vHodler.delButton = (Button) convertView.findViewById(R.id.delete_order);
//                vHodler.hurryButton = (Button) convertView.findViewById(R.id.hurryup_order);
                convertView.setTag(vHodler);
            } else {
                vHodler = (ViewHodler) convertView.getTag();
            }
            vHodler.title.setText(orderList.get(position).getFoodEntity().getName());
            vHodler.price.setText("¥" + orderList.get(position).getFoodEntity().getPrice());

            ImageLoader.getInstance().displayImage(orderList.get(position).getFoodEntity().getImage(), vHodler.imageV);
            return convertView;
        }

        class ViewHodler {
            ImageView imageV;
            TextView title;
            TextView price;
            Button delButton;
            Button hurryButton;
        }
    }

}
