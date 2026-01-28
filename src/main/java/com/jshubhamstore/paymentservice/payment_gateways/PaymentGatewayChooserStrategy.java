package com.jshubhamstore.paymentservice.payment_gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayChooserStrategy {

    private RazorpayPaymentGateway razorpayPaymentGateway;

    private StripePaymentGateway stripePaymentGateway;

    @Autowired
    public PaymentGatewayChooserStrategy(RazorpayPaymentGateway razorpayPaymentGateway,
                                         StripePaymentGateway stripePaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    public PaymentGateway getCurrentBestPaymentGateway(Boolean manualSwitch) {
        if (!manualSwitch) return razorpayPaymentGateway;
        return stripePaymentGateway;
    }

}
