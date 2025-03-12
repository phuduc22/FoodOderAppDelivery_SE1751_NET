package com.example.foodorderapp.DAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.foodorderapp.Entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                UserRoomDatabase.class, "user_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }
}
