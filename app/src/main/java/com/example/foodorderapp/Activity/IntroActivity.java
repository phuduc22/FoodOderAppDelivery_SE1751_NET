package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.foodorderapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {

    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();

        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                }
            }
        });
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, SignupActivity.class));
            }
        });
    }
}