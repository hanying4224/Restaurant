package com.tct.restaurant.entity;

import java.io.Serializable;

public class OrderItem implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String fid;
    private String status;
    private String remark;
    private String lineOrder;
    private String remindNum;
    private FoodEntity foodEntity;
    private String num;
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public FoodEntity getFoodEntity() {
        return foodEntity;
    }
    public void setFoodEntity(FoodEntity foodEntity) {
        this.foodEntity = foodEntity;
    }
    public String getFid() {
        return fid;
    }
    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getLineOrder() {
        return lineOrder;
    }
    public void setLineOrder(String lineOrder) {
        this.lineOrder = lineOrder;
    }
    public String getRemindNum() {
        return remindNum;
    }
    public void setRemindNum(String remindNum) {
        this.remindNum = remindNum;
    }

    
}
