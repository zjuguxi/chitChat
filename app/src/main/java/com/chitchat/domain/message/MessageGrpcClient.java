package com.chitchat.domain.message;

import java.util.List;

public interface MessageGrpcClient {

    void pushMessage(Message message);

    List<Message> pullMessages(List<Long> ids);

    List<Long> fetchDifference(List<Long> ids);
}
