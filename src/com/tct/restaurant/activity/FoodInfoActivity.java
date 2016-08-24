package com.tct.restaurant.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.EvaluationItem;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.util.InjectView;
import com.tct.restaurant.util.Injector;
import com.tct.restaurant.util.RequestUtils;

public class FoodInfoActivity extends Activity implements OnClickListener{
    View v1;
    View v2;
    private Context mContext;
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
    private TextView foodEvaluation2;
    private TextView foodNocommentsView;
    private ImageView foodPicView;
    private ImageView foodCommentBackView;
    private ImageView callView;
    private ImageView callingView;
    private View introductionLineView;
    private View ingredientLineView;
    private Button foodAddBt;
    private RatingBar rb;
    private View detailBackView;//detail_back
    private View mainContentRight;//detail_back
    private View mainContentRightComments;//detail_back
    private ListView commentsListView;
    
    private String mFid = "";
    private FoodEntity mFoodEntity;
    public List<FoodEntity> foodList_Current = new ArrayList<FoodEntity>();
    private List<EvaluationItem> evaluationList = new ArrayList<EvaluationItem>();
    private CommentsAdapter mCommentsAdapter = new CommentsAdapter();
    
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == RequestUtils.REQUEST_EVALUATIONLIST_OK) {
                Log.i("hao", "handleMessage: "+RequestUtils.REQUEST_FOODLIST_OK);
                evaluationList = RequestUtils.evaluationList;
                foodEvaluation.setText(" "+evaluationList.size()+"条");
                mCommentsAdapter.notifyDataSetChanged();
                commentsListView.setVisibility(View.VISIBLE);
                foodNocommentsView.setVisibility(View.GONE);
                Log.i("hao", "handleMessage size: "+evaluationList.size());
            } else if (msg.what == RequestUtils.REQUEST_NOK) {
                Log.i("hao", "handleMessage: "+RequestUtils.REQUEST_NOK+" no data in db");
                evaluationList.clear();
                foodEvaluation.setText(" 0条");
                mCommentsAdapter.notifyDataSetChanged();
                commentsListView.setVisibility(View.GONE);
                foodNocommentsView.setVisibility(View.VISIBLE);
            }
        };
    };
    
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(params);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.food_detail_layout);
        mContext = this;
        mFid = getIntent().getStringExtra("fid");
        Log.i("hao", "food id: " + mFid);
        
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
        detailBackView = findViewById(R.id.top_panel_back);
        detailBackView.setVisibility(View.VISIBLE);
        detailBackView.setOnClickListener(this);
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
        foodEvaluation2 = (TextView) findViewById(R.id.food_comment_num2);
        
        mainContentRight = findViewById(R.id.main_right);
        mainContentRightComments = findViewById(R.id.main_right_comments);
        commentsListView = (ListView) findViewById(R.id.comments_list);
        commentsListView.setAdapter(mCommentsAdapter);
        foodNocommentsView = (TextView) findViewById(R.id.food_comment_empty);
        foodCommentBackView = (ImageView) findViewById(R.id.food_comment_back);
        foodCommentBackView.setOnClickListener(this);
        callView = (ImageView) findViewById(R.id.top_panel_ring_pic);
        callView.setOnClickListener(this);
        callingView = (ImageView) findViewById(R.id.top_panel_ringing_pic);
        callingView.setOnClickListener(this);
        ingredientLineView = findViewById(R.id.food_ingredient_line);
        introductionLineView = findViewById(R.id.food_introduction_line);

        // TODO Auto-generated method stub
        foodList_Current = RequestUtils.foodList_Current;
        for (int i = 0; i < foodList_Current.size(); i++) {
            if (foodList_Current.get(i).getFID().equals(mFid)) {
                mFoodEntity = foodList_Current.get(i);
            }
        }
        if (mFoodEntity != null) {
            RequestUtils.requestEvaluationList(mFoodEntity.getFID(), mHandler);
            foodType.setText(mFoodEntity.getCategory());
            foodName.setText(mFoodEntity.getName());
            foodPrice.setText("¥"+mFoodEntity.getPrice());
            foodScore.setText(mFoodEntity.getStars()+"分");
            rb.setRating(Float.parseFloat(mFoodEntity.getStars()));
//            foodEvaluation.setText(" "+mFoodEntity.getEvaluation()+"条");
            foodInfo.setText(mFoodEntity.getIntroduction());
            foodInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
            ImageLoader.getInstance().displayImage(mFoodEntity.getImage(), foodPicView, options/*, null*/);
        }
    }
    
    OnClickListener dialogListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.ok:
                RequestUtils.insertAFoodToUnOrder(mFoodEntity, FoodInfoActivity.this);
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
    AlertDialog dialog;

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.food_add_to_cart:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_confirm, null);
            TextView tv =  (TextView) view.findViewById(R.id.dialog_text);
            tv.setText("添加到待下单列表？");
            Button okBtn = (Button) view.findViewById(R.id.ok);
            okBtn.setText("好的");
            Button cancelBtn = (Button) view.findViewById(R.id.cancel);
            okBtn.setOnClickListener(dialogListener);
            cancelBtn.setOnClickListener(dialogListener);
            builder.setView(view);
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            break;
        case R.id.food_comment_back:
            Log.i("hao", "comments click back.. ");
            if (mainContentRight.getVisibility()==View.GONE && mainContentRightComments.getVisibility()==View.VISIBLE) {
                mainContentRight.setVisibility(View.VISIBLE);
                mainContentRightComments.setVisibility(View.GONE);
            } else {
                new Thread(){
                    public void run() {
                        try{
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        }
                        catch (Exception e) {
                            Log.e("Exception when onBack", e.toString());
                        }
                    }
                }.start();
            }
            break;
        case R.id.top_panel_back:
            Log.i("hao", "HomeFragment click back.. ");
            if (mainContentRight.getVisibility()==View.GONE && mainContentRightComments.getVisibility()==View.VISIBLE) {
                mainContentRight.setVisibility(View.VISIBLE);
                mainContentRightComments.setVisibility(View.GONE);
            } else {
                new Thread(){
                    public void run() {
                        try{
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        }
                        catch (Exception e) {
                            Log.e("Exception when onBack", e.toString());
                        }
                    }
                }.start();
                detailBackView.setVisibility(View.GONE);
            }
            break;
        case R.id.food_comment_num:
            Log.i("hao", "HomeFragment onClick food_comment_num");
            foodEvaluation2.setText(foodEvaluation.getText());
            mainContentRight.setVisibility(View.GONE);
            mainContentRightComments.setVisibility(View.VISIBLE);
            break;
        case R.id.food_introduction:
            Log.i("hao", "HomeFragment onClick food_introduction");
            foodIntroduction.setTextColor(getResources().getColor(R.color.tct_black));
            foodIntroduction.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            foodIngredient.setTextColor(getResources().getColor(R.color.tct_gray));
            foodIngredient.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            foodInfo.setText(mFoodEntity.getIntroduction());
