package com.jshubhamstore.paymentservice.payment_gateways;

import com.razorpay.RazorpayException;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements PaymentGateway{

    private StripeClient stripeClient;

    @Autowired
    public StripePaymentGateway(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @Override
    public String generatePaymentLink(Long amount, String orderId, String phoneNumber, String name, String email, Long expiryInMills) throws RazorpayException {
        try {
          ProductCreateParams productCreateParams =
                  ProductCreateParams.builder()
                           .setName("MacBook Pro M5 Pro 36GB RAM 1TB SSD")
                            .setDescription("A machine you need to build your wildest ideas")
                           .setId(orderId)
                           .setActive(true)
                           .build();
            /*
            We should have a product id saved in our db that would be mapped to id in stripe DB
            if we have multiple orders for the same product we fetch it's saved striped_product_id from the DB
             */
            Product product = stripeClient.v1().products().create(productCreateParams);


            PriceCreateParams priceCreateParams =
                    PriceCreateParams.builder()
                            .setCurrency("inr")
                            .setUnitAmount(35000000L)
                            .setProduct(product.getId())
                            .build();

// For SDK versions 29.4.0 or lower, remove '.v1()' from the following line.
            Price price = stripeClient.v1().prices().create(priceCreateParams);


            PaymentLinkCreateParams paymentLinkCreateParams =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl("https://www.apple.com/in/store")
                                            .build())
                                    .build())
                            .build();
// For SDK versions 29.4.0 or lower, remove '.v1()' from the following line.
            PaymentLink paymentLink = stripeClient.v1().paymentLinks().create(paymentLinkCreateParams);
            return paymentLink.getUrl();

            /*
            // To deactivate a payment link later
            PaymentLinkUpdateParams updateParams =
                    PaymentLinkUpdateParams.builder()
                            .setActive(false)
                            .build();

// You would call this when you want to deactivate the link
            PaymentLink updatedPaymentLink = client.v1().paymentLinks().update(
                    paymentLink.getId(),
                    updateParams
            );
            */

        }catch (StripeException stripeException) {
            // TODO Add better exception handling here (if needed)
            System.out.println("Stripe exception was throw. Reason: " + stripeException.getStripeError());
        }

        return "";
    }
}
