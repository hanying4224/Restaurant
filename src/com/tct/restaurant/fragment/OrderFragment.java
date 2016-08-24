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
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.activity.HomePageActivity;
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
	private TextView orderedTV, unorderedTV, goPay;
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
                Log.d("ying", "待下单，    notifyDataSetChanged");
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
	    goPay = (TextView) currentView.findViewById(R.id.go_pay);
	    orderedTV.setOnClickListener(this);
	    unorderedTV.setOnClickListener(this);
	    goPay.setOnClickListener(this);
	    
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
    
    DisplayImageOptions options = new DisplayImageOptions.Builder() 
    //.showStubImage(R.drawable.ic_launcher)          // 设置图片下载期间显示的图片 
    //.showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片 
    //.showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片     
    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中 
    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中 
    .build();

    class OrderAdapter extends BaseAdapter{

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHodler vHodler;
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
                vHodler.plusImg = (ImageView) convertView.findViewById(R.id.plus_img);
                vHodler.reduceImg = (ImageView) convertView.findViewById(R.id.reduce_img);
                vHodler.numberTv = (TextView) convertView.findViewById(R.id.number);
                vHodler.commentImg = (ImageView) convertView.findViewById(R.id.comment_img);
                vHodler.comment_et = (EditText) convertView.findViewById(R.id.comment_tv);
                convertView.setTag(vHodler);
            } else {
                vHodler = (ViewHodler) convertView.getTag();
            }
            FoodEntity fEntity;
            Log.d("ying", "getview,   current_tag = "+ current_tag);
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
                vHodler.numberTv.setText(unorderList.get(position).getNum());
            }
            if (fEntity!=null) {
                vHodler.title.setText(fEntity.getName());
                vHodler.price.setText("¥" + fEntity.getPrice());
                ImageLoader.getInstance().displayImage(fEntity.getImage(), vHodler.imageV, options);
            }
            vHodler.delButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos2 = position;
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_confirm, null);
                    TextView tv =  (TextView) view.findViewById(R.id.dialog_text);
                    tv.setText("确定通知厨房取消此菜品？");
                    Button okBtn = (Button) view.findViewById(R.id.ok);
                    okBtn.setText("是哒");
                    Button cancelBtn = (Button) view.findViewById(R.id.cancel);
                    okBtn.setOnClickListener(listener2);
                    cancelBtn.setOnClickListener(listener2);
                    builder.setView(view);
                    dialog2 = builder.create();
                    dialog2.setCanceledOnTouchOutside(false);
                    dialog2.show();
                }
            });
            
            vHodler.commentImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    vHodler.commentImg.setVisibility(View.GONE);
                    vHodler.comment_et.setVisibility(View.VISIBLE);
                }
            });
            
            vHodler.hurryButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_hurryup, null);
                    Button cancelBtn = (Button) view.findViewById(R.id.ok);
                    cancelBtn.setOnClickListener(listener3);
                    builder.setView(view);
                    dialog3 = builder.create();
                    dialog3.show();
                }
            });
            
            vHodler.plusImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = (String) vHodler.numberTv.getText();
                    vHodler.numberTv.setText("" + (Integer.parseInt(num) + 1));
                }
            });
            
            vHodler.reduceImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = (String) vHodler.numberTv.getText();
                    int new_i = Integer.parseInt(num) - 1;
                    
                    if (new_i < 1) {
                        pos = position;
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_confirm, null);
                        TextView tv =  (TextView) view.findViewById(R.id.dialog_text);
                        tv.setText("主人，这么美味，不尝尝吗？");
                        Button okBtn = (Button) view.findViewById(R.id.ok);
                        okBtn.setText("不尝");
                        Button cancelBtn = (Button) view.findViewById(R.id.cancel);
                        okBtn.setOnClickListener(listener);
                        cancelBtn.setOnClickListener(listener);
                        builder.setView(view);
                        dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    } else {
                        vHodler.numberTv.setText("" + new_i);
                    }
                }
            });

            
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
            ImageView plusImg, reduceImg, commentImg;
            TextView numberTv;
            EditText comment_et;
        }
    }
    
    AlertDialog dialog;
    int pos;
    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("ying", "pos = "+ pos);
            switch (v.getId()) {
            case R.id.ok:
//                unorderList.remove(pos);
//                adapter.notifyDataSetChanged();
                RequestUtils.userUnOrderList.clear();
                RequestUtils.delAFoodFromUnOrder(unorderList.get(pos).getFoodEntity(), mHandler);
                dialog.cancel();
                break;
            case R.id.cancel:
                dialog.cancel();
                break;

            default:
                break;
            }
        }
    };

    AlertDialog dialog2;
    int pos2;
    OnClickListener listener2 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.ok:
                RequestUtils.userOrderList.clear();
                RequestUtils.delAFoodFromOrder(orderList.get(pos2).getFoodEntity(), mHandler);
                dialog2.cancel();
                break;
            case R.id.cancel:
                dialog2.cancel();
                break;

            default:
                break;
            }
        }
    };
    
    AlertDialog dialog3;
    OnClickListener listener3 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.ok:
                dialog3.cancel();
                break;

            default:
                break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ordered_page:
            current_tag = 0;
            bottom1Layout.setVisibility(View.GONE);
            bottom2Layout.setVisibility(View.VISIBLE);
            orderedTV.setBackgroundColor(getResources().getColor(R.color.tct_yellow));
            unorderedTV.setBackgroundColor(getResources().getColor(R.color.tct_lightgray_text));
            Log.d("ying", "已下单");
            RequestUtils.getUserOrderList(Constants.USER_ID, mHandler);
            break;
        case R.id.unorder_page:
            current_tag = 1;
            bottom1Layout.setVisibility(View.VISIBLE);
            bottom2Layout.setVisibility(View.GONE);
            orderedTV.setBackgroundColor(getResources().getColor(R.color.tct_lightgray_text));
            unorderedTV.setBackgroundColor(getResources().getColor(R.color.tct_yellow));
            Log.d("ying", "待下单");
            RequestUtils.getUserUnOrderList(Constants.USER_ID, mHandler);
            break;
        case R.id.go_pay:
            Log.d("ying", "pay************88");
            HomePageActivity activity = (HomePageActivity) getActivity();
            Button btn_pay = (Button) activity.getMenuFragment().getCurrentView().findViewById(R.id.btn_pay);
            MenuFragment menuFragment = activity.getMenuFragment();
            menuFragment.onClick(btn_pay);
//            btn_pay.setPressed(true);
            break;

        default:
            break;
        }
        
    }

}
