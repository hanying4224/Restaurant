package com.tct.restaurant.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;
import com.tct.restaurant.util.RequestUtils;

public class FoodInfoActivity extends Activity {
    View v1;
    View v2;
    TextView mCommentNum;
    private TextView foodName;
    private TextView foodPrice;
    private TextView foodScore;
    private TextView foodEvaluation;
    private TextView foodIntroduction;
    private ImageView foodPicView;
    private Button foodAddBt;
    private String mFid = "";
    private FoodEntity mFoodEntity;
    public  List<FoodEntity> foodList_Current = new ArrayList<FoodEntity>();
    
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.food_detail_layout);

        mFid = getIntent().getStringExtra("fid");
        Log.i("hao", "" + mFid);

        foodName = (TextView) findViewById(R.id.food_name);
        foodPrice = (TextView) findViewById(R.id.food_price);
        foodScore = (TextView) findViewById(R.id.food_score);
        foodEvaluation = (TextView) findViewById(R.id.food_comment_num);
        foodIntroduction = (TextView) findViewById(R.id.food_info);
        foodPicView = (ImageView) findViewById(R.id.food_pic);
        foodEvaluation.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
        foodAddBt = (Button) findViewById(R.id.food_add_to_cart);
        foodAddBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("hao", "HomeFragment click add to cart bt.. ");
            }
        });
        init();
    }

    DisplayImageOptions options = new DisplayImageOptions.Builder() 
        .showStubImage(R.drawable.ic_launcher)          // 设置图片下载期间显示的图片 
        .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片 
        .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片     
        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中 
        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中 
        .build();                                   // 创建配置过得DisplayImageOption对象

    private void init() {
        // TODO Auto-generated method stub
        foodList_Current = RequestUtils.foodList_Current;
        for (int i = 0; i < foodList_Current.size(); i++) {
            if (foodList_Current.get(i).getFID().equals(mFid)) {
                mFoodEntity = foodList_Current.get(i);
            }
        }
        if (mFoodEntity != null) {
            foodName.setText(mFoodEntity.getName());
            foodPrice.setText(mFoodEntity.getPrice()+"元");
            foodScore.setText("评分:"+mFoodEntity.getStars());
            foodEvaluation.setText("评论 "+mFoodEntity.getEvaluation());
            foodIntroduction.setText(mFoodEntity.getIntroduction());
            ImageLoader.getInstance().displayImage(mFoodEntity.getImage(), foodPicView, options/*, null*/);
        }
    }

}