//            ingredientLineView.setBackground(getResources().getColor(R.color.tct_gray));
            ingredientLineView.setBackgroundColor(getResources().getColor(R.color.tct_gray));
            introductionLineView.setBackgroundColor(getResources().getColor(R.color.tct_black));
            break;
        case R.id.food_ingredient:
            Log.i("hao", "HomeFragment onClick food_ingredient");
            foodIngredient.setTextColor(getResources().getColor(R.color.tct_black));
            foodIngredient.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            foodIntroduction.setTextColor(getResources().getColor(R.color.tct_gray));
            foodIntroduction.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            foodInfo.setText(mFoodEntity.getIngredient());
            ingredientLineView.setBackgroundColor(getResources().getColor(R.color.tct_black));
            introductionLineView.setBackgroundColor(getResources().getColor(R.color.tct_gray));
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
        case R.id.top_panel_ring_pic:
            Log.i("hao", "HomeFragment onClick top_panel_ring_pic");
            callView.setVisibility(View.GONE);
            callingView.setVisibility(View.VISIBLE);
            break;
        case R.id.top_panel_ringing_pic:
            Log.i("hao", "HomeFragment onClick top_panel_ringing_pic");
            callView.setVisibility(View.VISIBLE);
            callingView.setVisibility(View.GONE);
            break;
        default:
            break;
        }
    }
    
    class CommentsAdapter extends BaseAdapter{
        ViewHodler vHodler;

        @Override
        public int getCount() {
            return evaluationList.size();
        }

        @Override
        public Object getItem(int position) {
            return evaluationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            vHodler = new ViewHodler();
            
            if (convertView == null) {
                vHodler = new ViewHodler();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.comments_list_item, null);
                vHodler.content = (TextView) convertView.findViewById(R.id.comments_content);
                vHodler.time = (TextView) convertView.findViewById(R.id.comments_time);
                vHodler.rBar = (RatingBar) convertView.findViewById(R.id.comments_ratingBar);
//                vHodler.delButton = (Button) convertView.findViewById(R.id.delete_order);
//                vHodler.hurryButton = (Button) convertView.findViewById(R.id.hurryup_order);
                convertView.setTag(vHodler);
            } else {
                vHodler = (ViewHodler) convertView.getTag();
            }
            vHodler.content.setText(evaluationList.get(position).getContent());
            vHodler.time.setText(evaluationList.get(position).getTime());
            vHodler.rBar.setRating(Float.parseFloat(evaluationList.get(position).getEvaluation()));
            return convertView;
        }

        class ViewHodler {
            TextView content;
            TextView time;
            RatingBar rBar;
        }
    }

}
