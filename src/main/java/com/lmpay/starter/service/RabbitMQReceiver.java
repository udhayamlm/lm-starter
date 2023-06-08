package com.lmpay.starter.service;

import com.lmpay.starter.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@Component
@Slf4j
public class RabbitMQReceiver {

    @RabbitListener(queues = "${backend.rabbitmq.queue}")
    public void receivedMessage(Category category) {
        log.info("Recieved Message From RabbitMQ: " + category);
    }
}
