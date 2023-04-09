package com.app.nutritionalsupplements.models;

public class Product {
    private String name;
    private float rateValue;
    private int price;
    private String description;
    private int weightGram;

    public Product() {}

    public Product(String name, float rateValue, int price, String description, int weightGram) {
        this.name = name;
        this.rateValue = rateValue;
        this.price = price;
        this.description = description;
        this.weightGram = weightGram;
    }

    public String getName() {
        return name;
    }

    public float getRateValue() {
        return rateValue;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getWeightGram() {
        return weightGram;
    }
}
