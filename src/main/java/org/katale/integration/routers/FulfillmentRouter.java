package org.katale.integration.routers;

import com.mysql.cj.xdevapi.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.MessageChannel;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Base64;
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
    public MessageChannel routeOrder(String order) {

        if (!Pattern.matches(orderPattern,order)) {
            System.err.println("Error: Failed to match pattern with: \"" + order + "\"");
            return errorChannel;
        }
        return orderProcessingChannel;
    }

}
