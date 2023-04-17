package com.app.nutritionalsupplements.models;

public class Product {
    private String id;
    private int image;
    private String name;
    private float rateValue;
    private String price;
    private int cartedCount;
    private String nameInLowerCase;

    public Product() {}

    public Product(int image, String name, float rateValue, String price, int cartedCount) {
        this.image = image;
        this.name = name;
        this.rateValue = rateValue;
        this.price = price;
        this.cartedCount = cartedCount;
        this.nameInLowerCase = name.toLowerCase();
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

    public int getCartedCount() {
        return cartedCount;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameInLowerCase() {
        return nameInLowerCase;
    }
}
