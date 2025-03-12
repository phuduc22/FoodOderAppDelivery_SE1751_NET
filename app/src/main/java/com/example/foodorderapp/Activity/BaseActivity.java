package com.example.foodorderapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.R;

public class BaseActivity extends AppCompatActivity {

    public String TAG = "uilover";
    protected UserRoomDatabase userRoomDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRoomDatabase = UserRoomDatabase.getInstance(this);

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}
