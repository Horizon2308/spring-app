package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.OrderDetailDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.OrderDetail;
import com.duchung.shopappspring.responses.OrderDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderDetailService {
    Page<OrderDetailsResponse> getAllOrderDetails(Pageable pageable);

    OrderDetailsResponse getOrderDetailById(Long orderDetailId) throws DataNotFoundException;

    List<OrderDetailsResponse> getAllOrderDetailsByOrderId(Long orderId) throws DataNotFoundException;

    void deleteOrderDetail(Long orderDetailId) throws DataNotFoundException;

    OrderDetailsResponse updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    OrderDetailsResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
}
