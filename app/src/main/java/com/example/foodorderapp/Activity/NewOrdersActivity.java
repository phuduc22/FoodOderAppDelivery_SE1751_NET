package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.R;
import com.example.foodorderapp.DAO.OrderDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Order;
import java.util.ArrayList;
import java.util.List;

public class NewOrdersActivity extends AppCompatActivity implements OrderAdapter.OnOrderClickListener {
    private RecyclerView newOrdersRecyclerView;
    private UserRoomDatabase database;
    private OrderDao orderDao;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);

        newOrdersRecyclerView = findViewById(R.id.newOrdersRecyclerView);

        database = UserRoomDatabase.getInstance(this);
        orderDao = database.orderDao();

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList, this);

        newOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newOrdersRecyclerView.setAdapter(orderAdapter);

        loadNewOrders();
    }

    private void loadNewOrders() {
        orderList.clear();
        orderList.addAll(orderDao.getOrdersByStatus("new"));
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConfirmClick(Order order) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNewOrders();
    }
}