package com.example.foodorderapp.Activity;

import android.os.Bundle;
import com.example.foodorderapp.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.DAO.DishDao;
import com.example.foodorderapp.DAO.MenuDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Dish;
import com.example.foodorderapp.Entity.Menu;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddEditMenuActivity extends AppCompatActivity {
    private EditText menuNameEditText;
    private RecyclerView dishSelectionRecyclerView;
    private Button saveMenuButton;
    private UserRoomDatabase database;
    private DishDao dishDao;
    private MenuDao menuDao;
    private Menu menu;
    private List<Dish> dishList;
    private List<Integer> selectedDishIds;
    private DishSelectionAdapter dishSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu);

        menuNameEditText = findViewById(R.id.menuNameEditText);
        dishSelectionRecyclerView = findViewById(R.id.dishSelectionRecyclerView);
        saveMenuButton = findViewById(R.id.saveMenuButton);

        database = UserRoomDatabase.getInstance(this);
        dishDao = database.dishDao();
        menuDao = database.menuDao();

        dishList = new ArrayList<>();
        selectedDishIds = new ArrayList<>();

        dishSelectionAdapter = new DishSelectionAdapter(this, dishList, selectedDishIds);
        dishSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishSelectionRecyclerView.setAdapter(dishSelectionAdapter);

        menu = (Menu) getIntent().getSerializableExtra("menu");
        if (menu != null) {
            menuNameEditText.setText(menu.getName());
            selectedDishIds.addAll(menu.getDishIds());
        }

        loadDishes();

        saveMenuButton.setOnClickListener(v -> saveMenu());
    }

    private void loadDishes() {
        dishList.clear();
        dishList.addAll(dishDao.getAllDishes());
        dishSelectionAdapter.notifyDataSetChanged();
    }

    private void saveMenu() {
        String name = menuNameEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên thực đơn", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDishIds.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một món ăn", Toast.LENGTH_SHORT).show();
            return;
        }

        if (menu == null) {
            String createdAt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            menu = new Menu(name, selectedDishIds, createdAt);
            menuDao.insertMenu(menu);
            Toast.makeText(this, "Thêm thực đơn thành công", Toast.LENGTH_SHORT).show();
        } else {
            menu.setName(name);
            menu.setDishIds(selectedDishIds);
            menuDao.updateMenu(menu);
            Toast.makeText(this, "Cập nhật thực đơn thành công", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}