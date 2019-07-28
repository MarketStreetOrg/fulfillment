package org.katale.integration.factory;

import org.apache.activemq.command.*;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class MessageTypeFactory {

    private Message messageType;

    public MessageTypeFactory() {
    }

    public MessageTypeFactory(Message type) {
        this.messageType = type;
    }

    public Message getMessageType() {
        return messageType;
    }

    public void setMessageType(Message messageType) {
        this.messageType = messageType;
    }

    public ActiveMQMessage getInstance() {

        if (this.messageType instanceof ActiveMQTextMessage) return new ActiveMQTextMessage();

        if (this.messageType instanceof ActiveMQBytesMessage) return new ActiveMQBytesMessage();

        if (this.messageType instanceof ActiveMQMapMessage) return new ActiveMQMapMessage();

        if (this.messageType instanceof ActiveMQObjectMessage) return new ActiveMQObjectMessage();

        else return new ActiveMQStreamMessage();
    }

}
