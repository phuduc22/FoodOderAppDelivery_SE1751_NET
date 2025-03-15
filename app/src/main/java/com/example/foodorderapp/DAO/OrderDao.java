package com.example.foodorderapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderapp.Entity.Order;
import com.example.foodorderapp.Entity.OrderItem;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    List<Order> getAllOrders();

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    Order getOrderById(int orderId);

    @Query("SELECT * FROM orders WHERE status = :status")
    List<Order> getOrdersByStatus(String status);

    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    List<OrderItem> getOrderItemsByOrderId(int orderId);

    @Insert
    void insertOrderItem(OrderItem orderItem);

    @Delete
    void deleteOrderItem(OrderItem orderItem);
}