package com.example.foodorderapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.foodorderapp.Converters;
import java.io.Serializable;
import java.util.List;

@Entity(tableName = "orders")
@TypeConverters(Converters.class)
public class Order implements Serializable { // Thêm Serializable
    @PrimaryKey(autoGenerate = true)
    private int orderId;
    private String customerName;
    private List<OrderItem> items;
    private double totalAmount;
    private String status;
    private String createdAt;
    private String updatedAt;

    // Constructor mặc định
    public Order() {}

    // Constructor đầy đủ
    @Ignore
    public Order(String customerName, List<OrderItem> items, double totalAmount, String status, String createdAt, String updatedAt) {
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter và Setter
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}