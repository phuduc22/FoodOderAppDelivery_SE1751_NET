package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Xóa thông tin đăng nhập khỏi SharedPreferences (nếu có)
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();  // Xóa toàn bộ dữ liệu
        editor.apply();

        // Nếu dùng Firebase Authentication thì logout
        FirebaseAuth.getInstance().signOut();

        // Quay về màn hình Login
        Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack activity
        startActivity(intent);
        finish(); // Kết thúc activity hiện tại
    }
}
