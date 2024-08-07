package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.OrderDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.models.Order;
import com.duchung.shopappspring.responses.OrderResponse;
import com.duchung.shopappspring.responses.OrderStatisticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;


public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    Page<OrderResponse> getAllOrders(Pageable pageable, String keyword);
    OrderResponse updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long orderId) throws DataNotFoundException;
    OrderResponse getOrderById(Long orderId) throws DataNotFoundException;
    Page<OrderResponse> getAllOrdersByUserId(Long userId, Pageable pageable) throws DataNotFoundException;
    OrderResponse updateStatus(Long orderId, String newStatus) throws DataNotFoundException, InvalidParameterException;
    int countOrders();
    List<OrderStatisticResponse> getLatestOrder();
    double getTotalMoney();
    int countCancelledOrder();
    List<OrderResponse> getSuccessfulOrder();
}
