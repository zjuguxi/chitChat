package com.chitchat.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String id;

    private String sender;

    private Long sendTime;

    private String content;
}
