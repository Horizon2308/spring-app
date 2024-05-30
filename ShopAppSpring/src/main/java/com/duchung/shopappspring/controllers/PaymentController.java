package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.PaymentDTO;
import com.duchung.shopappspring.responses.VNPayResponse;
import com.duchung.shopappspring.services.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @GetMapping("/vn-pay")
    public VNPayResponse<PaymentDTO.VNPayResponse> pay(HttpServletRequest request, HttpServletResponse response) {
        return new VNPayResponse<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public VNPayResponse<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            response.sendRedirect("http://localhost:4200/orders");
            //return new VNPayResponse<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", "http://localhost:4200/orders"));
        } else {
            return new VNPayResponse<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
        return new VNPayResponse<>(HttpStatus.BAD_REQUEST, "Failed", null);
    }
}
