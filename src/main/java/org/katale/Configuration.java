package org.katale;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.katale.integration.amqp.RabbitMQConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.inbound.AmqpInboundGateway;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
@EnableJms
@EnableTransactionManagement
public class Configuration {

    @Value(value = "${activeMQ.url}")
    private String activeMQURL;

    @Value(value = "${activeMQ.username}")
    private String activeMQusername;

    @Value(value = "${activeMQ.password}")
    private String activeMQPassword;

    @Value(value = "${hibernate.dialect}")
    private String hibernateDialect;

    @Value(value = "${hibernate.show_sql}")
    private String showSQL;

    @Value(value = "${hibernate.format_sql}")
    private String formatSQL;

    @Value(value = "${jdbc.url}")
    private String mySQLURL;

    @Value(value = "${jdbc.username}")
    private String mySQLusername;

    @Value(value = "${jdbc.password}")
    private String mySQLPassword;


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activeMQURL);
        connectionFactory.setUserName(activeMQusername);
        connectionFactory.setPassword(activeMQPassword);
        return connectionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(mySQLURL);
        dataSource.setUsername(mySQLusername);
        dataSource.setPassword(mySQLPassword);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"org.katale.domains"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        //properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", showSQL);
        properties.setProperty("use_sql_comments", "true");
        properties.setProperty("hibernate.format_sql", formatSQL);
        return properties;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(activeMQConnectionFactory());
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, activeMQConnectionFactory());
        return factory;
    }

    @Bean
    TopicExchange orderExchange() {
        TopicExchange topicExchange = new TopicExchange("fulfillment");
        return topicExchange;
    }

    @Bean
    TopicExchange wareHouseExchange() {
        return new TopicExchange("warehouse");
    }

    @Bean
    Binding orderBinding(@Qualifier("ordersQueue") Queue queue, @Qualifier("orderExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("fulfillment.orders");
    }

    @Bean
    Binding wareHouseBinding(@Qualifier("fulfillmentQueue") Queue queue, @Qualifier("wareHouseExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("warehouse.fulfillment");
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQConsumer receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("ordersQueue");

        /***
         *Use this listener adapter if you are not depending on the Default injected RabbitMQ Listener
         * <code>container.setMessageListener(listenerAdapter);</code>
         */
        return container;
    }

    /**
     * Template for accessing the RabbitMQ Exchange, sending and receiving messages
     * Use if not relying on default injected RabbitMQ template
     *
     * @param
     * @return
     */
//    @Bean
//    RabbitTemplate template(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate=new RabbitTemplate();
//        rabbitTemplate.setExchange("fulfillment");
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//        return rabbitTemplate;
//    }

    //EAI (BUS ARCHITECTURE)
    @Bean
    public AmqpInboundChannelAdapter inboundChannelAdapter(SimpleMessageListenerContainer listenerContainer,
                                                           @Qualifier("amqpInputChannel") MessageChannel channel) {
        AmqpInboundChannelAdapter amqpInboundChannelAdapter = new AmqpInboundChannelAdapter(listenerContainer);
        amqpInboundChannelAdapter.setOutputChannel(channel);
        return amqpInboundChannelAdapter;
    }

    /**
     * Channels defined here
     */
    @Bean
    MessageChannel amqpInputChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel fulfillmentChannel() {
        return new QueueChannel();
    }

    @Bean
    MessageChannel pickUp() {
        return new QueueChannel();
    }

    @Bean
    MessageChannel delivery() {
        return new QueueChannel();
    }

    @Bean
    MessageChannel orderProcessingChannel() {
        return new QueueChannel();
    }
    
    /***
     * Queues defined here
     */
    @Bean
    Queue ordersQueue() {
        Queue queue = new Queue("ordersQueue", true);
        return queue;
    }

    @Bean
    Queue fulfillmentQueue() {
        Queue queue = new Queue("fulfillmentQueue", true);
        return queue;
    }
}