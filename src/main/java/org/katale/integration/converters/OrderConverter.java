package org.katale.integration.converters;

import org.katale.domains.Fulfillment;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;


@Component
public class OrderConverter {

    @Transformer(inputChannel = "amqpInputChannel",outputChannel = "amqpOutputChannel")
    public Fulfillment convert(Object message){
        System.out.println("This is my converted message");
        return new Fulfillment();
    }

}
