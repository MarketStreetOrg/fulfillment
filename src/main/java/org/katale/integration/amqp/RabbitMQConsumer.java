package org.katale.integration.amqp;

import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    public void receiveMessage(String message){
        System.out.println("message");
    }
}
