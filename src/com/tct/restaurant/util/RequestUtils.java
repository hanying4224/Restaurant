package com.tct.restaurant.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tct.BaseApplication;
import com.tct.restaurant.R;
import com.tct.restaurant.entity.EvaluationItem;
import com.tct.restaurant.entity.FoodEntity;
import com.tct.restaurant.entity.OrderItem;

public class RequestUtils {
    public static final int REQUEST_FOODLIST_OK = 100;
    public static final int REQUEST_USERORDER_OK = 101;
    public static final int REQUEST_USERUNORDER_OK = 102;
	public static final int REQUEST_EVALUATIONLIST_OK = 103;
    public static final int REQUEST_NOK = -1;

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
                        Log.d("ying", "onErrorResponse(requestFoodList), error = " + error.toString());
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

    public final static List<EvaluationItem> evaluationList = new ArrayList<EvaluationItem>();
    public static void requestEvaluationList(final String foodID, final Handler handler) {
        evaluationList.clear();

        String url= Constants.SERVER_IP + Constants.TABLE_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        evaluationList.addAll(JSonParserUtils.parseEvaluation(response));
                        Log.d("hao", "requestEvaluationList: evaluationList.size() = " + evaluationList.size());
                        if (evaluationList.size() != 0) {
                            if (handler != null) {
                                handler.sendEmptyMessage(REQUEST_EVALUATIONLIST_OK);
                            }
                        } else {
                            if (handler != null) {
                                handler.sendEmptyMessage(REQUEST_NOK);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("hao", "onErrorResponse(requestFoodList), error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("str", "select * from evaluateinfo where FID="+foodID);
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }

    public static void getFoodListByType(final String foodType, final Handler handler) {
        foodList_Current.clear();
        if (foodList.size() != 0) {
            for (FoodEntity fEntity : foodList) {
                if (foodType!=null && foodType.equals(fEntity.getCategory())) {
                    foodList_Current.add(fEntity);
                }
            }
            handler.sendEmptyMessage(REQUEST_FOODLIST_OK);
        } else {
            requestFoodList(foodType, handler);
        }
    }

    public final static List<OrderItem> userOrderList = new ArrayList<OrderItem>();
    public static void requestOrderList(final String userId, final Handler handler){
        userOrderList.clear();

        String url= Constants.SERVER_IP + Constants.TABLE_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userOrderList.clear();
                        userOrderList.addAll(JSonParserUtils.parseOrder(response));
                        Log.d("ying", "requestOrderList: userOrderList.size() = " + userOrderList.size());
//                        if (userOrderList.size() != 0) {
                            if (handler != null) {
                                handler.sendEmptyMessage(REQUEST_USERORDER_OK);
                            }
                            
//                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "onErrorResponse(getUserOrderList), error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("str", "select * from orderinfo where UID= "+ userId+" and statu = 1");
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }

    public static void getUserOrderList(final String userId, final Handler handler){
        if (userOrderList.size() != 0) {
            handler.sendEmptyMessage(REQUEST_USERORDER_OK);
        } else {
            requestOrderList(userId, handler);
        }
    }
    
    
    public final static List<OrderItem> userUnOrderList = new ArrayList<OrderItem>();
    public static void requestUnOrderList(final String userId, final Handler handler){
        userUnOrderList.clear();

        String url= Constants.SERVER_IP + Constants.TABLE_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userUnOrderList.clear();
                        userUnOrderList.addAll(JSonParserUtils.parseOrder(response));
                        Log.d("ying", "requestUnOrderList: userUnOrderList.size() = " + userUnOrderList.size());
//                        if (userUnOrderList.size() != 0) {
                            if (handler != null) {
                                handler.sendEmptyMessage(REQUEST_USERUNORDER_OK);
                            }
//                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "requestUnOrderList, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("str", "select * from orderinfo where UID= "+ userId+" and statu = 3");
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }

    public static void getUserUnOrderList(final String userId, final Handler handler){
        if (userUnOrderList.size() != 0) {
            handler.sendEmptyMessage(REQUEST_USERUNORDER_OK);
        } else {
            requestUnOrderList(userId, handler);
        }
    }


    public static void insertAFoodToServerOrder(final FoodEntity fEntity, final Context context) {
        String url= Constants.SERVER_IP + Constants.NOEN_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //更新订单
                        requestOrderList(Constants.USER_ID, null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                context.sendBroadcast(new Intent("tct.restaurant.updateorder"));
                            }
                        }, 3000);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "insertAFoodToServerOrder, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String sql = "insert into orderinfo (UID, FID, num, statu) values ('"+Constants.USER_ID+"','"+fEntity.getFID()+"','1','1')";
                Log.d("ying", "sql = "+ sql);
                map.put("str", sql);
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }

    public static void insertAFoodToUnOrder(final FoodEntity fEntity, final Context context) {
        String url= Constants.SERVER_IP + Constants.NOEN_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //更新购物车
                        requestUnOrderList(Constants.USER_ID, null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "insertAFoodToUnOrder, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String sql = "insert into orderinfo (UID, FID, num, statu) values ('"+Constants.USER_ID+"','"+fEntity.getFID()+"','1','3')";
                Log.d("ying", "sql = "+ sql);
                map.put("str", sql);
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }
    
    public static void delAFoodFromUnOrder(final FoodEntity fEntity, final Handler handler) {
        String url= Constants.SERVER_IP + Constants.NOEN_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //更新购物车
                        requestUnOrderList(Constants.USER_ID, handler);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "delAFoodFromUnOrder, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String sql = "delete from orderinfo where UID="+Constants.USER_ID+" and FID="+fEntity.getFID()+" and statu=3";
                Log.d("ying", "sql = "+ sql+" , del-name="+fEntity.getName());
                map.put("str", sql);
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }
    
    public static void delAFoodFromOrder(final FoodEntity fEntity, final Handler handler) {
        String url= Constants.SERVER_IP + Constants.NOEN_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //更新订单
                        requestOrderList(Constants.USER_ID, handler);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "delAFoodFromOrder, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String sql = "delete from orderinfo where UID="+Constants.USER_ID+" and FID="+fEntity.getFID()+" and statu=1";
                Log.d("ying", "sql = "+ sql+" , del-name="+fEntity.getName());
                map.put("str", sql);
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }


    public static void insertAFoodListToServerOrder(final List<OrderItem> fList) {
        String url= Constants.SERVER_IP + Constants.NOEN_QUERY;
        StringRequest request = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ying", "insertAFoodListToServerOrder, error = " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String sql = "";
                for (OrderItem oEntity : fList) {
                    sql += "insert into orderinfo (UID, FID, num) values ('"
                            +Constants.USER_ID+"','"+oEntity.getFid()+"','"+oEntity.getNum()+"');";
                }
                map.put("str", sql);
                return map;
            }
        };
        BaseApplication.getHttpRequestQueue().add(request);
    }
}
