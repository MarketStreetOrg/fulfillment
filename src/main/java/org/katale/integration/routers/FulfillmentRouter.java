package org.katale.integration.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.MessageChannel;

@MessageEndpoint
public class FulfillmentRouter {

    @Autowired
    @Qualifier("delivery")
    MessageChannel deliveryChannel;

    @Router(inputChannel = "amqpOutputChannel",channelMappings = {"pickUp","delivery"},defaultOutputChannel = "errorChannel",poller = {@Poller})
    public MessageChannel route(Object object){
        System.out.println("This is my routing Object:"+ object);
        return deliveryChannel;
    }

}
