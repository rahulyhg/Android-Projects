package com.example.uj.mebar;

/**
 * Created by UJ on 26-07-2018.
 */

public class Data {

    String id ;
    String title;
    String desc;
    int stock;
    int quan;
    int price;
    String img;
    String mSoldBy;

    public Data(){    }

    public Data(String id, String title, int quan, int price, String desc, String img, String soldBy){
        this.id =id;
        this.title= title;
        this.desc = desc;
        this.price = price;
        this.quan = quan;
        this.img = img;
        this.mSoldBy = soldBy;


    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {return desc;}

    public void setDesc(String desc) {this.desc = desc;}

    public int getStock() {return stock;}

    public void setStock(int stock) {this.stock = stock;}

    public int getQuan() {return quan;}

    public void setQuan(int quan) {this.quan = quan;}

    public int getPrice() {return price;}

    public void setPrice(int price) { this.price = price;}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getmSoldBy() {return mSoldBy;}

    public void setmSoldBy(String mSoldBy) {
        this.mSoldBy = mSoldBy;
    }


}