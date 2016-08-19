package com.tct.restaurant.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.entity.OrderItem;

public class JSonParserUtils {
    public static List<FoodEntity> parseFood(String response) {
        List<FoodEntity> list = new ArrayList<FoodEntity>();
        String jsonStr = response.substring(response.indexOf("["), response.lastIndexOf("]") + 1);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = jsonArray.getJSONObject(i);
                FoodEntity foodEntity = new FoodEntity();
                foodEntity.setFID(jb.getString("FID"));
                foodEntity.setCategory(jb.getString("category"));
                foodEntity.setImage(Constants.PIC_IP + jb.getString("picURL"));
                foodEntity.setIntroduction(jb.getString("summary"));
                foodEntity.setName(jb.getString("Fname"));
                foodEntity.setIngredient(jb.getString("ingredient"));
                foodEntity.setPrice(jb.getString("price"));
                foodEntity.setSold_num(jb.getString("saleSum"));
                foodEntity.setStars(jb.getString("stars"));
                foodEntity.setEvaluation(jb.getString("evaluation"));
                list.add(foodEntity);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.d("ying", "parseFoodBycategory Error", e);
        }
        return list;
    }
    
    public static List<OrderItem> parseOrder(String response) {
        Log.d("ying", "response="+response);
        List<OrderItem> list = new ArrayList<OrderItem>();
        String jsonStr = response.substring(response.indexOf("["), response.lastIndexOf("]") + 1);
        Log.d("ying", "jsonStr="+jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = jsonArray.getJSONObject(i);
                OrderItem orderItem = new OrderItem();
                orderItem.setFid(jb.getString("FID"));
                orderItem.setLineOrder(jb.getString("lineOrder"));
                orderItem.setRemark(jb.getString("remark"));
                orderItem.setRemindNum(jb.getString("remindNum"));
                orderItem.setStatus(jb.getString("statu"));
                FoodEntity fEntity;
                for (int j = 0; j < RequestUtils.foodList.size(); j++) {
                    if (RequestUtils.foodList.get(j).getFID().equals(orderItem.getFid())) {
                        fEntity = RequestUtils.foodList.get(j);
                        orderItem.setFoodEntity(fEntity);
                        break;
                    }
                }
                list.add(orderItem);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.d("ying", "parseOrder Error", e);
        }
        return list;
    }
}
