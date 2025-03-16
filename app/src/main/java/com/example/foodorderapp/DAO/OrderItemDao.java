package com.example.foodorderapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderapp.Entity.OrderItem;

import java.util.List;

@Dao
public interface OrderItemDao {

    // Thêm một OrderItem mới
    @Insert
    void insertOrderItem(OrderItem orderItem);

    // Thêm nhiều OrderItem cùng lúc
    @Insert
    void insertAllOrderItems(List<OrderItem> orderItems);

    // Cập nhật một OrderItem
    @Update
    void updateOrderItem(OrderItem orderItem);

    // Xóa một OrderItem
    @Delete
    void deleteOrderItem(OrderItem orderItem);

    // Xóa tất cả OrderItem của một đơn hàng dựa trên orderId
    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    void deleteOrderItemsByOrderId(int orderId);

    // Lấy tất cả OrderItem
    @Query("SELECT * FROM order_items")
    List<OrderItem> getAllOrderItems();

    // Lấy OrderItem theo itemId
    @Query("SELECT * FROM order_items WHERE itemId = :itemId")
    OrderItem getOrderItemById(int itemId);

    // Lấy tất cả OrderItem của một đơn hàng dựa trên orderId
    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    List<OrderItem> getOrderItemsByOrderId(int orderId);

    // Lấy tổng số lượng OrderItem của một đơn hàng
    @Query("SELECT COUNT(*) FROM order_items WHERE orderId = :orderId")
    int getOrderItemCountByOrderId(int orderId);

    // Lấy tổng giá trị (price * quantity) của các OrderItem trong một đơn hàng
    @Query("SELECT SUM(price * quantity) FROM order_items WHERE orderId = :orderId")
    double getTotalAmountByOrderId(int orderId);
}