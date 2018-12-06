package com.rabbitmq.queue.jmsq.publisher.Impl;

import com.rabbitmq.queue.config.JsonMessageListenerConfig;
import com.rabbitmq.queue.config.RabbitMqConfig;
import com.rabbitmq.queue.dto.SimplePojo;
import com.rabbitmq.queue.jmsq.publisher.CoreMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class JsonMessagePublisher implements CoreMessagePublisher {

    private AmqpTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(JsonMessagePublisher.class);

    @Autowired
    public JsonMessagePublisher(@Qualifier(value = "jsonRabbitTemplate") AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publisher(Message message) throws AmqpConnectException {

        logger.info("Scheduling pojo message.");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                logger.info("Sending pojo message.");
                rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE_NAME, JsonMessageListenerConfig.JSON_ROUTING_KEY, message);
                logger.info("Pojo: " + message.getBody());
            }
        }, 5000);
    }
}
