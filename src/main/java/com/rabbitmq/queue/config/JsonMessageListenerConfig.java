package com.rabbitmq.queue.config;

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

    @Bean
    public Queue jsonMessageQueue() {
        return new Queue(JSON_MESSAGE_QUEUE, true);
    }

    @Bean
    public MessageListenerAdapter jsonListenerAdapter(JsonMessageListener jsonMessageListener) {
        return new MessageListenerAdapter(jsonMessageListener, "receiveMessage");
    }

    @Bean(name = "jsonListenerAdapter")
    public Binding jsonQueueBinding(Queue jsonMessageQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(jsonMessageQueue).to(topicExchange).with(JSON_ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer jsonMessagingContainer(ConnectionFactory connectionFactory,
                                             @Qualifier(value = "jsonListenerAdapter") MessageListenerAdapter jsonListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(JSON_MESSAGE_QUEUE);
        container.setMessageListener(jsonListenerAdapter);
        return container;
    }
}
