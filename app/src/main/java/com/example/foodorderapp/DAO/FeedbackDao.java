package com.example.foodorderapp.DAO;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.foodorderapp.Entity.Feedback;

import java.util.List;
@Dao
public interface  FeedbackDao {
    // Lấy tất cả đánh giá của một món ăn theo DishId
    @Query("SELECT * FROM feedback WHERE dishId = :dishId")
    List<Feedback> getFeedbackByDishId(int dishId);

    // Chèn đánh giá mới vào database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReview(Feedback feedback);

    // Xóa tất cả đánh giá của một món ăn
    @Query("DELETE FROM feedback WHERE dishId = :dishId")
    void deleteReviewsByDishId(int dishId);
}
