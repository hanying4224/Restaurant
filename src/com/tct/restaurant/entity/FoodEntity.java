package com.tct.restaurant.entity;

import java.io.Serializable;

public class FoodEntity implements Serializable{

	private static final long serialVersionUID = -6453430021123563721L;
	public String image = "";
	public String name= "";
	public double price;
	public String introduction= "";
	public String sold_num= "";
    public String getImage() {
        return image;
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
    public String getSold_num() {
        return sold_num;
    }
    public void setSold_num(String sold_num) {
        this.sold_num = sold_num;
    }

	
}
