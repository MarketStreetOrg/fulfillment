package org.katale.integration;

import org.katale.aop.annotations.Log;
import org.katale.domains.Fulfillment;
import org.katale.exceptions.FulfillmentException;
import org.katale.service.FulfillmentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FulfillmentHandlerImpl implements FulfillmentHandler {

    @Autowired
    FulfillmentService fulfillmentService;

    @Autowired
    RabbitTemplate template;

    @Log(message = "Fulfillment for order has been saved")
    @Override
    public void saveToDB(Fulfillment fulfillment) throws FulfillmentException {
        fulfillmentService.save(fulfillment);
    }

    @Log(message = "Fulfillment for order has been sent to the Ware house")
    @Override
    public void sendToWareHouse(Fulfillment fulfillment) {
        Fulfillment savedFulfillment = fulfillmentService.getByOrder(fulfillment.getOrderId());
        if (savedFulfillment != null) {
            template.convertAndSend("warehouse", "warehouse.fulfillment", String.valueOf(savedFulfillment.getId()));
        }
    }
}
