package org.katale.integration.activators;

import org.katale.domains.Fulfillment;
import org.katale.integration.FulfillmentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class FulfillmentActivator {

    @Autowired
    FulfillmentHandler fulfillmentHandler;

    @ServiceActivator(inputChannel = "fulfillmentChannel", requiresReply = "false", poller = {@Poller})
    public void processFulfillment(Fulfillment fulfillment) throws Throwable {

        fulfillmentHandler.saveToDB(fulfillment);
        fulfillmentHandler.sendToWareHouse(fulfillment);

    }

}
