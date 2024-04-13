package com.chitchat.domain.message;

import java.util.List;

public interface MessageGrpcClient {

    void pushMessage(Message message);

    List<Message> pullMessages(String serverAddress, List<String> ids);

    List<String> fetchMessageIds(String serverAddress);
}
