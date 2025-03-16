package com.example.foodorderapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.foodorderapp.Converters;
import java.io.Serializable;
import java.util.List;

@Entity(tableName = "menus")
@TypeConverters(Converters.class)
public class Menu implements Serializable { // Thêm Serializable
    @PrimaryKey(autoGenerate = true)
    private int menuId;
    private String name;
    private List<Integer> dishIds;
    private String createdAt;

    // Constructor mặc định
    public Menu() {}

    // Constructor đầy đủ
    @Ignore
    public Menu(String name, List<Integer> dishIds, String createdAt) {
        this.name = name;
        this.dishIds = dishIds;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Integer> getDishIds() { return dishIds; }
    public void setDishIds(List<Integer> dishIds) { this.dishIds = dishIds; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}