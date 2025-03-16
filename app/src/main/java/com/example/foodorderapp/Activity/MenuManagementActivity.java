package com.example.foodorderapp.Activity;

import android.content.Intent;
import com.example.foodorderapp.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.DAO.MenuDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Menu;
import java.util.ArrayList;
import java.util.List;

public class MenuManagementActivity extends AppCompatActivity implements MenuAdapter.OnMenuClickListener {
    private RecyclerView menuRecyclerView;
    private Button addMenuButton;
    private UserRoomDatabase database;
    private MenuDao menuDao;
    private List<Menu> menuList;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        addMenuButton = findViewById(R.id.addMenuButton);

        database = UserRoomDatabase.getInstance(this);
        menuDao = database.menuDao();

        menuList = new ArrayList<>();
        menuAdapter = new MenuAdapter(this, menuList, this);

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(menuAdapter);

        loadMenus();

        addMenuButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditMenuActivity.class));
        });
    }

    private void loadMenus() {
        menuList.clear();
        menuList.addAll(menuDao.getAllMenus());
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(Menu menu) {
        Intent intent = new Intent(this, AddEditMenuActivity.class);
        intent.putExtra("menu", menu);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Menu menu) {
        menuDao.deleteMenu(menu);
        Toast.makeText(this, "Xóa thực đơn thành công", Toast.LENGTH_SHORT).show();
        loadMenus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMenus();
    }
}