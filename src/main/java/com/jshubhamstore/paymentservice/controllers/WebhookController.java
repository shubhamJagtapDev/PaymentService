package com.jshubhamstore.paymentservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stripeWebhook")
public class WebhookController {
    @PostMapping
    public void displayEvent(@RequestBody String event) {
        // TODO add custom handling for all required webhook events
        System.out.println(event);
    }
}
