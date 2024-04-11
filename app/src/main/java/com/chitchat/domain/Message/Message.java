package com.chitchat.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Long id;

    private String sender;

    private Long sendTime;

    private String content;

    private String receiver;
}