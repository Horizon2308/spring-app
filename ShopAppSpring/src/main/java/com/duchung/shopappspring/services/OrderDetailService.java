package com.duchung.shopappspring.services;

import com.duchung.shopappspring.domains.IsActive;
import com.duchung.shopappspring.dtos.OrderDetailDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.OrderDetail;
import com.duchung.shopappspring.repositories.OrderDetailsRepository;
import com.duchung.shopappspring.repositories.OrderRepository;
import com.duchung.shopappspring.repositories.ProductRepository;
import com.duchung.shopappspring.responses.OrderDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<OrderDetailsResponse> getAllOrderDetails(Pageable pageable) {
        return orderDetailsRepository.findAll(pageable)
                .map(this::convertToOrderResponse);
    }

    @Override
    public OrderDetailsResponse getOrderDetailById(Long orderDetailId) throws DataNotFoundException {
        return convertToOrderResponse(orderDetailsRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found!")));
    }

    @Override
    public List<OrderDetailsResponse> getAllOrderDetailsByOrderId(Long orderId) throws DataNotFoundException {
        return orderDetailsRepository.findAllByOrder(orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found!")))
                .stream()
                .map(this::convertToOrderResponse)
                .toList();
    }

    @Override
    public void deleteOrderDetail(Long orderDetailId) throws DataNotFoundException {
        OrderDetail deletedOrderDetail = orderDetailsRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found!"));
        deletedOrderDetail.setStatus(IsActive.DISABLE);
    }

    @Override
    public OrderDetailsResponse updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO)
            throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailsRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found!"));
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetail.setNumberOfProducts(orderDetail.getNumberOfProducts());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setProduct(productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Order detail not found!")));
        return convertToOrderResponse(orderDetailsRepository.save(orderDetail));
    }

    @Override
    public OrderDetailsResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail newOrderDetail = convertToOrder(orderDetailDTO);
        return convertToOrderResponse(orderDetailsRepository.save(newOrderDetail));
    }

    private OrderDetail convertToOrder(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        return OrderDetail.builder()
                .order(orderRepository.findById(orderDetailDTO.getOrderId())
                        .orElseThrow(() -> new DataNotFoundException("Order not found!")))
                .product(productRepository.findById(orderDetailDTO.getProductId())
                        .orElseThrow(() -> new DataNotFoundException("Product not found!")))
                .color(orderDetailDTO.getColor())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .status(IsActive.ENABLE)
                .build();
    }

    private OrderDetailsResponse convertToOrderResponse(OrderDetail orderDetail) {
        OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder()
                .id(orderDetail.getId())
                .color(orderDetail.getColor())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .orderId(orderDetail.getOrder().getId())
                .price(orderDetail.getPrice())
                .productId(orderDetail.getProduct().getId())
                .totalMoney(orderDetail.getTotalMoney())
                .status(orderDetail.getStatus())
                .build();
        orderDetailsResponse.setCreateAt(orderDetail.getCreateAt());
        orderDetailsResponse.setUpdateAt(orderDetail.getUpdateAt());
        return orderDetailsResponse;
    }
}
