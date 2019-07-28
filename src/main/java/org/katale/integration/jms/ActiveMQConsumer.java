package org.katale.integration.jms;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class ActiveMQConsumer implements MessageListener {

    @JmsListener(destination = "fulfillment.orders", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) {

        ActiveMQTextMessage msg= (ActiveMQTextMessage) message;

         try {
            System.out.println(msg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
