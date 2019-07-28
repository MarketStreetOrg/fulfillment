package org.katale.integration.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    @Autowired
    RabbitTemplate template;

    public void sendMessage(String message){
        template.convertAndSend("fulfillment","fulfillment.orders",message);
        System.out.println("Message has been sent");
    }

}
