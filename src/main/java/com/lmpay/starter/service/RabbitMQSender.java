package com.lmpay.starter.service;

import com.lmpay.starter.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@Service
@Slf4j
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${backend.rabbitmq.exchange}")
    private String exchange;

    @Value("${backend.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Category category) {
        rabbitTemplate.convertAndSend(exchange, routingkey, category);
        log.info("Send msg = {} ", category);

    }
}
