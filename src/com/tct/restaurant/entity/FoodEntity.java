package com.tct.restaurant.entity;

import java.io.Serializable;

public class FoodEntity implements Serializable{

	@Override
    public String toString() {
        return "FoodEntity [image=" + image + ", FID=" + FID + ", name=" + name + ", price="
                + price + ", introduction=" + introduction + ", sold_num="
                + sold_num + ", category=" + category + ", stars=" + stars
                + ", evaluation=" + evaluation + ", ingredient=" + ingredient + "]";
    }
    private static final long serialVersionUID = -6453430021123563721L;
    private int FID;
	private String image = "";
	private String name= "";
	private double price;
	private String introduction= "";
	private int sold_num;
	private String category;
	private String ingredient;
	private float stars;
	private String evaluation = "";

	public int getFID() {
        return FID;
    }
    public void setFID(int fID) {
        FID = fID;
    }
    public String getIngredient() {
        return ingredient;
    }
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
    public String getImage() {
        return image;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public int getSold_num() {
        return sold_num;
    }
    public void setSold_num(int sold_num) {
        this.sold_num = sold_num;
    }
    public float getStars() {
        return stars;
    }
    public void setStars(float stars) {
        this.stars = stars;
    }
    public String getEvaluation() {
        return evaluation;
    }
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

	
}
