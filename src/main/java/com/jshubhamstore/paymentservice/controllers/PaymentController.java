package com.jshubhamstore.paymentservice.controllers;

import com.jshubhamstore.paymentservice.dtos.PaymentRequestDto;
import com.jshubhamstore.paymentservice.services.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody PaymentRequestDto request) {
        String paymentLinkStr=null;
        try {
            paymentLinkStr = paymentService.getPaymentLink(request.getAmount(), request.getOrderId(), request.getPhoneNumber(),
                    request.getName(), request.getEmail());
        }catch (RazorpayException e) {
            System.out.println("Exception from Razorpay PG " + e.getMessage());
        }
        return paymentLinkStr;
    }
}
