package com.rabbitmq.queue.jmsq.listener.Impl;

import com.rabbitmq.queue.dto.SimplePojo;
import com.rabbitmq.queue.jmsq.listener.CoreMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Service;

@Service(value = "jsonMessageListener")
public class JsonMessageListener implements CoreMessageListener {

    Logger logger = LoggerFactory.getLogger(JsonMessageListener.class);

    @Override
    public void onMessage(Message message) {

        SimplePojo simplePojo = (SimplePojo) SerializationUtils.deserialize(message.getBody());
        logger.info("Received json is: " + simplePojo.toString());
    }
}
