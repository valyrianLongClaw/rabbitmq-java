package com.rabbitmq.queue.jmsq.listener.Impl;

import com.rabbitmq.queue.jmsq.listener.CoreMessageListener;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Service;

@Service
public class NormalMessageListener implements CoreMessageListener {

    /*public void receiveMessage(String message) {
        System.out.println("Message received: " + message);
    }*/

    @Override
    public void onMessage(Message message) {

        String receivedMessage = (String) SerializationUtils.deserialize(message.getBody());
        System.out.println("Message received: " + receivedMessage);

    }
}
