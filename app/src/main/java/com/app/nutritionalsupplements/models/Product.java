package com.app.nutritionalsupplements.models;

public class Product {
    private int image;
    private String name;
    private float rateValue;
    private String price;

    public Product() {}

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
}
