package com.chitchat.grpc.client;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageGrpcClient;
import com.chitchat.grpc.service.EmptyRequest;
import com.chitchat.grpc.service.MessageIdsRequest;
import com.chitchat.grpc.service.MessageIdsResponse;
import com.chitchat.grpc.service.MessageRequest;
import com.chitchat.grpc.service.MessageServiceGrpc;
import com.chitchat.grpc.service.MessagesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class MessageGrpcClientImpl implements MessageGrpcClient {

    @Value("${grpc.client.test.address}")
    private List<String> serverAddresses;

    @Autowired
    private MessageGrpcStubManager stubManager;

    @Override
    public void pushMessage(Message message) {
        for (String serverAddress : serverAddresses) {
            CompletableFuture.runAsync(() -> {
                try {
                    log.info("Start push message {} to server {}", message.getId(), serverAddress);
                    pushMessage(serverAddress, message);
                    log.info("Push message {} to server {} successful", message.getId(), serverAddress);
                } catch (Exception e) {
                    log.error(String.format("Push message %s to server %s failed", message.getId(), serverAddress), e);
                }
            });
        }
    }

    private void pushMessage(String serverAddress, Message message) {
        MessageRequest request = MessageRequest.newBuilder()
                .setId(message.getId())
                .setSender(message.getSender())
                .setSendTime(message.getSendTime())
                .setContent(message.getContent())
                .build();
        MessageServiceGrpc.MessageServiceBlockingStub stub = stubManager.getStub(serverAddress);
        stub.pushMessage(request);
    }

    @Override
    public List<Message> pullMessages(String serverAddress, List<String> ids) {
        MessageIdsRequest request = MessageIdsRequest.newBuilder()
                .addAllIds(ids)
                .build();
        MessageServiceGrpc.MessageServiceBlockingStub stub = stubManager.getStub(serverAddress);
        MessagesResponse response = stub.pullMessages(request);
        return response.getMessagesList().stream()
                .map(messageResponse -> Message.builder()
                        .id(messageResponse.getId())
                        .sender(messageResponse.getSender())
                        .sendTime(messageResponse.getSendTime())
                        .content(messageResponse.getContent())
                        .build()
                )
                .toList();
    }

    @Override
    public List<String> fetchMessageIds(String serverAddress) {
        EmptyRequest request = EmptyRequest.getDefaultInstance();
        MessageServiceGrpc.MessageServiceBlockingStub stub = stubManager.getStub(serverAddress);
        MessageIdsResponse response = stub.fetchMessageIds(request);
        return response.getIdsList();
    }
}
