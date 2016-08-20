package com.tct.restaurant.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;
import com.tct.restaurant.util.RequestUtils;

public class FoodInfoActivity extends Activity implements OnClickListener{
    View v1;
    View v2;
    private TextView foodType;
    private TextView foodName;
    private TextView foodPrice;
    private TextView foodScore;
    private TextView foodIntroduction;
    private TextView foodIngredient;
    private TextView foodInfo;
    private TextView foodEvaluation;
    private TextView foodPepperyNo;
    private TextView foodPepperyLittle;
    private TextView foodPepperyMiddle;
    private TextView foodPepperyMost;
    private ImageView foodPicView;
    private Button foodAddBt;
    private RatingBar rb;
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
        foodType = (TextView) findViewById(R.id.detail_food_type);
        foodPicView = (ImageView) findViewById(R.id.food_pic);
        foodName = (TextView) findViewById(R.id.food_name);
        foodPrice = (TextView) findViewById(R.id.food_price);
        rb = (RatingBar) findViewById(R.id.ratingBar);
        foodScore = (TextView) findViewById(R.id.food_score);
        foodIntroduction = (TextView) findViewById(R.id.food_introduction);
        foodIntroduction.setOnClickListener(this);
        foodIngredient = (TextView) findViewById(R.id.food_ingredient);
        foodIngredient.setOnClickListener(this);
        foodInfo = (TextView) findViewById(R.id.food_info);
        foodPepperyNo = (TextView) findViewById(R.id.food_taste_peppery_no);
        foodPepperyLittle = (TextView) findViewById(R.id.food_taste_peppery_little);
        foodPepperyMiddle = (TextView) findViewById(R.id.food_taste_peppery_middle);
        foodPepperyMost = (TextView) findViewById(R.id.food_taste_peppery_most);
        foodPepperyNo.setOnClickListener(this);
        foodPepperyLittle.setOnClickListener(this);
        foodPepperyMiddle.setOnClickListener(this);
        foodPepperyMost.setOnClickListener(this);
        foodEvaluation = (TextView) findViewById(R.id.food_comment_num);
        foodEvaluation.setOnClickListener(this);
        foodAddBt = (Button) findViewById(R.id.food_add_to_cart);
        foodAddBt.setOnClickListener(this);
        
        
        // TODO Auto-generated method stub
        foodList_Current = RequestUtils.foodList_Current;
        for (int i = 0; i < foodList_Current.size(); i++) {
            if (foodList_Current.get(i).getFID().equals(mFid)) {
                mFoodEntity = foodList_Current.get(i);
            }
        }
        if (mFoodEntity != null) {
            foodType.setText(mFoodEntity.getCategory());
            foodName.setText(mFoodEntity.getName());
            foodPrice.setText("¥"+mFoodEntity.getPrice());
            foodScore.setText(mFoodEntity.getStars()+"分");
            rb.setRating(Float.parseFloat(mFoodEntity.getStars()));
            foodEvaluation.setText(" "+mFoodEntity.getEvaluation()+"条");
            foodInfo.setText(mFoodEntity.getIntroduction());
            foodInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
            ImageLoader.getInstance().displayImage(mFoodEntity.getImage(), foodPicView, options/*, null*/);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.food_add_to_cart:
            Log.i("hao", "HomeFragment click add to cart bt.. ");
            //ying
            break;
        case R.id.food_introduction:
            Log.i("hao", "HomeFragment onClick food_introduction");
            foodIntroduction.setTextColor(getResources().getColor(R.color.tct_black));
            foodIntroduction.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            foodIngredient.setTextColor(getResources().getColor(R.color.tct_gray));
            foodIngredient.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            foodInfo.setText(mFoodEntity.getIntroduction());
            break;
        case R.id.food_ingredient:
            Log.i("hao", "HomeFragment onClick food_ingredient");
            foodIngredient.setTextColor(getResources().getColor(R.color.tct_black));
            foodIngredient.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            foodIntroduction.setTextColor(getResources().getColor(R.color.tct_gray));
            foodIntroduction.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            foodInfo.setText(mFoodEntity.getIngredient());
            break;
        case R.id.food_taste_peppery_no:
            Log.i("hao", "HomeFragment onClick food_taste_peppery_no");
            foodPepperyNo.setBackground(getResources().getDrawable(R.drawable.text_field_selected_black));
            foodPepperyLittle.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyMiddle.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyMost.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyNo.setTextColor(getResources().getColor(R.color.tct_black));
            foodPepperyLittle.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyMiddle.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyMost.setTextColor(getResources().getColor(R.color.tct_gray));
            break;
        case R.id.food_taste_peppery_little:
            Log.i("hao", "HomeFragment onClick food_taste_peppery_little");
            foodPepperyNo.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyLittle.setBackground(getResources().getDrawable(R.drawable.text_field_selected_black));
            foodPepperyMiddle.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyMost.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyNo.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyLittle.setTextColor(getResources().getColor(R.color.tct_black));
            foodPepperyMiddle.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyMost.setTextColor(getResources().getColor(R.color.tct_gray));
            break;
        case R.id.food_taste_peppery_middle:
            Log.i("hao", "HomeFragment onClick food_taste_peppery_middle");
            foodPepperyNo.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyLittle.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyMiddle.setBackground(getResources().getDrawable(R.drawable.text_field_selected_black));
            foodPepperyMost.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyNo.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyLittle.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyMiddle.setTextColor(getResources().getColor(R.color.tct_black));
            foodPepperyMost.setTextColor(getResources().getColor(R.color.tct_gray));
            break;
        case R.id.food_taste_peppery_most:
            Log.i("hao", "HomeFragment onClick food_taste_peppery_most");
            foodPepperyNo.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyLittle.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyMiddle.setBackground(getResources().getDrawable(R.drawable.text_field));
            foodPepperyMost.setBackground(getResources().getDrawable(R.drawable.text_field_selected_black));
            foodPepperyNo.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyLittle.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyMiddle.setTextColor(getResources().getColor(R.color.tct_gray));
            foodPepperyMost.setTextColor(getResources().getColor(R.color.tct_black));
            break;
        case R.id.food_comment_num:
            Log.i("hao", "HomeFragment onClick food_comment_num");
            break;
        default:
            break;
        }
    }

}
