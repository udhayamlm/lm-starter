package com.lmpay.starter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@Configuration
public class RabbitMQHeaderConfig {

    @Bean
    Queue elasticQueueHeader() {
        return new Queue("elasticQueueHeader", false);
    }

    @Bean
    Queue crudQueueHeader() {
        return new Queue("crudQueueHeader", false);
    }

    @Bean
    Queue notificationQueueHeader() {
        return new Queue("notificationQueueHeader", false);
    }

    @Bean
    HeadersExchange headerExchange() {
        return new HeadersExchange("header-exchange");
    }

    @Bean
    Binding elasticBindingHeader(Queue elasticQueueHeader, HeadersExchange headerExchange) {
        return BindingBuilder.bind(elasticQueueHeader).to(headerExchange).where("department").matches("elastic");
    }

    @Bean
    Binding crudBindingHeader(Queue crudQueueHeader, HeadersExchange headerExchange) {
        return BindingBuilder.bind(crudQueueHeader).to(headerExchange).where("department").matches("crud");
    }

    @Bean
    Binding notificationBindingHeader(Queue notificationQueueHeader, HeadersExchange headerExchange) {
        return BindingBuilder.bind(notificationQueueHeader).to(headerExchange).where("department").matches("notification");
    }
}
