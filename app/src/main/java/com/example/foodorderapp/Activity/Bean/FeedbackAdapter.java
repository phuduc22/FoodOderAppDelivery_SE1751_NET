package com.example.foodorderapp.Activity.Bean;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Entity.Dish;
import com.example.foodorderapp.Entity.Feedback;
import com.example.foodorderapp.R;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private Context context;
    private List<Feedback> feedbackList;

    public FeedbackAdapter(Context context, List<Feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);

        holder.txtReviewerName.setText(feedback.getReviewerName());
        holder.txtReviewDate.setText(feedback.getFormattedReviewDate());
        holder.txtReviewText.setText(feedback.getReviewText());
        holder.ratingBar.setRating(feedback.getRating());

        // Load ảnh đại diện bằng Glide

        String imagePath = "android.resource://" + context.getPackageName() + "/drawable/"+feedback.getAvatarUrl();
        Glide.with(context)
                .load(Uri.parse(imagePath))
                .into(holder.imgAvatar);

    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
    public void updateData(List<Feedback> newFeedbackList) {
        this.feedbackList = newFeedbackList;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtReviewerName, txtReviewDate, txtReviewText, txtPurchased;
        ImageView imgAvatar;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.reviewer_avatar);
            txtReviewerName = itemView.findViewById(R.id.reviewer_name);
            txtReviewDate = itemView.findViewById(R.id.review_date);
            txtReviewText = itemView.findViewById(R.id.review_text);
            ratingBar = itemView.findViewById(R.id.review_rating);
        }
    }
}
