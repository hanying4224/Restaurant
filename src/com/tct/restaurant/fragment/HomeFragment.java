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
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.activity.FoodInfoActivity;
import com.tct.restaurant.activity.WelcomeActivity;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.util.RequestUtils;

@SuppressLint({ "NewApi", "ValidFragment" })
public class HomeFragment extends Fragment {
    private View currentView;
    private GridView foodGridView = null;
    private List<FoodEntity> foodEntityList = new ArrayList<FoodEntity>();
    private String mFoodType = "热菜类";
    private GridViewAdapter gridAdapter = null;
    private View sortBySales;
    private View sortByScore;
    private TextView sortBySalesTextView;
    private TextView sortByScoreTextView;
    private Drawable drawableWhite;
    private Drawable drawableBlack;

    public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
        currentView.setLayoutParams(layoutParams);
    }

    public FrameLayout.LayoutParams getCurrentViewParams() {
        return (LayoutParams) currentView.getLayoutParams();
    }
    
    public HomeFragment() {
        // TODO Auto-generated constructor stub
    }
    
    public HomeFragment(String foodType) {
        Log.i("hao", "HomeFragment foodType: "+foodType);
        // TODO Auto-generated constructor stub
        mFoodType = foodType;
    }
    
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == RequestUtils.REQUEST_FOODLIST_OK) {
                Log.i("hao", "handleMessage: "+RequestUtils.REQUEST_FOODLIST_OK);
                foodEntityList = RequestUtils.foodList_Current;
                gridAdapter.notifyDataSetChanged();
                Log.i("hao", "handleMessage size: "+foodEntityList.size());
            }
        };
    };
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.i("hao", "HomeFragment onCreateView begin.. ");
        // TODO Auto-generated method stub
        gridAdapter = new GridViewAdapter(getActivity());
        currentView = inflater.inflate(R.layout.slidingpane_home_layout,
                container, false);
        TextView foodtype = (TextView) currentView.findViewById(R.id.foodmenutype);
        foodtype.setText(mFoodType);
        sortBySales = currentView.findViewById(R.id.foodsales);
        sortBySales.setOnClickListener(mClickListener);
        sortByScore = currentView.findViewById(R.id.foodscore);
        sortByScore.setOnClickListener(mClickListener);
        sortBySalesTextView = (TextView) currentView.findViewById(R.id.foodsales_text);
        sortByScoreTextView = (TextView) currentView.findViewById(R.id.foodscore_text);
        drawableWhite = getResources().getDrawable(R.drawable.rank_white);
        drawableWhite.setBounds(0, 0, drawableWhite.getMinimumWidth(), drawableWhite.getMinimumHeight());
        drawableBlack = getResources().getDrawable(R.drawable.rank_black);
        drawableBlack.setBounds(0, 0, drawableBlack.getMinimumWidth(), drawableBlack.getMinimumHeight());
        
        foodGridView = (GridView) currentView.findViewById(R.id.grid_food);
        foodGridView.setNumColumns(3);
        foodGridView.setHorizontalSpacing(70);
        foodGridView.setVerticalSpacing(30);
        foodGridView.setAdapter(gridAdapter);
        RequestUtils.getFoodListByType(mFoodType, mHandler);
        Log.i("hao", "foodEntityList.size: "+foodEntityList.size());
        return currentView;
    }

    OnClickListener mClickListener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
            case R.id.foodsales:
                sortBySales.setBackgroundColor(getResources().getColor(R.color.tct_yellow));
                sortByScore.setBackgroundColor(getResources().getColor(R.color.tct_white));
                sortBySalesTextView.setTextColor(getResources().getColor(R.color.tct_white));
                sortBySalesTextView.setCompoundDrawables(null, null, drawableWhite, null);
                sortByScoreTextView.setTextColor(getResources().getColor(R.color.tct_black));
                sortByScoreTextView.setCompoundDrawables(null, null, drawableBlack, null);
                break;
            case R.id.foodscore:
                sortBySales.setBackgroundColor(getResources().getColor(R.color.tct_white));
                sortByScore.setBackgroundColor(getResources().getColor(R.color.tct_yellow));
                sortByScoreTextView.setTextColor(getResources().getColor(R.color.tct_white));
                sortByScoreTextView.setCompoundDrawables(null, null, drawableWhite, null);
                sortBySalesTextView.setTextColor(getResources().getColor(R.color.tct_black));
                sortBySalesTextView.setCompoundDrawables(null, null, drawableBlack, null);
                break;
            default:
                break;
            }
        }
    };
    class GridViewAdapter extends BaseAdapter {

        private Context c;
        private FoodEntity foodEntity= null;
//        private List<FoodEntity> foodEntityList;
        DisplayImageOptions options = new DisplayImageOptions.Builder() 
            .showStubImage(R.drawable.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
            .build();                                   // 创建配置过得DisplayImageOption对象
/*        .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片 */
        public GridViewAdapter(Context c) {
            this.c = c;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return foodEntityList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return foodEntityList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(final int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            ViewHodler vh;
            if (arg1 == null) {
                vh = new ViewHodler();
                arg1 = LayoutInflater.from(c).inflate(R.layout.food_griditem,
                        null);
                vh.foodPic = (ImageView) arg1.findViewById(R.id.food_pic);
                vh.foodName = (TextView) arg1.findViewById(R.id.food_name);
                vh.foodPrice = (TextView) arg1.findViewById(R.id.food_price);
                vh.foodSales = (TextView) arg1.findViewById(R.id.food_sales);
                vh.foodScore = (TextView) arg1.findViewById(R.id.food_score);
                vh.foodAddtoOrder = (ImageView) arg1.findViewById(R.id.food_addpic);
                arg1.setTag(vh);
            } else {
                vh = (ViewHodler) arg1.getTag();
            }
            foodEntity = foodEntityList.get(arg0);
            Log.i("hao", foodEntity.getName()+" "+foodEntity.getFID());
            vh.foodName.setText(foodEntity.getName());
            vh.foodPrice.setText(foodEntity.getPrice()+"元/份");
            vh.foodSales.setText(foodEntity.getSold_num()+"份");
            vh.foodScore.setText(" "+foodEntity.getStars()+"分");
            ImageLoader.getInstance().displayImage(foodEntity.getImage(), vh.foodPic, options/*, null*/);
            vh.foodPic.setTag(foodEntity.getFID());
            vh.foodPic.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    final String id = v.getTag().toString();
                    Intent foodInfoIntent = new Intent(c, FoodInfoActivity.class);
                    foodInfoIntent.putExtra("fid", id+"");
                    startActivity(foodInfoIntent);
                }
            });
            vh.foodAddtoOrder.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("hao", "HomeFragment click add image..  position = "+ arg0);
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    View view = LayoutInflater.from(c).inflate(R.layout.dialog_order_confirm, null);
                    builder.setView(view);
                    dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    Button okBtn = (Button) view.findViewById(R.id.ok);
                    Button cancelBtn = (Button) view.findViewById(R.id.cancel);
                    okBtn.setOnClickListener(listener);
                    cancelBtn.setOnClickListener(listener);
                    position = arg0;
                    dialog.show();
                }
            });

            return arg1;
        }

        class ViewHodler {
            ImageView foodPic;
            TextView foodName;
            TextView foodSales;
            TextView foodScore;
            TextView foodPrice;
            ImageView foodAddtoOrder;
        }

        int position;
        AlertDialog dialog;
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.ok:
                    Log.d("ying", "ok, position = "+ position);
                    RequestUtils.insertAFoodToServerOrder(foodEntityList.get(position), c);
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

//        class ImageLoadingListenerImp implements ImageLoadingListener {
//
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                // TODO Auto-generated method stub
//                
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view,
//                    FailReason failReason) {
//                // TODO Auto-generated method stub
//                
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view,
//                    Bitmap loadedImage) {
//                // TODO Auto-generated method stub
//                
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                // TODO Auto-generated method stub
//                
//            }
//        }

    }

}
