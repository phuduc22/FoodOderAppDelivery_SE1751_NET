package com.example.foodorderapp.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.example.foodorderapp.R;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.Entity.Dish;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {
    private Context context;
    private List<Dish> dishList;
    private OnDishClickListener listener;

    public interface OnDishClickListener {
        void onEditClick(Dish dish);
        void onDeleteClick(Dish dish);
    }

    public DishAdapter(Context context, List<Dish> dishList, OnDishClickListener listener) {
        this.context = context;
        this.dishList = dishList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.dishNameTextView.setText(dish.getName());
        holder.dishPriceTextView.setText("Giá: " + dish.getPrice() + " VNĐ");
        holder.dishAvailabilityTextView.setText("Trạng thái: " + (dish.isAvailable() ? "Còn món" : "Hết món"));

        holder.editDishButton.setOnClickListener(v -> listener.onEditClick(dish));
        holder.deleteDishButton.setOnClickListener(v -> listener.onDeleteClick(dish));
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImageView;
        TextView dishNameTextView, dishPriceTextView, dishAvailabilityTextView;
        Button editDishButton, deleteDishButton;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImageView = itemView.findViewById(R.id.dishImageView);
            dishNameTextView = itemView.findViewById(R.id.dishNameTextView);
            dishPriceTextView = itemView.findViewById(R.id.dishPriceTextView);
            dishAvailabilityTextView = itemView.findViewById(R.id.dishAvailabilityTextView);
            editDishButton = itemView.findViewById(R.id.editDishButton);
            deleteDishButton = itemView.findViewById(R.id.deleteDishButton);
        }
    }
}