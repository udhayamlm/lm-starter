package com.lmpay.starter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@Configuration
public class RabbitMQTopicConfig {
    @Bean
    Queue elasticQueueTopic() {
        return new Queue("elasticQueueTopic", false);
    }

    @Bean
    Queue crudQueueTopic() {
        return new Queue("crudQueueTopic", false);
    }

    @Bean
    Queue notificationQueueTopic() {
        return new Queue("notificationQueueTopic", false);
    }

    @Bean
    Queue allQueueTopic() {
        return new Queue("allQueueTopic", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    Binding elasticBindingTopic(Queue elasticQueueTopic, TopicExchange topicExchange) {
        return BindingBuilder.bind(elasticQueueTopic).to(topicExchange).with("queue.elastic");
    }

    @Bean
    Binding crudBindingTopic(Queue crudQueueTopic, TopicExchange topicExchange) {
        return BindingBuilder.bind(crudQueueTopic).to(topicExchange).with("queue.crud");
    }

    @Bean
    Binding notificationBindingTopic(Queue notificationQueueTopic, TopicExchange topicExchange) {
        return BindingBuilder.bind(notificationQueueTopic).to(topicExchange).with("queue.notification");
    }

    @Bean
    Binding allBinding(Queue allQueueTopic, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueueTopic).to(topicExchange).with("queue.*");
    }
}
