package com.rabbitmq.queue.jmsq.publisher;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.core.Message;

public interface CoreMessagePublisher {

    void publisher(Message message) throws AmqpConnectException;
}
