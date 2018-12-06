package com.rabbitmq.queue.config;

import com.rabbitmq.queue.jmsq.listener.Impl.NormalMessageListener;
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
public class NormalMessageListenerConfig {

    private static final String TEXT_MESSAGE_QUEUE = "textMessageQueue";
    public static final String TEXT_MESSAGE_ROUTING_KEY = "textMessageRoutingKey";

    @Bean(name = "textMessageQueue")
    public Queue textMessageQueue() {
        return new Queue(TEXT_MESSAGE_QUEUE, true);
    }

    @Bean(name = "listenerAdapter")
    MessageListenerAdapter listenerAdapter(NormalMessageListener normalMessageListener) {
        return new MessageListenerAdapter(normalMessageListener, "receiveMessage");
    }

    @Bean
    Binding binding(Queue textMessageQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(textMessageQueue).to(topicExchange).with(TEXT_MESSAGE_ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer textMessagingContainer(ConnectionFactory connectionFactory,
                                             @Qualifier(value = "listenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(TEXT_MESSAGE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
