package com.example.foodorderapp.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.R;
import com.example.foodorderapp.DAO.OrderDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Order;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView orderIdTextView, customerNameTextView, totalAmountTextView, statusTextView;
    private RecyclerView orderItemsRecyclerView;
    private Button confirmOrderButton, preparingOrderButton, completeOrderButton, deliverOrderButton, cancelOrderButton;
    private UserRoomDatabase database;
    private OrderDao orderDao;
    private Order order;
    private OrderItemAdapter orderItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderIdTextView = findViewById(R.id.orderIdTextView);
        customerNameTextView = findViewById(R.id.customerNameTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        statusTextView = findViewById(R.id.statusTextView);
        orderItemsRecyclerView = findViewById(R.id.orderItemsRecyclerView);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        preparingOrderButton = findViewById(R.id.preparingOrderButton);
        completeOrderButton = findViewById(R.id.completeOrderButton);
        deliverOrderButton = findViewById(R.id.deliverOrderButton);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);

        database = UserRoomDatabase.getInstance(this);
        orderDao = database.orderDao();

        order = (Order) getIntent().getSerializableExtra("order");
        if (order != null) {
            orderIdTextView.setText("Mã đơn hàng: " + order.getOrderId());
            customerNameTextView.setText("Khách hàng: " + order.getCustomerName());
            totalAmountTextView.setText("Tổng tiền: " + order.getTotalAmount() + " VNĐ");
            statusTextView.setText("Trạng thái: " + order.getStatus());

            orderItemAdapter = new OrderItemAdapter(this, order.getItems());
            orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            orderItemsRecyclerView.setAdapter(orderItemAdapter);

            updateButtonStates();
        }

        confirmOrderButton.setOnClickListener(v -> updateOrderStatus("confirmed"));
        preparingOrderButton.setOnClickListener(v -> updateOrderStatus("preparing"));
        completeOrderButton.setOnClickListener(v -> updateOrderStatus("completed"));
        deliverOrderButton.setOnClickListener(v -> updateOrderStatus("delivered"));
        cancelOrderButton.setOnClickListener(v -> updateOrderStatus("cancelled"));
    }

    private void updateButtonStates() {
        String status = order.getStatus();
        confirmOrderButton.setEnabled(status.equals("new"));
        preparingOrderButton.setEnabled(status.equals("confirmed"));
        completeOrderButton.setEnabled(status.equals("preparing"));
        deliverOrderButton.setEnabled(status.equals("completed"));
        cancelOrderButton.setEnabled(!status.equals("delivered") && !status.equals("cancelled"));
    }

    private void updateOrderStatus(String newStatus) {
        order.setStatus(newStatus);
        order.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        orderDao.updateOrder(order);
        Toast.makeText(this, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
        statusTextView.setText("Trạng thái: " + order.getStatus());
        updateButtonStates();
    }
}