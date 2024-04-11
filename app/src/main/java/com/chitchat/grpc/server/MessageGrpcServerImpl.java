package com.chitchat.grpc.server;

import com.chitchat.domain.message.Message;
import com.chitchat.domain.message.MessageService;
import com.chitchat.grpc.service.EmptyResponse;
import com.chitchat.grpc.service.MessageIdsRequest;
import com.chitchat.grpc.service.MessageIdsResponse;
import com.chitchat.grpc.service.MessageRequest;
import com.chitchat.grpc.service.MessageResponse;
import com.chitchat.grpc.service.MessageServiceGrpc;
import com.chitchat.grpc.service.MessagesResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class MessageGrpcServerImpl extends MessageServiceGrpc.MessageServiceImplBase {

    @Autowired
    private MessageService messageService;

    @Override
    public void pushMessage(MessageRequest request, StreamObserver<EmptyResponse> responseObserver) {
        Message message = new Message();
        BeanUtils.copyProperties(request, message);
        messageService.saveMessage(message);
        responseObserver.onNext(EmptyResponse.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void pullMessages(MessageIdsRequest request, StreamObserver<MessagesResponse> responseObserver) {
        List<Long> ids = request.getIdsList();
        List<Message> messages = messageService.getMessagesByIds(ids);
        List<MessageResponse> messageResponses = messages.stream()
                .map(message -> {
                    MessageResponse messageResponse = MessageResponse.newBuilder().build();
                    BeanUtils.copyProperties(message, messageResponse);
                    return messageResponse;
                })
                .toList();
        MessagesResponse response = MessagesResponse.newBuilder()
                .addAllMessages(messageResponses)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void fetchDifference(MessageIdsRequest request, StreamObserver<MessageIdsResponse> responseObserver) {
        // todo
    }
}
