package com.tct.restaurant.entity;

import java.io.Serializable;

public class EvaluationItem implements Serializable{

    private static final long serialVersionUID = 1L;

    private String EID;
    private String UID;
    private String FID;
    private String PID;
    private String content;
    private FoodEntity foodEntity;
    private String time;
    private String evaluation;
    private String Image;

    public String getEID() {
        return EID;
    }
    public void setEID(String eID) {
        EID = eID;
    }
    public String getUID() {
        return UID;
    }
    public void setUID(String uID) {
        UID = uID;
    }
    public String getFID() {
        return FID;
    }
    public void setFID(String fID) {
        FID = fID;
    }
    public String getPID() {
        return PID;
    }
    public void setPID(String pID) {
        PID = pID;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public FoodEntity getFoodEntity() {
        return foodEntity;
    }
    public void setFoodEntity(FoodEntity foodEntity) {
        this.foodEntity = foodEntity;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getEvaluation() {
        return evaluation;
    }
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }
}
