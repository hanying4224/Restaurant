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
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tct.restaurant.R;
import com.tct.restaurant.util.Constants;

@SuppressLint("NewApi")
public class MenuFragment extends Fragment implements View.OnClickListener {

    /*
     * public static final String FOODTYPE_HOT = "热菜类";
    public static final String FOODTYPE_COLD = "凉菜类";
    public static final String FOODTYPE_SWEET = "甜点类";
    public static final String FOODTYPE_SOUP = "汤类";
    public static final String FOODTYPE_DRINKS = "饮料类";
    public static final String FOODTYPE_STAPLE = "主食类";
     * */
	private View currentView;
	private Button bt_gift, /*bt_home,*/ bt_invitation, bt6;
	private Button bt_orders;
	private Button btn_nearby;
	private String[] generalsTypes = new String[] { "点餐" };
	private String[][] generals = new String[][] {
            { Constants.FOODTYPE_SPECIAL, Constants.FOODTYPE_HOT, Constants.FOODTYPE_COLD, Constants.FOODTYPE_STAPLE, Constants.FOODTYPE_SOUP, Constants.FOODTYPE_DRINKS }
    };
	private Context mContext;
	private LinearLayout order_layout, pay_layout, amusement_layout, nearby_layout;
	private ImageView order_img, pay_img, amusement_img, nearby_img;
	ExpandableListView expandableListView;

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
//		bt_home = (Button) currentView.findViewById(R.id.btn_home);
		bt_invitation = (Button) currentView.findViewById(R.id.btn_amusement);
		bt_orders = (Button) currentView.findViewById(R.id.btn_order);
		btn_nearby = (Button) currentView.findViewById(R.id.btn_nearby);
		order_layout = (LinearLayout) currentView.findViewById(R.id.order_layout);
		pay_layout = (LinearLayout) currentView.findViewById(R.id.pay_layout);
		amusement_layout = (LinearLayout) currentView.findViewById(R.id.amusement_layout);
		nearby_layout = (LinearLayout) currentView.findViewById(R.id.nearby_layout);
		order_img = (ImageView) currentView.findViewById(R.id.order_img);
		pay_img = (ImageView) currentView.findViewById(R.id.pay_img);
		amusement_img = (ImageView) currentView.findViewById(R.id.amusement_img);
		nearby_img = (ImageView) currentView.findViewById(R.id.nearby_img);
		btn_nearby.setOnClickListener(this);
		bt_gift.setOnClickListener(this);
//		bt_home.setOnClickListener(this);
		bt_invitation.setOnClickListener(this);
		bt_orders.setOnClickListener(this);
		mContext = getActivity();
		expandableListView = (ExpandableListView) currentView.findViewById(R.id.food_list);
		expandableListView.setAdapter(adapter);
		expandableListView.setOnChildClickListener(onChildClickListener);
		expandableListView.setGroupIndicator(null);
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                Log.d("ying", "onGroupClick");
                menuItemClick(bt_orders, order_layout, false);
                menuItemClick(bt_gift, pay_layout, false);
                menuItemClick(bt_invitation, amusement_layout, false);
                menuItemClick(btn_nearby, nearby_layout, false);
                menuIconClick(-1);
                
