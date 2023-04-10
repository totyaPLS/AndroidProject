package com.app.nutritionalsupplements.models;

import android.widget.ImageView;

public class Product {
    private final int image;
    private final String name;
    private final float rateValue;
    private final String price;
    private String description;
    private int weightGram;

    public Product(int image, String name, float rateValue, String price) {
        this.image = image;
        this.name = name;
        this.rateValue = rateValue;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public float getRateValue() {
        return rateValue;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getWeightGram() {
        return weightGram;
    }
}
