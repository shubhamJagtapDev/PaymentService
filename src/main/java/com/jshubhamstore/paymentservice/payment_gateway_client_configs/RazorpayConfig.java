package com.jshubhamstore.paymentservice.payment_gateway_client_configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.key.id}")
    private String razorPayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorPayKeySecret;

    public static String RAZORPAY_PAYMENT_LINK_URL_KEY = "short_url";

    @Bean
    public RazorpayClient getRazorpayClient() {
        try {
            return new RazorpayClient(razorPayKeyId, razorPayKeySecret);
        } catch (RazorpayException razorpayException) {
            // TODO Throw the checked exception here and handle where it is needed to handle
            throw new RuntimeException(razorpayException.getMessage());
        }
    }
}
