package com.chitchat.grpc.client;

import com.chitchat.grpc.service.MessageServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageGrpcStubManager {

    @Value("${grpc.server.port}")
    private int serverPort;

    private static final Map<String, MessageServiceGrpc.MessageServiceBlockingStub> stubs = new ConcurrentHashMap<>();

    public MessageServiceGrpc.MessageServiceBlockingStub getStub(String serverAddress) {
        return stubs.computeIfAbsent(serverAddress, this::createStub);
    }

    private MessageServiceGrpc.MessageServiceBlockingStub createStub(String serverAddress) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        return MessageServiceGrpc.newBlockingStub(channel);
    }
}
