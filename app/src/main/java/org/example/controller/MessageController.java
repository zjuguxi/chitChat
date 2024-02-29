package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping(path = "/message")
@Tag(name = "Message", description = "Message Operations")
public class MessageController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message sendMessage(@RequestBody Message message) {
        message.setId(UUID.randomUUID().toString());
        message.setSendTime(Instant.now().getEpochSecond());
        return message;
    }
}
