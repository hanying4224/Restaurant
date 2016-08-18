package com.tct.restaurant.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.tct.BaseApplication;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.FoodEntity;

public class RequestUtils {
    public static final int REQUEST_FOODLIST_OK = 100;
    public static List<FoodEntity> getFoodList() {
        List<FoodEntity> list = new ArrayList<FoodEntity>();
        FoodEntity fEntity;
        for (int i = 0; i < 50; i++) {
            fEntity = new FoodEntity();
            fEntity.setImage("drawable://" + R.drawable.pic_hanbao2);
            fEntity.setIntroduction("汉堡，西式快餐的领头羊，高热量... ...");
            fEntity.setName("香辣汉堡");
            fEntity.setPrice(23);
            fEntity.setSold_num(36);
            list.add(fEntity);
            fEntity = null;
        }
        return list;
    }

    public final static List<FoodEntity> foodList = new ArrayList<FoodEntity>();
    public final static List<FoodEntity> foodList_Current = new ArrayList<FoodEntity>();
    public static void requestFoodList(final String foodType, final Handler handler) {
        foodList.clear();

        String url= Constants.SERVER_IP + Constants.TABLE_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("ying", "onResponse2, response = "+ response.toString());
                        foodList.addAll(JSonParserUtils.parseFood(response));
                        Log.d("ying", "requestFoodList: foodList.size() = " + foodList.size());
                        if (foodList.size() != 0) {
                            for (FoodEntity fEntity : foodList) {
                                if (foodType.equals(fEntity.getCategory())) {
                                    foodList_Current.add(fEntity);
                                }
                            }
                            if (handler != null) {
                                handler.sendEmptyMessage(REQUEST_FOODLIST_OK);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "onErrorResponse2, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("str", "select * from foodinfo");
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }

    public static void getFoodListByType(final String foodType, final Handler handler) {
        foodList_Current.clear();
        if (foodList.size() != 0) {
            for (FoodEntity fEntity : foodList) {
                if (foodType.equals(fEntity.getCategory())) {
                    foodList_Current.add(fEntity);
                }
            }
            handler.sendEmptyMessage(REQUEST_FOODLIST_OK);
        } else {
            requestFoodList(foodType, handler);
        }
    }
}
