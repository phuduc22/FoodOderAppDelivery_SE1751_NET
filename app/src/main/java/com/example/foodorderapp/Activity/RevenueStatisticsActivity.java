package com.example.foodorderapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderapp.DAO.OrderDao;
import com.example.foodorderapp.DAO.RevenueDao;
import com.example.foodorderapp.DAO.UserRoomDatabase;
import com.example.foodorderapp.Entity.Order;
import com.example.foodorderapp.R;
import com.example.foodorderapp.Entity.Revenue;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RevenueStatisticsActivity extends AppCompatActivity {
    private Spinner periodSpinner;
    private TextView revenueTextView, orderCountTextView;
    private UserRoomDatabase database;
    private RevenueDao revenueDao;
    private OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_statistics);

        periodSpinner = findViewById(R.id.periodSpinner);
        revenueTextView = findViewById(R.id.revenueTextView);
        orderCountTextView = findViewById(R.id.orderCountTextView);

        database = UserRoomDatabase.getInstance(this);
        revenueDao = database.revenueDao();
        orderDao = database.orderDao();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.period_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(adapter);

        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRevenueStats(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        updateRevenueStats(0); // Mặc định hiển thị theo ngày
    }

    private void updateRevenueStats(int periodType) {
        String period;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (periodType == 0) { // Ngày
            period = dateFormat.format(new Date());
        } else if (periodType == 1) { // Tuần
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            period = dateFormat.format(calendar.getTime()) + "_week";
        } else { // Tháng
            period = new SimpleDateFormat("yyyy-MM").format(new Date());
        }

        Revenue revenue = revenueDao.getRevenueByPeriod(period);
        if (revenue == null) {
            double totalRevenue = calculateTotalRevenue(periodType);
            int orderCount = orderDao.getOrdersByStatus("delivered").size();
            revenue = new Revenue(period, totalRevenue, orderCount);
            revenueDao.insertRevenue(revenue);
        }

        revenueTextView.setText("Tổng doanh thu: " + revenue.getTotalRevenue() + " VNĐ");
        orderCountTextView.setText("Số đơn hàng: " + revenue.getOrderCount());
    }

    private double calculateTotalRevenue(int periodType) {
        List<Order> deliveredOrders = orderDao.getOrdersByStatus("delivered");
        double total = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        for (Order order : deliveredOrders) {
            try {
                Date orderDate = dateFormat.parse(order.getUpdatedAt());
                calendar.setTime(new Date());
                if (periodType == 0) { // Ngày
                    if (dateFormat.format(orderDate).equals(dateFormat.format(new Date()))) {
                        total += order.getTotalAmount();
                    }
                } else if (periodType == 1) { // Tuần
                    calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                    Date weekStart = calendar.getTime();
                    calendar.add(Calendar.DAY_OF_WEEK, 6);
                    Date weekEnd = calendar.getTime();
                    if (orderDate.after(weekStart) && orderDate.before(weekEnd)) {
                        total += order.getTotalAmount();
                    }
                } else { // Tháng
                    String orderMonth = new SimpleDateFormat("yyyy-MM").format(orderDate);
                    String currentMonth = new SimpleDateFormat("yyyy-MM").format(new Date());
                    if (orderMonth.equals(currentMonth)) {
                        total += order.getTotalAmount();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }
}