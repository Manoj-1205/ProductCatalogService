package dev.manoj.productcatalog.controllers.webhooks;

import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhooksController {
    @PostMapping
    public void handleWebhookRequest(@RequestBody Event webhookEvent){
        System.out.println("WEBHOOK CALLED....");
    }
}
