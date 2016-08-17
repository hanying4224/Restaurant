package com.tct.restaurant.util;

import java.util.ArrayList;
import java.util.List;

import com.tct.restaurant.R;
import com.tct.restaurant.entity.FoodEntity;

public class RequestUtils {
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
}
