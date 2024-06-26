package com.chitchat.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {

    private String id;

    private String sender;

    private Long sendTime;

    private String content;
}
