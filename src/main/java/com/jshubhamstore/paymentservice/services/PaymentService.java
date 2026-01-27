package com.jshubhamstore.paymentservice.services;

import com.razorpay.RazorpayException;

public interface PaymentService {
    String getPaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) throws RazorpayException;
}
