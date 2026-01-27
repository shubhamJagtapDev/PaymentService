package com.jshubhamstore.paymentservice.services;

import com.jshubhamstore.paymentservice.payment_gateways.PaymentGateway;
import com.jshubhamstore.paymentservice.payment_gateways.PaymentGatewayChooserStrategy;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentGateway paymentGateway;
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Autowired
    public PaymentServiceImpl(PaymentGatewayChooserStrategy paymentGatewayChooserStrategy) {
        this.paymentGatewayChooserStrategy = paymentGatewayChooserStrategy;
    }

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) throws RazorpayException {
        paymentGateway = paymentGatewayChooserStrategy.getCurrentBestPaymentGateway();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        long expiryTimeInMills = calendar.getTimeInMillis();
        return paymentGateway.generatePaymentLink(amount, orderId, phoneNumber, name, email, expiryTimeInMills);
    }
}
