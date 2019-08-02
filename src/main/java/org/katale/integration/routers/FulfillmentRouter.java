package org.katale.integration.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.MessageChannel;

import java.util.regex.Pattern;

@MessageEndpoint
public class FulfillmentRouter {

    @Value("${order.regexp}")
    private String orderPattern;

    @Autowired
    @Qualifier("orderProcessingChannel")
    MessageChannel orderProcessingChannel;

    @Autowired
    @Qualifier("errorChannel")
    MessageChannel errorChannel;

    @Router(inputChannel = "amqpInputChannel", channelMappings = {"orderProcessingChannel"},
            defaultOutputChannel = "errorChannel")
    public MessageChannel routeOrder(Object object) {

        if (!Pattern.matches(orderPattern, object.toString())) {
            System.err.println("Error: Failed to match pattern with: \"" + object.toString() + "\"");
            return errorChannel;
        }
        return orderProcessingChannel;
    }

}
