package com.jshubhamstore.paymentservice.payment_gateway_client_configs;

import com.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.apiKey}")
    private String secretAPIKey;

    @Bean
    public StripeClient getStripClient(){
        return new StripeClient(secretAPIKey);
    }

}
