package org.katale.integration;

import org.junit.Test;
import org.katale.BaseTest;
import org.katale.integration.jms.ActiveMQProducer;
import org.springframework.beans.factory.annotation.Autowired;

public class ActiveMQProducerTest extends BaseTest {

    @Autowired
    ActiveMQProducer producer;

    @Test
    public void sendMessage() {
        producer.sendMessage("Hello World");
    }
}