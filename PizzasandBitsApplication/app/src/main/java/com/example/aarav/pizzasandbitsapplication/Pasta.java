package com.example.aarav.pizzasandbitsapplication;

public class Pasta {
    private String name;
    private int imageResourceId;

    public static final Pasta[] pastas={
            new Pasta("Spaghetti Bolognese",R.drawable.pastachick),
            new Pasta("Lasagne",R.drawable.lasagne)
    };

    private Pasta(String name,int imageResurceId){
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
