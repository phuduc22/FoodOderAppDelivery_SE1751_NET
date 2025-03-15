package com.example.foodorderapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderapp.Entity.Menu;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * FROM menus")
    List<Menu> getAllMenus();

    @Query("SELECT * FROM menus WHERE menuId = :menuId")
    Menu getMenuById(int menuId);

    @Insert
    void insertMenu(Menu menu);

    @Update
    void updateMenu(Menu menu);

    @Delete
    void deleteMenu(Menu menu);
}