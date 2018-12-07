package com.rabbitmq.queue.jmsq.publisher.Impl;

import com.rabbitmq.queue.config.NormalMessageListenerConfig;
import com.rabbitmq.queue.config.RabbitMqConfig;
import com.rabbitmq.queue.jmsq.publisher.CoreMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NormalMessagePublisher implements CoreMessagePublisher {

    Logger logger = LoggerFactory.getLogger(NormalMessagePublisher.class);
    private AmqpTemplate rabbitTemplate;

    @Autowired
    public NormalMessagePublisher(@Qualifier(value = "textRabbitTemplate") AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publisher(Message message) throws AmqpConnectException {

        logger.info("Sending Message");
        rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE_NAME, NormalMessageListenerConfig.TEXT_MESSAGE_ROUTING_KEY, message);
        logger.info("Message sent");
    }
}
