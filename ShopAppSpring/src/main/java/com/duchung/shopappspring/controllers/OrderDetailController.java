package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.OrderDetailDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order-detail")
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrderDetail(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok().body(new SuccessResponse<>(orderDetailService.getAllOrderDetails(PageRequest
                .of(page, limit)), "Get all order details"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable("id") Long orderDetailId) {
        try {
            return ResponseEntity.ok().body(new SuccessResponse<>(orderDetailService.getOrderDetailById(orderDetailId)
                    , "Get order detail by id: " + orderDetailId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@PathVariable("id") Long orderId) {
        try {
            return ResponseEntity.ok().body(new SuccessResponse<>(orderDetailService
                    .getAllOrderDetailsByOrderId(orderId), "Get order details by order id: " + orderId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO,
                                               BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                    orderDetailService.createOrderDetail(orderDetailDTO),
                    "Created successfully"
            ));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long orderDetailId,
                                               @Valid @RequestBody OrderDetailDTO orderDetailDTO,
                                               BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.accepted().body(new SuccessResponse<>(
                    orderDetailService.updateOrderDetail(orderDetailId, orderDetailDTO),
                    "Updated successfully"
            ));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long orderDetailId) {
        try {
            orderDetailService.deleteOrderDetail(orderDetailId);
            return ResponseEntity.ok().body(new SuccessResponse<>("Deleted successfully"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }
}
