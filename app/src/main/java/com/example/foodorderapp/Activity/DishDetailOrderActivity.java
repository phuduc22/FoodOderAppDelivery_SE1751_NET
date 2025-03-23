package com.example.foodorderapp.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Activity.Bean.FeedbackAdapter;
import com.example.foodorderapp.DAO.FeedbackDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Dish;
import com.example.foodorderapp.Entity.Feedback;
import com.example.foodorderapp.R;

import java.util.Arrays;
import java.util.List;

public class DishDetailOrderActivity extends AppCompatActivity {
    private static int currentPageList = 0 ;
    private ImageView imgDish;
    private TextView txtDishName, txtDishPrice, txtDishDescription;
    private EditText etQuantity;
    private Button btnOrder, btnBackList,btnNextList;
    private RecyclerView reviewsRecyclerView;
    private FeedbackAdapter feedbackAdapter;
    private List<Feedback> feedbackList;
    private int dishId;
    private UserRoomDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dish_detail_order);

// Ánh xạ View
        imgDish = findViewById(R.id.imgDish);
        txtDishName = findViewById(R.id.txtDishName);
        txtDishPrice = findViewById(R.id.txtDishPrice);
        txtDishDescription = findViewById(R.id.txtDishDescription);
        etQuantity = findViewById(R.id.etQuantity);
        btnOrder = findViewById(R.id.btnOrder);
        btnBackList = findViewById(R.id.btnBackFeedback);
        btnNextList = findViewById(R.id.btnNextFeedback);
        reviewsRecyclerView = findViewById(R.id.reviews_recyclerview);

        // Nhận dữ liệu từ Intent
        dishId = getIntent().getIntExtra("DishId", -1);
        database = UserRoomDatabase.getInstance(this);

        if (dishId != -1) {
            Dish dish = database.dishDao().getDishById(dishId);
            if (dish != null) {
                txtDishName.setText(dish.getName());
                txtDishPrice.setText("Price: " + dish.getPrice() + " VND");
                txtDishDescription.setText(dish.getDescription());

                String imagePath = "android.resource://" + this.getPackageName() + "/drawable/"+dish.getImageUrl();

                // Load ảnh món ăn (nếu có URL)
                Glide.with(this).load(imagePath).into(imgDish);
            }
        }

        // Xử lý sự kiện đặt hàng
        btnOrder.setOnClickListener(v -> {
            String quantityText = etQuantity.getText().toString().trim();
            if (quantityText.isEmpty() || Integer.parseInt(quantityText) <= 0) {
                Toast.makeText(DishDetailOrderActivity.this, "Please enter a valid quantity!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DishDetailOrderActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        List<Feedback> feedbackListDatabase = database.feedbackDao().getFeedbackByDishId(dishId);
        if (feedbackListDatabase.isEmpty()){
            List<Feedback> defaultFeedback = Arrays.asList(new Feedback(
                    1, dishId, "Nguyễn Văn A", 4.5f, "Món ăn rất ngon!", Feedback.getCurrentDate(), "https://example.com/avatar1.jpg"
            ),
new Feedback(
                    2, dishId, "Trần Thị B", 3.0f, "Tạm ổn, phục vụ hơi chậm", Feedback.getCurrentDate(), "https://example.com/avatar2.jpg"
            ),
                    new Feedback(
                    3, dishId, "Lê Văn C", 5.0f, "Quá tuyệt vời!", Feedback.getCurrentDate(), "https://example.com/avatar3.jpg"
            ),new Feedback(
                    4, dishId, "Phạm Minh D", 2.5f, "Chất lượng không như mong đợi", Feedback.getCurrentDate(), "https://example.com/avatar4.jpg"
            ),new Feedback(
                    5, dishId, "Đỗ Thị E", 4.0f, "Ngon nhưng hơi đắt", Feedback.getCurrentDate(), "https://example.com/avatar5.jpg"
            ));
            for (Feedback feedback : defaultFeedback) {
                database.feedbackDao().insertReview(feedback); // Chèn từng món vào database
            }
        }
        // Xem thêm đánh giá
        btnBackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPageList > 0) {
                    currentPageList--;
                    updateFeedbackList(feedbackListDatabase);
                }
            }
        });
        btnNextList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentPageList * 3) + 3 < feedbackListDatabase.size()) {
                    currentPageList++;
                    updateFeedbackList(feedbackListDatabase);
                }
            }
        });
        // Ánh xạ RecyclerView
        reviewsRecyclerView = findViewById(R.id.reviews_recyclerview);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int toIndex = Math.min(3, feedbackListDatabase.size());
        feedbackList = feedbackListDatabase.subList(0, toIndex);

        // Gán Adapter vào RecyclerView
        feedbackAdapter = new FeedbackAdapter(this, feedbackList);
        reviewsRecyclerView.setAdapter(feedbackAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void updateFeedbackList(List<Feedback> feedbackListDatabase) {
        if (feedbackListDatabase == null || feedbackListDatabase.isEmpty()) return;
        int fromIndex = currentPageList * 3;
        int toIndex = Math.min(fromIndex + 3, feedbackListDatabase.size());
        List<Feedback> pagedList = feedbackListDatabase.subList(fromIndex, toIndex);

        feedbackAdapter = new FeedbackAdapter(this, pagedList);
        reviewsRecyclerView.setAdapter(feedbackAdapter);
    }
}