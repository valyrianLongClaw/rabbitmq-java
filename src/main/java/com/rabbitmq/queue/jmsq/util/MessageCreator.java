package com.rabbitmq.queue.jmsq.util;

import com.rabbitmq.queue.dto.SimplePojo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.utils.SerializationUtils;

public class MessageCreator {

    public static Message createSimplePojoMessage(SimplePojo simplePojo) {

        return getMessageForObject(simplePojo, simplePojo.getUserName());
    }

    public static Message createTextMessage(String message) {
        return getMessageForObject(message, message);
    }

    private static Message getMessageForObject(Object object, String messageId) {

        MessageProperties properties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT)
                .setMessageId(messageId)
                .build();

        return MessageBuilder
                .withBody(SerializationUtils.serialize(object))
                .andProperties(properties)
                .build();
    }
}
