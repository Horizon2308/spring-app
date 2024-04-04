package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.domains.OrderStatus;
import com.duchung.shopappspring.dtos.OrderDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.responses.OrderListResponse;
import com.duchung.shopappspring.responses.OrderResponse;
import com.duchung.shopappspring.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "limit",required = false) Integer limit) {
        Page<OrderResponse> orderResponseList = orderService.getAllOrders(PageRequest.of(page, limit));
        int totalPage = orderResponseList.getTotalPages();
        if (totalPage == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Orders are empty!"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(OrderListResponse.builder()
                .ordersList(orderResponseList)
                .totalPage(totalPage)
                .build(), "Get all orders"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long orderId) {
        try {
            return ResponseEntity.ok(new SuccessResponse<>(orderService.getOrderById(orderId),
                    "Get order with id: " + orderId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("user_id") Long userId,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "limit",required = false) Integer limit) {
        try {
            return ResponseEntity.ok().body(new SuccessResponse<>(orderService.getAllOrdersByUserId(userId,
                    PageRequest.of(page, limit)),
                    "Get all orders with user id: " + userId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new ErrorResponse<>(errorMessages));
        }
        try {
            return ResponseEntity.accepted().body(new SuccessResponse<>(orderService.createOrder(orderDTO),
                    "Created successfully!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long orderId,
                                         @Valid @RequestBody OrderDTO orderDTO,
                                         BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new ErrorResponse<>(errorMessages));
        }
        try {
            return ResponseEntity.accepted().body(new SuccessResponse<>(orderService.updateOrder(orderId,
                    orderDTO), "Updated an order which has id: " + orderId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().body(new SuccessResponse<>("Deleted an order which has id: " + orderId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PutMapping("/change-status/{id}/{new_status}/{shipping_date}")
    public ResponseEntity<?> changeStatus(@PathVariable(value = "shipping_date", required = false)
                                              String shippingDate,
                                          @PathVariable("new_status") String newStatus,
                                          @PathVariable("id") Long orderId) {
        if (newStatus.equals(OrderStatus.SHIPPED) && shippingDate == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>("Shipping date can't be null!"));
        }
        try {
            return ResponseEntity.ok(new SuccessResponse<>(orderService.updateStatus(orderId, newStatus,
                    shippingDate), "Changed status successfully"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        } catch (InvalidParameterException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>("Invalid date"));
        }
    }
}
