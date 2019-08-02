package org.katale.integration.transformers;

import org.katale.domains.Fulfillment;
import org.katale.service.FulfillmentService;
import org.katale.utilities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class OrderTransformer {

    @Autowired
    FulfillmentService fulfillmentService;

   // @Validator(params={"[A-Z | a-z]*"})
    @Transformer(inputChannel = "orderProcessingChannel",outputChannel = "fulfillmentChannel",poller = {@Poller})
    public Fulfillment transformOrder(Object order) throws Throwable{

        Fulfillment fulfillment=new Fulfillment();
        fulfillment.setOrderId(Long.parseLong((String) order));
        fulfillment.setStatus(Status.PENDING);
        fulfillment.setDateFulfilled(new Date());

        System.out.println("Here");

        return fulfillment;
    }

}
