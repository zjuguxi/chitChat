package com.chitchat.grpc.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServerService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                .setMessage("Hello, " + request.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
