package com.example.foodorderapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Dish;
import com.example.foodorderapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button logoutBtn,btnBackList,btnNextList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView navHeaderTitle;
    private View headerView;
    private RecyclerView recyclerView;
    private DishDisplayAdapter adapter;
    private List<Dish> foodList;
    private UserRoomDatabase database;
    private static int currentPageList = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest"); // Mặc định là "Guest"

        // Lấy header view của NavigationView
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);

        // Tìm TextView trong header và cập nhật tên
        navHeaderTitle = headerView.findViewById(R.id.nav_header_title);
        navHeaderTitle.setText(username);

        // Ánh xạ
        drawerLayout = findViewById(R.id.drawerLayout);
        logoutBtn = findViewById(R.id.logoutBtn);
        btnBackList = findViewById(R.id.btnBackButton);
        btnNextList = findViewById(R.id.btnNextButton);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Thêm nút Toggle để mở Drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Xử lý khi nhấn vào các mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(MainActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(MainActivity.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_dish_management) { // Thêm mục mới
                    Toast.makeText(MainActivity.this, "Dish Management Selected", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, DishManagementActivity.class));
                } else if (id == R.id.nav_menu_management) { // Thêm mục mới
                    Toast.makeText(MainActivity.this, "Menu Management Selected", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MenuManagementActivity.class));
                } else if (id == R.id.nav_new_orders) { // Thêm mục mới
                    Toast.makeText(MainActivity.this, "New Orders Selected", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, NewOrdersActivity.class));
                } else if (id == R.id.nav_revenue_stats) { // Thêm mục mới
                    Toast.makeText(MainActivity.this, "Revenue Statistics Selected", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, RevenueStatisticsActivity.class));
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                    logoutUser();
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        database = UserRoomDatabase.getInstance(this);
        List<Dish> foodListDatabase = database.dishDao().getAllDishes();
        if (foodListDatabase.isEmpty()) { // Kiểm tra danh sách trống
            List<Dish> defaultDishes = Arrays.asList(
                    new Dish("Phở Bò", "Phở bò truyền thống Việt Nam", 60000,
                            "phobovietnam", "Món chính", true),
                    new Dish("Bánh Mì", "Bánh mì kẹp thịt thơm ngon", 30000,
                            "banhmikepthit", "Ăn nhẹ", true),
                    new Dish("Bún Chả", "Bún chả Hà Nội ngon tuyệt", 50000,
                            "bunchahanoi", "Món chính", true),
                    new Dish("Gỏi Cuốn", "Gỏi cuốn tôm thịt với nước chấm đặc biệt", 40000,
                            "goicuontomthit", "Khai vị", true),
                    new Dish("Chè Ba Màu", "Món chè truyền thống với đậu xanh, đậu đỏ và nước cốt dừa", 25000,
                            "chebamau", "Tráng miệng", true)
            );

            for (Dish dish : defaultDishes) {
                database.dishDao().insertDish(dish); // Chèn từng món vào database
            }
        }
            btnBackList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentPageList > 0) {
                        currentPageList--;
                        updateFoodList(foodListDatabase);
                    }
                }
            });

            btnNextList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((currentPageList * 3) + 3 < foodListDatabase.size()) {
                        currentPageList++;
                        updateFoodList(foodListDatabase);
                    }
                }
            });

            // Ánh xạ RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            int toIndex = Math.min(3, foodListDatabase.size());
            foodList = foodListDatabase.subList(0, toIndex);

            // Gán Adapter vào RecyclerView
            adapter = new DishDisplayAdapter(this, foodList);
            recyclerView.setAdapter(adapter);



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }
    private void updateFoodList(List<Dish> foodListDatabase) {
        if (foodListDatabase == null || foodListDatabase.isEmpty()) return;
        int fromIndex = currentPageList * 3;
        int toIndex = Math.min(fromIndex + 3, foodListDatabase.size());
        List<Dish> pagedList = foodListDatabase.subList(fromIndex, toIndex);

        adapter = new DishDisplayAdapter(this, pagedList);
        recyclerView.setAdapter(adapter);
    }

    private void logoutUser() {
        // Xóa dữ liệu đăng nhập (nếu dùng SharedPreferences)
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack
        startActivity(intent);
        finish();
    }
}