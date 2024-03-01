package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String id;

    private String sender;

    private String receiver;

    private String messageBody;

    private Long sendTime;
}
