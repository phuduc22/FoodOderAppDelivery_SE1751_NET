package com.example.foodorderapp.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodorderapp.R;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.Entity.Dish;
import java.util.List;

public class DishSelectionAdapter extends RecyclerView.Adapter<DishSelectionAdapter.DishSelectionViewHolder> {
    private Context context;
    private List<Dish> dishList;
    private List<Integer> selectedDishIds;

    public DishSelectionAdapter(Context context, List<Dish> dishList, List<Integer> selectedDishIds) {
        this.context = context;
        this.dishList = dishList;
        this.selectedDishIds = selectedDishIds;
    }

    @NonNull
    @Override
    public DishSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dish_selection, parent, false);
        return new DishSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishSelectionViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.dishNameTextView.setText(dish.getName());
        holder.dishSelectionCheckBox.setChecked(selectedDishIds.contains(dish.getDishId()));

        holder.dishSelectionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedDishIds.contains(dish.getDishId())) {
                    selectedDishIds.add(dish.getDishId());
                }
            } else {
                selectedDishIds.remove(Integer.valueOf(dish.getDishId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class DishSelectionViewHolder extends RecyclerView.ViewHolder {
        TextView dishNameTextView;
        CheckBox dishSelectionCheckBox;

        public DishSelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            dishNameTextView = itemView.findViewById(R.id.dishNameTextView);
            dishSelectionCheckBox = itemView.findViewById(R.id.dishSelectionCheckBox);
        }
    }
}