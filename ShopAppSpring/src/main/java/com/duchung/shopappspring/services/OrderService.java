package com.duchung.shopappspring.services;

import com.duchung.shopappspring.domains.IsActive;
import com.duchung.shopappspring.domains.OrderStatus;
import com.duchung.shopappspring.dtos.CartItemDTO;
import com.duchung.shopappspring.dtos.OrderDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.models.Order;
import com.duchung.shopappspring.models.OrderDetail;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.repositories.OrderDetailsRepository;
import com.duchung.shopappspring.repositories.OrderRepository;
import com.duchung.shopappspring.repositories.ProductRepository;
import com.duchung.shopappspring.repositories.UserRepository;
import com.duchung.shopappspring.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository.findById(orderDTO.getUserID())
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());//lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDING);
        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(IsActive.ENABLE);//đoạn này nên set sẵn trong sql
        //EAV-Entity-Attribute-Value model
        order.setTotalMoney(orderDTO.getTotalMoney());
        // Tạo danh sách các đối tượng OrderDetail từ cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
            // Tạo một đối tượng OrderDetail từ CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lấy thông tin sản phẩm từ cartItemDTO
            Long productId = cartItemDTO.getProductId();
            Long quantity = cartItemDTO.getQuantity();

            // Tìm thông tin sản phẩm từ cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            // Đặt thông tin cho OrderDetail
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(Math.toIntExact(quantity));
            // Các trường khác của OrderDetail nếu cần
            orderDetail.setPrice(product.getPrice());

            // Thêm OrderDetail vào danh sách
            orderDetails.add(orderDetail);
        }

        orderRepository.save(order);
        // Lưu danh sách OrderDetail vào cơ sở dữ liệu
        orderDetailsRepository.saveAll(orderDetails);
        //coupon
        return convertToOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::convertToOrderResponse);
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order updatedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found!"));
        updatedOrder.setNote(orderDTO.getNote());
        updatedOrder.setAddress(orderDTO.getAddress());
        updatedOrder.setEmail(orderDTO.getEmail());
        updatedOrder.setFullName(orderDTO.getFullName());
        updatedOrder.setPaymentMethod(orderDTO.getPaymentMethod());
        updatedOrder.setPhoneNumber(orderDTO.getPhoneNumber());
        updatedOrder.setShippingAddress(orderDTO.getShippingAddress());
        updatedOrder.setShippingMethod(orderDTO.getShippingMethod());
        updatedOrder.setTotalMoney(orderDTO.getTotalMoney());
        return convertToOrderResponse(orderRepository.save(updatedOrder));
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) throws DataNotFoundException {
        Order deletedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found!"));
        deletedOrder.setActive(IsActive.DISABLE);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found!"));
        return convertToOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getAllOrdersByUserId(Long userId, Pageable pageable) throws DataNotFoundException {
        return orderRepository.findAllByUser(userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found!")), pageable)
                .map(this::convertToOrderResponse);
    }

    @Transactional
    @Override
    public OrderResponse updateStatus(Long orderId, String newStatus, String shippingDate)
            throws DataNotFoundException, InvalidParameterException {
        Order updatedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found!"));
        updatedOrder.setStatus(newStatus);
        if (shippingDate != null) {
            LocalDate date = LocalDate.parse(shippingDate);
            if (date.isBefore(LocalDate.now())) {
                throw new InvalidParameterException("Invalid date");
            }
        }
        return convertToOrderResponse(orderRepository.save(updatedOrder));
    }

    private Order convertToOrder(OrderDTO orderDTO, User user) {
        return Order.builder()
                .orderDate(LocalDate.now())
                .active(IsActive.ENABLE)
                .address(orderDTO.getAddress())
                .email(orderDTO.getEmail())
                .phoneNumber(orderDTO.getPhoneNumber())
                .fullName(orderDTO.getFullName())
                .paymentMethod(orderDTO.getPaymentMethod())
                .note(orderDTO.getNote())
                .totalMoney(orderDTO.getTotalMoney())
                .status(OrderStatus.PENDING)
                .user(user)
                .shippingAddress(orderDTO.getShippingAddress())
                .shippingMethod(orderDTO.getShippingMethod())
                .build();
    }

    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .active(order.getActive())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .note(order.getNote())
                .status(order.getStatus())
                .fullName(order.getFullName())
                .totalMoney(order.getTotalMoney())
                .email(order.getEmail())
                .userId(order.getUser().getId())
                .paymentMethod(order.getPaymentMethod())
                .shippingAddress(order.getShippingAddress())
                .shippingMethod(order.getShippingMethod())
                .shippingDate(order.getShippingDate())
                .orderDetails(order.getOrderDetails())
                .build();
        orderResponse.setCreateAt(order.getCreateAt());
        orderResponse.setUpdateAt(order.getUpdateAt());
        return orderResponse;
    }

}
