package com.lmpay.starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@Slf4j
@Configuration
public class RabbitMQFanoutConfig {

    @Bean
    Queue elasticQueueFanout() {
        return new Queue("elasticQueue-f", false);
    }

    @Bean
    Queue crudQueueFanout() {
        return new Queue("crudQueue-f", false);
    }

    @Bean
    Queue notificationQueueFanout() {
        return new Queue("notificationQueue-f", false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    Binding elasticBindingFanout(Queue elasticQueueFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(elasticQueueFanout).to(fanoutExchange);
    }

    @Bean
    Binding crudBindingFanout(Queue crudQueueFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(crudQueueFanout).to(fanoutExchange);
    }

    @Bean
    Binding notificationBindingFanout(Queue notificationQueueFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(notificationQueueFanout).to(fanoutExchange);
    }
}