                return false;
            }
        });
		return currentView;
	}
	
	OnChildClickListener onChildClickListener =  new OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition, int childPosition, long id) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();// 开始一个事物
            Fragment homeFragment = new HomeFragment(adapter.getChild(groupPosition, childPosition).toString());
            ft.replace(R.id.slidingpane_content, homeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
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
            if (isExpanded) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.food_group_view_expand, null);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.food_group_view, null);
            }
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(getChild(groupPosition, childPosition).toString());
            textView.setTextSize(15);
            textView.setTextColor(mContext.getResources().getColor(R.color.tct_black));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(20, 18, 20, 18);
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
	    expandableListView.collapseGroup(0);
		FragmentTransaction ft = getFragmentManager().beginTransaction();// 开始一个事物
		switch (v.getId()) {
		/*case R.id.btn_home:
			Fragment homeFragment = new HomeFragment("凉菜类");
			ft.replace(R.id.slidingpane_content, homeFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;*/
		case R.id.btn_order:
		    menuIconClick(0);
		    menuItemClick(bt_orders, order_layout, true);
		    menuItemClick(bt_gift, pay_layout, false);
		    menuItemClick(bt_invitation, amusement_layout, false);
		    menuItemClick(btn_nearby, nearby_layout, false);
			Fragment orderFragment = new OrderFragment();
			ft.replace(R.id.slidingpane_content, orderFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_pay:
		    menuIconClick(1);
		    menuItemClick(bt_orders, order_layout, false);
            menuItemClick(bt_gift, pay_layout, true);
            menuItemClick(bt_invitation, amusement_layout, false);
            menuItemClick(btn_nearby, nearby_layout, false);
			Fragment giftFragment = new PayFragment();
			ft.replace(R.id.slidingpane_content, giftFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_amusement:
		    menuIconClick(2);
		    menuItemClick(bt_orders, order_layout, false);
            menuItemClick(bt_gift, pay_layout, false);
            menuItemClick(bt_invitation, amusement_layout, true);
            menuItemClick(btn_nearby, nearby_layout, false);
			Fragment invitationFragment = new AmusementFragment();
			ft.replace(R.id.slidingpane_content, invitationFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_nearby:
		    menuIconClick(3);
		    menuItemClick(bt_orders, order_layout, false);
            menuItemClick(bt_gift, pay_layout, false);
            menuItemClick(bt_invitation, amusement_layout, false);
            menuItemClick(btn_nearby, nearby_layout, true);
            Fragment nearbyFragment = new NearbyFragment();
            ft.replace(R.id.slidingpane_content, nearbyFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            break;
		}
//		((HomePageActivity) getActivity()).getSlidingPaneLayout().closePane();
	}
	
	private void menuItemClick(Button btn, LinearLayout layout, boolean highLight) {
	    Resources r = mContext.getResources();
	    if (highLight) {
            btn.setTextColor(r.getColor(R.color.tct_yellow));
            layout.setBackgroundColor(r.getColor(R.color.tct_gray_bg));
        } else {
            btn.setTextColor(r.getColor(R.color.tct_black));
            layout.setBackgroundColor(r.getColor(R.color.tct_white));
        }
	}
	
	private void menuIconClick(int i) {
	    if (i== 0) {//订单
            order_img.setImageResource(R.drawable.order_selected);
            pay_img.setImageResource(R.drawable.pay);
            amusement_img.setImageResource(R.drawable.entertainment);
            nearby_img.setImageResource(R.drawable.nearby);
        } else if (i == 1) {
            order_img.setImageResource(R.drawable.order);
            pay_img.setImageResource(R.drawable.pay_selected);
            amusement_img.setImageResource(R.drawable.entertainment);
            nearby_img.setImageResource(R.drawable.nearby);
        } else if (i == 2) {
            order_img.setImageResource(R.drawable.order);
            pay_img.setImageResource(R.drawable.pay);
            amusement_img.setImageResource(R.drawable.entertainment_selected);
            nearby_img.setImageResource(R.drawable.nearby);
        } else if (i == 3) {
            order_img.setImageResource(R.drawable.order);
            pay_img.setImageResource(R.drawable.pay);
            amusement_img.setImageResource(R.drawable.entertainment);
            nearby_img.setImageResource(R.drawable.nearby_selected);
        } else if (i == -1) {
            order_img.setImageResource(R.drawable.order);
            pay_img.setImageResource(R.drawable.pay);
            amusement_img.setImageResource(R.drawable.entertainment);
            nearby_img.setImageResource(R.drawable.nearby);
        }
	}
}
