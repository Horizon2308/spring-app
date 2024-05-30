package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request);
}
