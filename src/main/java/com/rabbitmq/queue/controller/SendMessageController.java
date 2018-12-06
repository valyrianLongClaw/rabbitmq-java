package com.rabbitmq.queue.controller;

import com.rabbitmq.queue.dto.SimplePojo;
import com.rabbitmq.queue.jmsq.publisher.Impl.JsonMessagePublisher;
import com.rabbitmq.queue.jmsq.publisher.Impl.NormalMessagePublisher;
import com.rabbitmq.queue.jmsq.util.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class SendMessageController {

    private NormalMessagePublisher textMessagePublisher;

    private JsonMessagePublisher jsonMessagePublisher;

    @Autowired
    public void setTextMessagePublisher(NormalMessagePublisher textMessagePublisher) {
        this.textMessagePublisher = textMessagePublisher;
    }

    @Autowired
    public void setJsonMessagePublisher(JsonMessagePublisher jsonMessagePublisher) {
        this.jsonMessagePublisher = jsonMessagePublisher;
    }

    @GetMapping("send/text")
    public ResponseEntity<?> publishMessage(@RequestParam(name = "message", defaultValue = "Ashraf", required = true) final String message) {
        textMessagePublisher.publisher(MessageCreator.createTextMessage(message));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("send/json")
    public ResponseEntity<?> publishMessage() {

        SimplePojo simplePojo = new SimplePojo("Ashraf", "India", "Hyderabad");
        jsonMessagePublisher.publisher(MessageCreator.createSimplePojoMessage(simplePojo));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
