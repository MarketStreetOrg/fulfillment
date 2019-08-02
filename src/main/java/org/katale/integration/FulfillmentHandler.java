package org.katale.integration;

import org.katale.domains.Fulfillment;
import org.katale.exceptions.FulfillmentException;

public interface FulfillmentHandler {

    void saveToDB(Fulfillment fulfillment) throws FulfillmentException;

    void sendToWareHouse(Fulfillment fulfillment);

}
