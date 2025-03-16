package com.example.foodorderapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderapp.Entity.Revenue;

import java.util.List;

@Dao
public interface RevenueDao {
    @Query("SELECT * FROM revenue")
    List<Revenue> getAllRevenue();

    @Query("SELECT * FROM revenue WHERE period = :period")
    Revenue getRevenueByPeriod(String period);

    @Insert
    void insertRevenue(Revenue revenue);

    @Update
    void updateRevenue(Revenue revenue);

    @Delete
    void deleteRevenue(Revenue revenue);
}