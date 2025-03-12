package com.example.foodorderapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.R;
import com.example.foodorderapp.databinding.ActivityIntroBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
