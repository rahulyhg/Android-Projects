package com.example.uj.mebarvendor;

public class Upload {

    private String title;
    private String ImageUrl;
    private String Seller;
    private String Desc;
    private int Price;
    private int quan;


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    private String User;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan() {
        this.quan = 0;
    }



    public Upload(){}

    public Upload(String Title, String img, String desc, int price, String seller, String user){
        title = Title;
        ImageUrl = img;
        Desc = desc;
        Price = price;
        Seller = seller;
        User = user;
    }
}
