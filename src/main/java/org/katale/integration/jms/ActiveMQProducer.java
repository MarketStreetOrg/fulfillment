package org.katale.integration.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

@Component
public class ActiveMQProducer {

    @Autowired
    private JmsTemplate template;

    public ActiveMQProducer(){

    }

    public void sendMessage(final String message){
        this.template.send("fulfillment.orders",new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage((message));
            }
        });
    }

}
