package com.rabbitmq.queue.config;

import com.rabbitmq.queue.jmsq.listener.CoreMessageListener;
import com.rabbitmq.queue.jmsq.listener.Impl.JsonMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMessageListenerConfig {

    private static final String JSON_MESSAGE_QUEUE = "jsonMessageQueue";
    public static final String JSON_ROUTING_KEY = "jsonRoutingKey";

    @Bean(value = "jsonMessageQueue")
    public Queue jsonMessageQueue() {
        return new Queue(JSON_MESSAGE_QUEUE, true);
    }

    @Bean
    public Binding jsonQueueBinding(Queue jsonMessageQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(jsonMessageQueue).to(topicExchange).with(JSON_ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer jsonMessagingContainer(@Qualifier(value = "jsonMessageListener") CoreMessageListener jsonMessageListener,
                                                          @Qualifier(value = "jsonMessageQueue") Queue jsonMessageQueue) {

        return RabbitMqConfig.getListenerContainer(jsonMessageListener, jsonMessageQueue);
    }
}
