package com.jshubhamstore.paymentservice.payment_gateways;

import com.razorpay.PaymentLink;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jshubhamstore.paymentservice.payment_gateway_client_configs.RazorpayConfig.RAZORPAY_PAYMENT_LINK_URL_KEY;

@Component
public class RazorpayPaymentGateway implements PaymentGateway{

    private final RazorpayClient razorpay;

    @Autowired
    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpay = razorpayClient;
    }

    @Override
    public String generatePaymentLink(Long amount, String orderId, String phoneNumber, String name, String email, Long expiryInMills) throws RazorpayException {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        paymentLinkRequest.put("first_min_partial_amount",0);
        paymentLinkRequest.put("expire_by",expiryInMills);
        paymentLinkRequest.put("reference_id", orderId);
        paymentLinkRequest.put("description","Payment for order id " + orderId);
        JSONObject customer = new JSONObject();
        customer.put("name",phoneNumber);
        customer.put("contact",name);
        customer.put("email",email);
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",false);
        notify.put("email",false);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("order_id",orderId);
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
        return payment.get(RAZORPAY_PAYMENT_LINK_URL_KEY);
    }
}
