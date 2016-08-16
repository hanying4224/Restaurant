/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneMenuFragment.java 
 * @Prject: SlidingPane
 * @Package: com.tct.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:42:07 
 * @version: V1.0   
 */
package com.tct.restaurant.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.tct.restaurant.R;
import com.tct.restaurant.activity.HomePageActivity;
import com.tct.restaurant.activity.LoginActivity;

@SuppressLint("NewApi")
public class MenuFragment extends Fragment implements View.OnClickListener {

	private View currentView;
	private Button bt_gift, bt_home, bt_invitation, bt_orders, bt6;
	private String[] generalsTypes = new String[] { "点餐" };
	private String[][] generals = new String[][] {
            { "招牌", "热菜", "凉菜", "主食", "养生汤", "酒水" }
    };
	private Context mContext;

	public View getCurrentView() {
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.slidingpane_menu_layout,
				container, false);
		bt_gift = (Button) currentView.findViewById(R.id.btn_pay);
		bt_home = (Button) currentView.findViewById(R.id.btn_home);
		bt_invitation = (Button) currentView.findViewById(R.id.btn_amusement);
		bt_orders = (Button) currentView.findViewById(R.id.btn_order);
		bt_gift.setOnClickListener(this);
		bt_home.setOnClickListener(this);
		bt_invitation.setOnClickListener(this);
		bt_orders.setOnClickListener(this);
		mContext = getActivity();
		ExpandableListView expandableListView = (ExpandableListView) currentView.findViewById(R.id.food_list);
		expandableListView.setAdapter(adapter);
		expandableListView.setOnChildClickListener(onChildClickListener);
		return currentView;
	}
	
	OnChildClickListener onChildClickListener =  new OnChildClickListener() {
        
        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition, int childPosition, long id) {
            Toast.makeText(
                    mContext,
                    "你点击了" + adapter.getChild(groupPosition, childPosition),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    };
	
	ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return generalsTypes.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            return generals[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
            return generalsTypes[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return generals[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(getGroup(groupPosition).toString());
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(20, 10, 20, 10);
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(getChild(groupPosition, childPosition).toString());
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(20, 10, 20, 10);
            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }
	    
	};

	@SuppressLint("CommitTransaction")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();// 开始一个事物
		switch (v.getId()) {
		case R.id.btn_home:
			Fragment homeFragment = new HomeFragment();
			ft.replace(R.id.slidingpane_content, homeFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_order:
			Fragment orderFragment = new OrderFragment();
			ft.replace(R.id.slidingpane_content, orderFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_pay:
			Fragment giftFragment = new PayFragment();
			ft.replace(R.id.slidingpane_content, giftFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_amusement:
			Fragment invitationFragment = new AmusementFragment();
			ft.replace(R.id.slidingpane_content, invitationFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		}
//		((HomePageActivity) getActivity()).getSlidingPaneLayout().closePane();
	}
}
