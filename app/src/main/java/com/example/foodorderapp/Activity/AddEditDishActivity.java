package com.example.foodorderapp.Activity;

import android.os.Bundle;
import com.example.foodorderapp.R;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderapp.DAO.DishDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Dish;

public class AddEditDishActivity extends AppCompatActivity {
    private EditText dishNameEditText, dishDescriptionEditText, dishPriceEditText, dishCategoryEditText;
    private CheckBox dishAvailabilityCheckBox;
    private Button saveDishButton;
    private UserRoomDatabase database;
    private DishDao dishDao;
    private Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_dish);

        dishNameEditText = findViewById(R.id.dishNameEditText);
        dishDescriptionEditText = findViewById(R.id.dishDescriptionEditText);
        dishPriceEditText = findViewById(R.id.dishPriceEditText);
        dishCategoryEditText = findViewById(R.id.dishCategoryEditText);
        dishAvailabilityCheckBox = findViewById(R.id.dishAvailabilityCheckBox);
        saveDishButton = findViewById(R.id.saveDishButton);

        database = UserRoomDatabase.getInstance(this);
        dishDao = database.dishDao();

        dish = (Dish) getIntent().getSerializableExtra("dish");
        if (dish != null) {
            dishNameEditText.setText(dish.getName());
            dishDescriptionEditText.setText(dish.getDescription());
            dishPriceEditText.setText(String.valueOf(dish.getPrice()));
            dishCategoryEditText.setText(dish.getCategory());
            dishAvailabilityCheckBox.setChecked(dish.isAvailable());
        }

        saveDishButton.setOnClickListener(v -> saveDish());
    }

    private void saveDish() {
        String name = dishNameEditText.getText().toString().trim();
        String description = dishDescriptionEditText.getText().toString().trim();
        String priceStr = dishPriceEditText.getText().toString().trim();
        String category = dishCategoryEditText.getText().toString().trim();
        boolean isAvailable = dishAvailabilityCheckBox.isChecked();

        if (name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tên và giá", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dish == null) {
            dish = new Dish(name, description, price, "", category, isAvailable);
            dishDao.insertDish(dish);
            Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
        } else {
            dish.setName(name);
            dish.setDescription(description);
            dish.setPrice(price);
            dish.setCategory(category);
            dish.setAvailable(isAvailable);
            dishDao.updateDish(dish);
            Toast.makeText(this, "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}