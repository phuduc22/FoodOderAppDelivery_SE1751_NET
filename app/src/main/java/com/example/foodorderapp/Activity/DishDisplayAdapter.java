package com.example.foodorderapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Entity.Dish;
import com.example.foodorderapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class DishDisplayAdapter extends RecyclerView.Adapter<DishDisplayAdapter.ViewHolder> {

    private Context context;
    private List<Dish> listFood;
    DatabaseReference databaseReference;
    public DishDisplayAdapter(Context context, List<Dish> listFood){
        this.context=context;
        this.listFood = listFood;

    }
    @NonNull
    @Override
    public DishDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dish_menu_display,parent,false);
        return new DishDisplayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishDisplayAdapter.ViewHolder holder, int position) {
        final Dish food = listFood.get(position);
        String imagePath = "android.resource://" + context.getPackageName() + "/drawable/"+food.getImageUrl();
        Glide.with(context)
                .load(Uri.parse(imagePath))
                .into(holder.imageView);
        holder.FoodName.setText(food.getName());
        holder.FoodPrice.setText("Price: "+food.getPrice()+" dong ");
//         Xử lý sự kiện click vào CardView

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DishDetailOrderActivity.class);

            // Truyền dữ liệu qua Intent
            intent.putExtra("DishId", food.getDishId());
            context.startActivity(intent);
        });
        holder.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            Intent intent = new Intent(context, DishDetailOrderActivity.class);

            // Truyền dữ liệu qua Intent
            intent.putExtra("DishId", food.getDishId());
            context.startActivity(intent);
            }
        });

    }
    public void updateData(List<Dish> newFoodList) {
        this.listFood = newFoodList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView FoodName,FoodPrice;
        Button btnBuyNow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Food_Image);
            FoodName=itemView.findViewById(R.id.Food_Name);
            FoodPrice=itemView.findViewById(R.id.Food_Price);
            btnBuyNow=itemView.findViewById(R.id.btnBuyNow);

        }
    }

}
