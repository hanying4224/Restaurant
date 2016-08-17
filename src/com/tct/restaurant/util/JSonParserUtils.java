package com.tct.restaurant.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.tct.restaurant.entity.FoodEntity;

public class JSonParserUtils {
    public static List<FoodEntity> parseFoodBycategory(String response, String foodType) {
        List<FoodEntity> list = new ArrayList<FoodEntity>();
        String jsonStr = response.substring(response.indexOf("["), response.lastIndexOf("]") + 1);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = jsonArray.getJSONObject(i);
                FoodEntity foodEntity = new FoodEntity();
                foodEntity.setCategory(jb.getString("category"));
                foodEntity.setImage(jb.getString("pic"));//not really pic
                foodEntity.setIntroduction(jb.getString("summary"));
                foodEntity.setName(jb.getString("Fname"));
                foodEntity.setIngredient(jb.getString("ingredient"));
                foodEntity.setPrice(jb.getDouble("price"));
                foodEntity.setSold_num(jb.getInt("saleSum"));
                if (foodType.equals(foodEntity.getCategory())) {
                    list.add(foodEntity);
                }
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.d("ying", "parseFoodBycategory Error", e);
        }
        return list;
    }
}
