package com.example.foodorderapp.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodorderapp.R;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.Entity.Menu;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context context;
    private List<Menu> menuList;
    private OnMenuClickListener listener;

    public interface OnMenuClickListener {
        void onEditClick(Menu menu);
        void onDeleteClick(Menu menu);
    }

    public MenuAdapter(Context context, List<Menu> menuList, OnMenuClickListener listener) {
        this.context = context;
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.menuNameTextView.setText(menu.getName());
        holder.menuDishCountTextView.setText("Số món: " + menu.getDishIds().size());

        holder.editMenuButton.setOnClickListener(v -> listener.onEditClick(menu));
        holder.deleteMenuButton.setOnClickListener(v -> listener.onDeleteClick(menu));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView menuNameTextView, menuDishCountTextView;
        Button editMenuButton, deleteMenuButton;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuNameTextView = itemView.findViewById(R.id.menuNameTextView);
            menuDishCountTextView = itemView.findViewById(R.id.menuDishCountTextView);
            editMenuButton = itemView.findViewById(R.id.editMenuButton);
            deleteMenuButton = itemView.findViewById(R.id.deleteMenuButton);
        }
    }
}