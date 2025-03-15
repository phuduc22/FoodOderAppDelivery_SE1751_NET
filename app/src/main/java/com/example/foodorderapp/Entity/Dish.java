package com.example.foodorderapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "dishes")
public class Dish implements Serializable { // Thêm Serializable
    @PrimaryKey(autoGenerate = true)
    private int dishId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private boolean isAvailable;

    // Constructor mặc định
    public Dish() {}

    // Constructor đầy đủ
    @Ignore
    public Dish(String name, String description, double price, String imageUrl, String category, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Getter và Setter
    public int getDishId() { return dishId; }
    public void setDishId(int dishId) { this.dishId = dishId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}