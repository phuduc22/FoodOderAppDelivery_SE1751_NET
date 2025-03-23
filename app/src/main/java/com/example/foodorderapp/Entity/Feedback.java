package com.example.foodorderapp.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Entity(tableName = "feedback")
public class Feedback {
    @PrimaryKey
    @NonNull
    private String feedbackId; // 🟢 Đổi từ int -> String
    private int userId;
    private int dishId;
    private String reviewerName;
    private float rating;
    private String reviewText;
    private String reviewDate; // 🟢 Đổi từ Date -> String
    private String avatarUrl;

    // Constructor đầy đủ (Tạo ID tự động)
    public Feedback(int userId, int dishId, String reviewerName, float rating, String reviewText, String reviewDate, String avatarUrl) {
        this.feedbackId = UUID.randomUUID().toString(); // 🟢 Tạo ID ngẫu nhiên
        this.userId = userId;
        this.dishId = dishId;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.avatarUrl = avatarUrl;
    }
    public String getFormattedReviewDate() {
        if (reviewDate == null || reviewDate.isEmpty()) {
            return "N/A"; // Trả về "N/A" nếu ngày bị null hoặc rỗng
        }
        return reviewDate; // Vì reviewDate đã là String nên trả về luôn
    }
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Getter & Setter
    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getDishId() { return dishId; }
    public void setDishId(int dishId) { this.dishId = dishId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public String getReviewDate() { return reviewDate; }
    public void setReviewDate(String reviewDate) { this.reviewDate = reviewDate; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
