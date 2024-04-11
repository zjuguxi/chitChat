package com.chitchat.web.controller;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageService;
import com.chitchat.web.dto.MessageRequestDto;
import com.chitchat.web.dto.MessageResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/messages")
@Tag(name = "Message", description = "Message APIs")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveMessage(@RequestBody MessageRequestDto requestDto) {
        Message message = new Message();
        BeanUtils.copyProperties(requestDto, message);
        messageService.saveMessage(message);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageResponseDto> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return messages.stream()
                .map(message -> {
                    MessageResponseDto responseDto = new MessageResponseDto();
                    BeanUtils.copyProperties(message, responseDto);
                    return responseDto;
                })
                .toList();
    }
}
