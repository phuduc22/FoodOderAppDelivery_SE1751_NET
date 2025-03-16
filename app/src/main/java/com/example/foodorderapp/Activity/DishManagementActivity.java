package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import com.example.foodorderapp.R;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.DAO.DishDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Dish;
import java.util.ArrayList;
import java.util.List;

public class DishManagementActivity extends AppCompatActivity implements DishAdapter.OnDishClickListener {
    private RecyclerView dishRecyclerView;
    private Button addDishButton;
    private UserRoomDatabase database;
    private DishDao dishDao;
    private List<Dish> dishList;
    private DishAdapter dishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_management);

        dishRecyclerView = findViewById(R.id.dishRecyclerView);
        addDishButton = findViewById(R.id.addDishButton);

        database = UserRoomDatabase.getInstance(this);
        dishDao = database.dishDao();

        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishList, this);

        dishRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishRecyclerView.setAdapter(dishAdapter);

        loadDishes();

        addDishButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditDishActivity.class));
        });
    }

    private void loadDishes() {
        dishList.clear();
        dishList.addAll(dishDao.getAllDishes());
        dishAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(Dish dish) {
        Intent intent = new Intent(this, AddEditDishActivity.class);
        intent.putExtra("dish", dish);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Dish dish) {
        dishDao.deleteDish(dish);
        Toast.makeText(this, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
        loadDishes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDishes();
    }
}