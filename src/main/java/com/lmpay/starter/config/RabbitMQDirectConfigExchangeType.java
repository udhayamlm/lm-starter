package com.lmpay.starter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@Configuration
public class RabbitMQDirectConfigExchangeType {
    @Bean
    Queue elasticQueue() {
        return new Queue("elasticQueue", false);
    }

    @Bean
    Queue crudQueue() {
        return new Queue("crudQueue", false);
    }

    @Bean
    Queue notificationQueue() {
        return new Queue("notificationQueue", false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    Binding elasticBinding(Queue elasticQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(elasticQueue).to(directExchange).with("elastic");
    }

    @Bean
    Binding crudBinding(Queue crudQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(crudQueue).to(directExchange).with("crud");
    }

    @Bean
    Binding notificationBinding(Queue notificationQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(notificationQueue).to(directExchange).with("notification");
    }
}
