package com.chitchat.domain.message;

import java.util.List;

public interface MessageService {

    void saveMessage(Message message);

    void saveMessages(List<Message> messages);

    List<Message> getAllMessages();

    List<Message> getMessagesByIds(List<String> ids);
}
