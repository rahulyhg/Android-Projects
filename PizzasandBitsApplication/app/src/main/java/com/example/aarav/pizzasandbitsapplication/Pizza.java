package com.example.aarav.pizzasandbitsapplication;

public class Pizza {
    private String name;
    private int imageResourceId;

    public static final Pizza[] pizzas={
            new Pizza("Diavolo",R.drawable.diavolo),
            new Pizza("Funghi",R.drawable.funghi)
    };

    private Pizza(String name,int imageResurceId){
        this.name=name;
        this.imageResourceId=imageResurceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String toString() {
        return name;
    }
}
