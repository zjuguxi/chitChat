package com.chitchat.grpc.client;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageGrpcClient;
import com.chitchat.grpc.service.MessageIdsRequest;
import com.chitchat.grpc.service.MessageIdsResponse;
import com.chitchat.grpc.service.MessageRequest;
import com.chitchat.grpc.service.MessageServiceGrpc;
import com.chitchat.grpc.service.MessagesResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageGrpcClientImpl implements MessageGrpcClient {

    @GrpcClient("message") // fixme
    private MessageServiceGrpc.MessageServiceBlockingStub messageServiceStub;

    @Override
    public void pushMessage(Message message) {
        MessageRequest request = MessageRequest.newBuilder().build();
        BeanUtils.copyProperties(message, request);
        messageServiceStub.pushMessage(request);
    }

    @Override
    public List<Message> pullMessages(List<Long> ids) {
        MessageIdsRequest request = MessageIdsRequest.newBuilder()
                .addAllIds(ids)
                .build();
        MessagesResponse response = messageServiceStub.pullMessages(request);
        return response.getMessagesList().stream()
                .map(messageResponse -> {
                    Message message = new Message();
                    BeanUtils.copyProperties(messageResponse, message);
                    return message;
                })
                .toList();
    }

    @Override
    public List<Long> fetchDifference(List<Long> ids) {
        MessageIdsRequest request = MessageIdsRequest.newBuilder()
                .addAllIds(ids)
                .build();
        MessageIdsResponse response = messageServiceStub.fetchDifference(request);
        return response.getIdsList();
    }
}
