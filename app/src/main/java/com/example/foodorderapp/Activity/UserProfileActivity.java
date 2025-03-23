package com.example.foodorderapp.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.User;
import com.example.foodorderapp.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    private TextView userName, userEmail, userPhone, userRole, userStatus, userCreatedAt, userUpdatedAt;
    private ImageView userAvatar;
    private Button btnEditProfile, btnLogout;

    private UserRoomDatabase userRoomDatabase;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Ánh xạ View
        userAvatar = findViewById(R.id.userAvatar);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userRole = findViewById(R.id.userRole);
        userStatus = findViewById(R.id.userStatus);
        userCreatedAt = findViewById(R.id.userCreatedAt);
        userUpdatedAt = findViewById(R.id.userUpdatedAt);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Lấy dữ liệu người dùng từ Database
        userRoomDatabase = UserRoomDatabase.getInstance(this);

        new Thread(() -> {
            // Giả sử bạn đã lưu email của người đăng nhập vào SharedPreferences
            String loggedInEmail = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("username", null);
            if (loggedInEmail != null) {
                currentUser = userRoomDatabase.userDao().getUserByEmail(loggedInEmail);

                if (currentUser != null) {
                    runOnUiThread(() -> updateUI(currentUser));
                }
            }
        }).start();

        // Sự kiện khi nhấn vào nút Đăng xuất
        btnLogout.setOnClickListener(view -> {
            getSharedPreferences("USER_PREFS", MODE_PRIVATE).edit().remove("LOGGED_IN_EMAIL").apply();
            startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
            finish();
        });

        // Sự kiện khi nhấn vào nút Chỉnh sửa hồ sơ (Chưa triển khai)
        btnEditProfile.setOnClickListener(view ->
                Toast.makeText(UserProfileActivity.this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        );
    }

    // Cập nhật giao diện với dữ liệu từ Database
    private void updateUI(User user) {
        userName.setText(user.getUsername());
        userEmail.setText(user.getEmail());
        userPhone.setText(user.getPhoneNumber() != null ? user.getPhoneNumber() : "No phone number");
        userRole.setText("Role: " + user.getRole());
        userStatus.setText("Status: " + user.getStatus());

        // Format thời gian từ timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        userCreatedAt.setText("Created At: " + sdf.format(user.getCreatedAt()));
        userUpdatedAt.setText("Updated At: " + sdf.format(user.getUpdatedAt()));
    }
}