package com.example.foodorderapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "revenue")
public class Revenue {
    @PrimaryKey(autoGenerate = true)
    private int revenueId;
    private String period; // Ngày/tháng/năm (ví dụ: "2025-03-14")
    private double totalRevenue;
    private int orderCount;

    // Constructor mặc định
    public Revenue() {}

    // Constructor đầy đủ
    @Ignore
    public Revenue(String period, double totalRevenue, int orderCount) {
        this.period = period;
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
    }

    // Getter và Setter
    public int getRevenueId() { return revenueId; }
    public void setRevenueId(int revenueId) { this.revenueId = revenueId; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }
}