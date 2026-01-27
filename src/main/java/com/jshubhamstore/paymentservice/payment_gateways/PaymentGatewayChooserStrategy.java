package com.jshubhamstore.paymentservice.payment_gateways;

import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayChooserStrategy {

    private RazorpayPaymentGateway razorpayPaymentGateway;

    public PaymentGatewayChooserStrategy(RazorpayPaymentGateway razorpayPaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }

    public PaymentGateway getCurrentBestPaymentGateway() {
        return razorpayPaymentGateway;
    }

}
