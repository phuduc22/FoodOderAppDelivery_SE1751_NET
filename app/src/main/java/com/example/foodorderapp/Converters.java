package com.example.foodorderapp;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.foodorderapp.Entity.OrderItem;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    // Chuyển đổi List<Integer> (dùng cho Menu.dishIds)
    @TypeConverter
    public static List<Integer> fromIntegerListString(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toIntegerListString(List<Integer> list) {
        if (list == null) {
            return null;
        }
        return gson.toJson(list);
    }

    // Chuyển đổi List<OrderItem> (dùng cho Order.items)
    @TypeConverter
    public static List<OrderItem> fromOrderItemListString(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<OrderItem>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toOrderItemListString(List<OrderItem> list) {
        if (list == null) {
            return null;
        }
        return gson.toJson(list);
    }
}