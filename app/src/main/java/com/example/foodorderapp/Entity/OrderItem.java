package com.example.foodorderapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "order_items")
public class OrderItem implements Serializable { // Thêm Serializable
    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private int orderId;
    private int dishId;
    private String name;
    private int quantity;
    private double price;

    // Constructor mặc định
    public OrderItem() {}

    // Constructor đầy đủ
    @Ignore
    public OrderItem(int orderId, int dishId, String name, int quantity, double price) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter và Setter
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getDishId() { return dishId; }
    public void setDishId(int dishId) { this.dishId = dishId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}