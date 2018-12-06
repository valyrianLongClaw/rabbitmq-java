package com.rabbitmq.queue.config;

import com.rabbitmq.queue.jmsq.listener.CoreMessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static String TOPIC_EXCHANGE_NAME;

    private static ConnectionFactory connectionFactory;

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public RabbitMqConfig() {
    }

    @Autowired
    public RabbitMqConfig(@Value("${spring.rabbitmq.template.exchange}") String topicExchange) {
        TOPIC_EXCHANGE_NAME = topicExchange;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean(name = "textRabbitTemplate")
    public RabbitTemplate textRabbitTemplate(final ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean(name = "jsonRabbitTemplate")
    public RabbitTemplate jsonRabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        jsonRabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return jsonRabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static SimpleMessageListenerContainer getListenerContainer(CoreMessageListener messageListener,
                                                                      Queue queue) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setMessageListener(messageListener);
        return container;
    }
}
