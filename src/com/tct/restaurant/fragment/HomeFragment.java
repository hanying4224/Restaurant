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
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.util.RequestUtils;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment {
    private View currentView;
//    private RefreshableListView mListView;
//    private HomePageRestaurantAdapter adapter;
//    private List<RestaurantEntity> mlist;
//    private int total = 21;
//    private int step = 7;
//    private int add = 7;

    private GridView foodGridView = null;
    private List<FoodEntity> foodEntityList = new ArrayList<FoodEntity>();

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
        currentView = inflater.inflate(R.layout.slidingpane_home_layout,
                container, false);
        // mListView = (RefreshableListView) currentView
        // .findViewById(R.id.mineList);
        // getDate();
        // setListener();
        RequestUtils.getFoodListByType("热菜类", new Handler());
        foodEntityList = RequestUtils.foodList_Current;
        Log.i("hao", "foodEntityList.size: "+foodEntityList.size());
        foodGridView = (GridView) currentView.findViewById(R.id.grid_food);
        foodGridView.setNumColumns(3);
        foodGridView.setHorizontalSpacing(70);
        foodGridView.setVerticalSpacing(50);
        foodGridView.setAdapter(new GridViewAdapter(foodEntityList, getActivity()));
        return currentView;
    }

    class GridViewAdapter extends BaseAdapter {

        private Context c;

        private List<FoodEntity> foodEntityList;
        DisplayImageOptions options = new DisplayImageOptions.Builder() 
        .showStubImage(R.drawable.ic_launcher)          // 设置图片下载期间显示的图片 
        .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片 
        .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片     
        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中 
        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中 
        .build();                                   // 创建配置过得DisplayImageOption对象
/*        .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片 
*/        public GridViewAdapter(List<FoodEntity> foodEntityList, Context c) {
            this.c = c;
            this.foodEntityList = foodEntityList;
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
        public View getView(int arg0, View arg1, ViewGroup arg2) {
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
                vh.foodAddBt = (TextView) arg1.findViewById(R.id.food_score);
                arg1.setTag(vh);
            } else {
                vh = (ViewHodler) arg1.getTag();
            }
            Log.i("hao", ""+foodEntityList.get(arg0).getImage());
            vh.foodName.setText(foodEntityList.get(arg0).getName());
            vh.foodPrice.setText(foodEntityList.get(arg0).getPrice()+"元/份");
            vh.foodSales.setText("Sales: "+foodEntityList.get(arg0).getSold_num());
            vh.foodAddBt.setText("Score:"+"4.2分");
            
            
            ImageLoader.getInstance().displayImage(foodEntityList.get(arg0).getImage(), vh.foodPic, options/*, null*/);
            return arg1;
        }

        class ViewHodler {
            ImageView foodPic;
            TextView foodName;
            TextView foodSales;
            TextView foodAddBt;
            TextView foodPrice;
        }
        
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
