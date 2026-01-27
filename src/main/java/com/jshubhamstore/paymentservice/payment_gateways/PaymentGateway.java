package com.jshubhamstore.paymentservice.payment_gateways;

import com.razorpay.RazorpayException;

public interface PaymentGateway {
    String generatePaymentLink(Long amount, String orderId, String phoneNumber, String name, String email, Long expiryInMills) throws RazorpayException;
}
