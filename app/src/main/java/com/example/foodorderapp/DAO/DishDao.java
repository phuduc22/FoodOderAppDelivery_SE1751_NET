package com.example.foodorderapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderapp.Entity.Dish;

import java.util.List;

@Dao
public interface DishDao {
    @Query("SELECT * FROM dishes")
    List<Dish> getAllDishes();

    @Query("SELECT * FROM dishes WHERE dishId = :dishId")
    Dish getDishById(int dishId);

    @Insert
    void insertDish(Dish dish);

    @Update
    void updateDish(Dish dish);

    @Delete
    void deleteDish(Dish dish);
}