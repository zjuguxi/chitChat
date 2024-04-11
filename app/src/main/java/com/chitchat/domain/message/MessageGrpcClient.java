package com.chitchat.domain.message;

import java.util.List;

public interface MessageGrpcClient {

    void pushMessage(Message message);

    List<Message> pullMessages(List<String> ids);

    List<String> fetchDifference(List<String> ids);
}
