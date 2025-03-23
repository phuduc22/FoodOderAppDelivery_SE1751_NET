package com.example.foodorderapp.DAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.foodorderapp.Converters;
import com.example.foodorderapp.Entity.Dish;
import com.example.foodorderapp.Entity.Feedback;
import com.example.foodorderapp.Entity.Menu;
import com.example.foodorderapp.Entity.Order;
import com.example.foodorderapp.Entity.OrderItem;
import com.example.foodorderapp.Entity.Revenue;
import com.example.foodorderapp.Entity.User;

@Database(entities = {User.class, Feedback.class, Order.class, OrderItem.class, Dish.class, Menu.class, Revenue.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DishDao dishDao();
    public abstract MenuDao menuDao();
    public abstract OrderDao orderDao();
    public abstract FeedbackDao feedbackDao();

    public abstract OrderItemDao orderItemDao(); // Thêm OrderItemDao
    public abstract RevenueDao revenueDao();

    private static UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                UserRoomDatabase.class, "user_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries() // Chỉ dùng trong giai đoạn phát triển
                        .build();
            }
        }
        return INSTANCE;
    }
}